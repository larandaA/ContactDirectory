package by.bsu.contactdirectory.action;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.service.ServiceClientException;
import by.bsu.contactdirectory.service.ServiceServerException;
import by.bsu.contactdirectory.servlet.MainServlet;
import by.bsu.contactdirectory.util.file.FileNameGenerator;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsu.contactdirectory.entity.*;
import by.bsu.contactdirectory.service.ContactService;

public class SaveContactAction implements Action {
	
	private ContactService contactService = new ContactService();
	private static Logger logger = LogManager.getLogger(SaveContactAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		request.getSession().removeAttribute("page");
		request.getSession().removeAttribute("searchObject");

		Contact contact = new Contact();
		Photo photo = new Photo();
		contact.setPhoto(photo);
		Address address = new Address();
		contact.setAddress(address);
		Map<String, String> fileMap = new HashMap<>();

		try {
			process(request, response, contact, fileMap);
		} catch (ActionException ex) {
			logger.error(ex.getMessage());
			request.setAttribute("errorMessage", "Invalid parameter.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			return;
		} catch (IOException ex) {
			logger.error(ex);
			request.setAttribute("errorMessage", "Internal server error. Sorry.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			return;
		}

		for (Attachment att : contact.getAttachments()) {
			att.setPath(fileMap.get(att.getPath().substring(2)));
		}
		
		try {
			contactService.createContact(contact);
			logger.info("New contact created successfully.");
			response.sendRedirect("ContactList");
		} catch (ServiceServerException ex) {
			logger.error("Failed to create contact.", ex);
			request.setAttribute("errorMessage", "Internal server error. Sorry.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		} catch (ServiceClientException ex) {
			logger.error("Validation failed.");
			request.setAttribute("errorMessage", "Invalid parameters.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		}
	}


	private void process(HttpServletRequest request, HttpServletResponse response, Contact contact, Map<String, String> fileMap) throws IOException, ActionException {
		if (!ServletFileUpload.isMultipartContent(request)) {
			return;
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					processFormField(item, contact);
				}
				else {
					processFile(item, contact, fileMap);
				}
			}

		} catch (FileUploadException ex) {
			throw new IOException(ex);
		}
	}

	private void processFormField(FileItem item, Contact contact) throws IOException, ActionException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		String name = item.getFieldName();
		String value = Streams.asString(item.getInputStream(), "UTF-8");
		/*value = URLEncoder.encode(value, "CP1251");
		value = URLDecoder.decode(value, "UTF-8");*/
		switch (name) {
			case "firstName":
				contact.setFirstName(value);
				break;
			case "lastName":
				contact.setLastName(value);
				break;
			case "patronymic":
				contact.setPatronymic(value);
				break;
			case "gender":
				if (value != null && !value.isEmpty()) {
					try {
						contact.setGender(Gender.valueOf(value.toUpperCase()));
					} catch (IllegalArgumentException ex) {
						throw new ActionException("Invalid gender got: " + value);
					}
				} break;
			case "citizenship":
				contact.setCitizenship(value);
				break;
			case "maritalStatus":
				if (value != null && !value.isEmpty()) {
					try {
						contact.setMaritalStatus(MaritalStatus.valueOf(value.toUpperCase()));
					} catch (IllegalArgumentException ex) {
						throw new ActionException("Invalid marital status got: " + value);
					}
				} break;
			case "email":
				contact.setEmail(value);
				break;
			case "webSite":
				contact.setWebSite(value);
				break;
			case "placeOfWork":
				contact.setPlaceOfWork(value);
				break;
			case "birthDate":
				if (value != null && !value.isEmpty()) {
					try{
						Calendar birthDate = Calendar.getInstance();
						birthDate.setTime(dateFormat.parse(value));
						contact.setBirthDate(birthDate);
					} catch (ParseException ex) {
						throw new ActionException("Invalid birthDate got: " + value);
					}
				} break;
			case "country":
				contact.getAddress().setCountry(value);
				break;
			case "city":
				contact.getAddress().setCity(value);
				break;
			case "localAddress":
				contact.getAddress().setLocalAddress(value);
				break;
			case "index":
				contact.getAddress().setIndex(value);
				break;
			case "createPhone":
				Phone phone = parsePhone(value);
				if (phone != null) {
					contact.getPhones().add(phone);
				}
				break;
			case "createAtt":
				Attachment att = parseAttachment(value);
				if (att != null) {
					contact.getAttachments().add(att);
				}
				break;
		}
	}

	private void processFile(FileItem item, Contact contact, Map<String, String> fileMap) throws IOException, ActionException {
        String paramName = item.getFieldName();
		if (item.getName() == null || item.getName().isEmpty()) {
			return;
		}
		String extension = FilenameUtils.getExtension(item.getName());
		String filename = "";
		if ("photo".equals(paramName)) {
			filename = FileNameGenerator.generatePhotoFileName(extension);

		} else if (paramName != null && paramName.startsWith("attFile")) {
			filename = FileNameGenerator.generateAttFileName(extension);
		} else {
			return;
		}
		if (filename != null && !filename.isEmpty()) {
			try {
				File storeFile = new File(filename);
				logger.debug(String.format("NEW FILE: %s", storeFile.getAbsolutePath()));
				if (!storeFile.createNewFile()) {
					throw new IOException("Can't create file: " + filename);
				}
				item.write(storeFile);
			} catch (IOException ex) {
				throw ex;
			} catch (Exception ex) {
				throw new IOException(ex);
			}
			if ("photo".equals(paramName)) {
				contact.getPhoto().setPath(filename);

			} else if (paramName != null && paramName.startsWith("attFile")) {
				fileMap.put(paramName, filename);
			}
		}

	}

	private Phone parsePhone(String value) throws ActionException {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String[] params = value.split("\\|");
		if(params.length != 6) {
			throw new ActionException(String.format("Invalid phone parameters: %s", Arrays.deepToString(params)));
		}
		Phone phone = new Phone();
		if (!params[0].isEmpty()) {
			try {
				phone.setId(Integer.parseInt(params[0]));
			} catch (NumberFormatException ex) {
				throw new ActionException(String.format("Invalid phone id: %s", params[0]));
			}
		}
		if (!params[1].isEmpty()) {
			try {
				phone.setCountryCode(Integer.parseInt(params[1]));
			} catch (NumberFormatException ex) {
				throw new ActionException(String.format("Invalid country code: %s", params[1]));
			}
		}
		if (!params[2].isEmpty()) {
			try {
				phone.setOperatorCode(Integer.parseInt(params[2]));
			} catch (NumberFormatException ex) {
				throw new ActionException(String.format("Invalid operator code: %s", params[2]));
			}
		}
		if (!params[3].isEmpty()) {
			try {
				phone.setPhoneNumber(Integer.parseInt(params[3]));
			} catch (NumberFormatException ex) {
				throw new ActionException(String.format("Invalid phone number: %s", params[3]));
			}
		}
		if (!params[4].isEmpty()) {
			try {
				phone.setType(PhoneType.valueOf(params[4].toUpperCase()));
			} catch (IllegalArgumentException ex) {
				throw new ActionException(String.format("Invalid phone type: %s", params[4]));
			}
		}
		phone.setComment(params[5]);
		return phone;
	}

	private Attachment parseAttachment(String value) throws ActionException {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String[] params = value.split("\\|");
		if(params.length != 4) {
			throw new ActionException(String.format("Invalid attachment parameters: %s", Arrays.deepToString(params)));
		}
		Attachment att = new Attachment();
		if (!params[0].isEmpty()) {
			try {
				att.setId(Integer.parseInt(params[0]));
			} catch (NumberFormatException ex) {
				throw new ActionException(String.format("Invalid attachment id: %s", params[0]));
			}
		}
		att.setName(params[1]);
		att.setPath(params[2]);
		att.setComment(params[3]);
		return att;
	}

}
