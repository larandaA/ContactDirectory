package by.bsu.contactdirectory.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.service.ServiceClientException;
import by.bsu.contactdirectory.service.ServiceServerException;
import by.bsu.contactdirectory.servlet.MainServlet;
import by.bsu.contactdirectory.util.generator.FileNameGenerator;
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
		request.getSession().removeAttribute("page");
		request.getSession().removeAttribute("searchObject");

		Contact contact = new Contact();
		Photo photo = new Photo();
		contact.setPhoto(photo);
		Address address = new Address();
		contact.setAddress(address);

		try {
			process(request, response, contact);
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
		
		try {
			contactService.createContact(contact);
			logger.info("New contact created successfully.");
			response.sendRedirect("http://127.0.0.1:8080/ContactDirectory/");
		} catch (ServiceServerException ex) {
			logger.error("Failed to create contact." + ex);
			request.setAttribute("errorMessage", "Internal server error. Sorry.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		} catch (ServiceClientException ex) {
			logger.error("Validation failed.");
			request.setAttribute("errorMessage", "Invalid parameters.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		}
	}


	private void process(HttpServletRequest request, HttpServletResponse response, Contact contact) throws IOException, ActionException {
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
					processFile(item, contact);
				}
			}

		} catch (FileUploadException ex) {
			throw new IOException(ex);
		}
	}

	private void processFormField(FileItem item, Contact contact) throws IOException, ActionException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		String name = item.getFieldName();
		String value = Streams.asString(item.getInputStream());
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
		}
	}

	private void processFile(FileItem item, Contact contact) throws IOException, ActionException {
        String paramName = item.getFieldName();
		String extension = FilenameUtils.getExtension(item.getName());
		String filename = "";
		String uploadPath = MainServlet.appPath;
		switch (paramName) {
			case "photo":
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


}
