package by.bsu.contactdirectory.action;

import by.bsu.contactdirectory.entity.*;
import by.bsu.contactdirectory.service.ContactService;

import by.bsu.contactdirectory.service.ServiceClientException;
import by.bsu.contactdirectory.service.ServiceServerException;
import by.bsu.contactdirectory.servlet.MainServlet;
import by.bsu.contactdirectory.util.generator.FileNameGenerator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UpdateContactAction implements Action {

	private ContactService contactService = new ContactService();
	private static Logger logger = LogManager.getLogger(UpdateContactAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


		Contact contact = new Contact();
		Address address = new Address();
		contact.setAddress(address);
		List<Integer> deletePhones = new LinkedList<>();
		List<String> deleteFiles = new LinkedList<>();
		List<Integer> deleteAttachments = new LinkedList<>();
		boolean photoIsDeleted = false;

		try {
			process(request, response, contact, deletePhones, deleteAttachments, deleteFiles);
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

		if (photoIsDeleted && contact.getPhoto() == null) {
			contact.setPhoto(new Photo());
		}
		contact.getAddress().setContactId(contact.getId());
		if (contact.getPhoto() != null) {
			contact.getPhoto().setContactId(contact.getId());
		}
		for (Attachment attachment : contact.getAttachments()) {
			attachment.setContactId(contact.getId());
		}
		for (Phone phone : contact.getPhones()) {
			phone.setContactId(contact.getId());
		}

		try {
			contactService.updateContact(contact);
			logger.info("Contact updated successfully. Id: " + contact.getId());
			response.sendRedirect("http://127.0.0.1:8080/ContactDirectory/");
		} catch (ServiceServerException ex) {
			logger.error("Failed to update contact. Id: " + contact.getId(), ex);
			request.setAttribute("errorMessage", "Internal server error. Sorry.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		} catch (ServiceClientException ex) {
			logger.error("Validation failed.");
			request.setAttribute("errorMessage", "Invalid parameters.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		}


		/*
		Contact contact = new Contact();
		String buf = request.getParameter("id");
		try {
			contact.setId(Integer.parseInt(buf));
		} catch (IllegalFormatException ex) {
			logger.error("Invalid contact id got: " + buf);
			request.setAttribute("errorMessage", "Invalid parameter.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			return;
		}
		contact.setFirstName(request.getParameter("firstName"));
		contact.setLastName(request.getParameter("lastName"));
		contact.setPatronymic(request.getParameter("patronymic"));
		buf = request.getParameter("gender");
		if (buf == null || buf.isEmpty()) {
			contact.setGender(null);
		} else {
			try {
				contact.setGender(Gender.valueOf(buf.toUpperCase()));
			} catch (IllegalArgumentException ex) {
				logger.error("Invalid gender got: " + buf);
				request.setAttribute("errorMessage", "Invalid parameter.");
				request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
				return;
			}
		}
		contact.setCitizenship(request.getParameter("citizenship"));
		buf = request.getParameter("maritalStatus");
		if (buf == null || buf.isEmpty()) {
			contact.setMaritalStatus(null);
		} else {
			try {
				contact.setMaritalStatus(MaritalStatus.valueOf(buf.toUpperCase()));
			} catch (IllegalArgumentException ex) {
				logger.error("Invalid marital status got: " + buf);
				request.setAttribute("errorMessage", "Invalid parameter.");
				request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
				return;
			}
		}
		contact.setEmail(request.getParameter("email"));
		contact.setWebSite(request.getParameter("webSite"));
		contact.setPlaceOfWork(request.getParameter("placeOfWork"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		buf = request.getParameter("birthDate");
		if (buf == null || buf.isEmpty()) {
			contact.setBirthDate(null);
		} else {
			try{
				Calendar birthDate = Calendar.getInstance();
				birthDate.setTime(dateFormat.parse(buf));
				contact.setBirthDate(birthDate);
			} catch (ParseException ex) {
				logger.error("Invalid birth date got: " + buf);
				request.setAttribute("errorMessage", "Invalid parameter.");
				request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
				return;
			}
		}
		Address address = new Address();
		address.setCountry(request.getParameter("country"));
		address.setCity(request.getParameter("city"));
		address.setLocalAddress(request.getParameter("localAddress"));
		address.setIndex(request.getParameter("index"));
		address.setContactId(contact.getId());
		contact.setAddress(address);

		Photo photo = new Photo();
		photo.setContactId(contact.getId());
		contact.setPhoto(photo);
		try {
			contactService.updateContact(contact);
			logger.info("Contact updated successfully. Id: " + contact.getId());
			response.sendRedirect("http://127.0.0.1:8080/ContactDirectory/ContactList");
		} catch (ServiceClientException ex) {
			logger.error("Validation failed.", ex);
			request.setAttribute("errorMessage", "Invalid parameter.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		} catch (ServiceServerException ex) {
			logger.error("Failed to supdate contact.", ex);
			request.setAttribute("errorMessage", "Internal server error. Sorry.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		}*/
		
	}

	private void process(HttpServletRequest request, HttpServletResponse response, Contact contact, List<Integer> deletePhones, List<Integer> deleteAttachments, List<String> deleteFiles) throws IOException, ActionException {
		if (!ServletFileUpload.isMultipartContent(request)) {
			return;
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					processFormField(item, contact, deletePhones, deleteAttachments, deleteFiles);
				}
				else {
					processFile(item, contact);
				}
			}

		} catch (FileUploadException ex) {
			throw new IOException(ex);
		}
	}

	private void processFormField(FileItem item, Contact contact, List<Integer> deletePhones, List<Integer> deleteAttachments, List<String> deleteFiles) throws IOException, ActionException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		String name = item.getFieldName();
		String value = Streams.asString(item.getInputStream());
		switch (name) {
			case "id":
				try {
					contact.setId(Integer.parseInt(value));
				} catch (NumberFormatException ex) {
					throw new ActionException(String.format("Invalid contact id got: %s", value));
				}
				break;
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
			case "updatePhone":
				Phone phone = parsePhone(value);
				if (phone != null) {
					contact.getPhones().add(phone);
				}
				break;
			case "deletePhoneWithId":
				try {
					deletePhones.add(Integer.parseInt(value));
				} catch (NumberFormatException ex) {
					throw new ActionException(String.format("Invalid phone id to delete got: %s", value));
				}
				break;
			case "deletePhotoWithPath":
			case "deleteFileWithPath":
				if (value != null && !value.isEmpty()) {
					deleteFiles.add(value);
				}
				break;
			case "deleteAttachmentWithId":
				try {
					deleteAttachments.add(Integer.parseInt(value));
				} catch (NumberFormatException ex) {
					throw new ActionException(String.format("Invalid attachment id to delete got: %s", value));
				}
				break;

		}
	}

	private void processFile(FileItem item, Contact contact) throws IOException, ActionException {
		String paramName = item.getFieldName();
		if (item.getName() == null || item.getName().isEmpty()) {
			return;
		}
		String extension = FilenameUtils.getExtension(item.getName());
		String filename = "";
		String uploadPath = MainServlet.appPath;
		switch (paramName) {
			case "photo":
				contact.setPhoto(new Photo());
				filename = FileNameGenerator.generatePhotoFileName(extension);
				break;
		}
		if (filename != null && !filename.isEmpty()) {
			try {
				File storeFile = new File(uploadPath + filename);
				logger.debug("NEW FILE: " + storeFile.getAbsolutePath());
				if (!storeFile.createNewFile()) {
					throw new IOException("Can't create file: " + filename);
				}
				item.write(storeFile);
			} catch (IOException ex) {
				throw ex;
			} catch (Exception ex) {
				throw new IOException(ex);
			}
			switch (paramName) {
				case "photo":
					contact.getPhoto().setPath(filename);
					break;
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

}
