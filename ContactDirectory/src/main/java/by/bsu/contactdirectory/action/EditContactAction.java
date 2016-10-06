package by.bsu.contactdirectory.action;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.entity.PhoneType;
import by.bsu.contactdirectory.service.ServiceServerException;
import by.bsu.contactdirectory.util.file.FileNameGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.entity.Gender;
import by.bsu.contactdirectory.entity.MaritalStatus;
import by.bsu.contactdirectory.service.ContactService;
import by.bsu.contactdirectory.service.CountryService;
import by.bsu.contactdirectory.servlet.Actions;

public class EditContactAction implements Action {
	
	private ContactService contactService = new ContactService();
	private CountryService countryService = new CountryService();
	private static Logger logger = LogManager.getLogger(EditContactAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("EditContact action requested.");
		try {
			Contact contact = getContact(request);
			setAttributes(request, contact);
			logger.info(String.format("Contact info requested. Id: %d", contact.getId()));
			request.getRequestDispatcher(Actions.CONTACT_INFO_JSP).forward(request, response);
		} catch (ActionException ex) {
			logger.error(ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Invalid parameter.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		} catch (ServiceServerException ex) {
			logger.error(ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Internal server error. Sorry.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		}
	}

	private Contact getContact(HttpServletRequest request) throws ActionException, ServiceServerException {
		Contact contact;
		int id = 0;
		String buf = request.getParameter(ID_ATTRIBUTE);
		if(buf == null || buf.trim().isEmpty()) {
			throw new ActionException("No contact selected.");
		}
		try {
			id = Integer.parseInt(buf);
		} catch (NumberFormatException ex) {
			throw new ActionException(String.format("Invalid id got: %s", buf));
		}
		contact = contactService.getContact(id);
		if (contact == null) {
			throw new ActionException(String.format("No such contact: %d", id));
		}
		return contact;
	}

	private void setAttributes(HttpServletRequest request, Contact contact) throws ServiceServerException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		SimpleDateFormat timeFormat = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
		request.setAttribute(CONTACT_ATTRIBUTE, contact);
		request.setAttribute(ACTION_ATTRIBUTE, Actions.UPDATE_CONTACT.substring(1));
		request.setAttribute(DATE_FORMAT_ATTRIBUTE, dateFormat);
		request.setAttribute(TIME_FORMAT_ATTRIBUTE, timeFormat);

		request.setAttribute(COUNTRIES_ATTRIBUTE, countryService.getCountryNames());
		request.setAttribute(CODES_ATTRIBUTE, countryService.getCountryCodes());

		request.setAttribute(MARITAL_ATTRIBUTE, MaritalStatus.values());
		request.setAttribute(GENDERS_ATTRIBUTE, Gender.values());
		request.setAttribute(TYPES_ATTRIBUTE, PhoneType.values());
		request.setAttribute(DEFAULT_PHOTO_ATTRIBUTE, FileNameGenerator.defaultPhotoPath);
	}
}
