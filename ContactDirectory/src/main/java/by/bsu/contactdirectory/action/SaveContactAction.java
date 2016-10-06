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
import by.bsu.contactdirectory.servlet.Actions;
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
		logger.info("SaveContact action requested.");

		request.getSession().removeAttribute(PAGE_ATTRIBUTE);
		request.getSession().removeAttribute(SEARCH_OBJECT_ATTRIBUTE);

		try {
			Contact contact = new Contact();
			Photo photo = new Photo();
			contact.setPhoto(photo);
			Address address = new Address();
			contact.setAddress(address);
			Map<String, String> fileMap = new HashMap<>();
			process(request, response, contact, fileMap);

			for (Attachment att : contact.getAttachments()) {
				att.setPath(fileMap.get(att.getPath()));
			}

			contactService.createContact(contact);
			logger.info("New contact created successfully.");
			response.sendRedirect(Actions.CONTACT_LIST.substring(1));
		} catch (ActionException ex) {
			logger.error(ex.getMessage());
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Invalid parameter.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		} catch (IOException ex) {
			logger.error(ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Internal server error. Sorry.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		} catch (ServiceServerException ex) {
			logger.error("Failed to create contact.", ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Internal server error. Sorry.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		} catch (ServiceClientException ex) {
			logger.error("Validation failed.");
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Invalid parameters.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
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
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		String name = item.getFieldName();
		String value = Streams.asString(item.getInputStream(), "UTF-8");
		switch (name) {
			case FIRST_NAME_ATTRIBUTE:
				contact.setFirstName(value);
				break;
			case LAST_NAME_ATTRIBUTE:
				contact.setLastName(value);
				break;
			case PATRONYMIC_ATTRIBUTE:
				contact.setPatronymic(value);
				break;
			case GENDER_ATTRIBUTE:
				contact.setGender(ActionHelper.getGenderFromString(value));
				break;
			case CITIZENSHIP_ATTRIBUTE:
				contact.setCitizenship(value);
				break;
			case MARITAL_STATUS_ATTRIBUTE:
				contact.setMaritalStatus(ActionHelper.getMaritalStatusFromString(value));
				break;
			case EMAIL_ATTRIBUTE:
				contact.setEmail(value);
				break;
			case WEB_SITE_ATTRIBUTE:
				contact.setWebSite(value);
				break;
			case PLACE_OF_WORK_ATTRIBUTE:
				contact.setPlaceOfWork(value);
				break;
			case BIRTH_DATE_ATTRIBUTE:
				contact.setBirthDate(ActionHelper.getCalendarFromString(value, BIRTH_DATE_ATTRIBUTE));
				break;
			case COUNTRY_ATTRIBUTE:
				contact.getAddress().setCountry(value);
				break;
			case CITY_ATTRIBUTE:
				contact.getAddress().setCity(value);
				break;
			case LOCAL_ADDRESS_ATTRIBUTE:
				contact.getAddress().setLocalAddress(value);
				break;
			case INDEX_ATTRIBUTE:
				contact.getAddress().setIndex(value);
				break;
			case CREATE_PHONE_ATTRIBUTE:
				Phone phone = ActionHelper.parsePhone(value);
				if (phone != null) {
					contact.getPhones().add(phone);
				}
				break;
			case CREATE_ATT_ATTRIBUTE:
				Attachment att = ActionHelper.parseAttachment(value);
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
		String folder = "";
		if (PHOTO_ATTRIBUTE.equals(paramName)) {
			filename = FileNameGenerator.generatePhotoFileName(extension);
			folder = FileNameGenerator.photosPath;

		} else if (paramName != null && paramName.startsWith(ATT_FILE_ATTRIBUTE)) {
			filename = FileNameGenerator.generateAttFileName(extension);
			folder = FileNameGenerator.filesPath;
		} else {
			return;
		}
		if (filename != null && !filename.isEmpty()) {
			try {
				logger.debug(String.format("NEW FILE: %s%s", folder, filename));
				File storeFile = new File(folder, filename);
				logger.debug(String.format("NEW FILE: %s", storeFile.getAbsolutePath()));
				if (!storeFile.createNewFile()) {
					throw new IOException(String.format("Can't create file: %s%s", folder, filename));
				}
				item.write(storeFile);
			} catch (IOException ex) {
				throw ex;
			} catch (Exception ex) {
				throw new IOException(ex);
			}
			if (PHOTO_ATTRIBUTE.equals(paramName)) {
				contact.getPhoto().setPath(filename);

			} else if (paramName != null && paramName.startsWith(ATT_FILE_ATTRIBUTE)) {
				fileMap.put(paramName, filename);
			}
		}

	}
}
