package by.bsu.contactdirectory.action;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.service.ServiceServerException;
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
		String buf = request.getParameter("id");
		if (buf == null) {
			logger.error("Null contact id got.");
			request.setAttribute("errorMessage", "No contact selected.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			return;
		}
		Contact contact = null;
		try {
			contact = contactService.getContact(Integer.parseInt(buf));
		} catch (IllegalArgumentException ex) {
			logger.error("Invalid contact id got: " + buf);
			request.setAttribute("errorMessage", "Invalid parameter.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			return;
		} catch (ServiceServerException ex) {
			logger.error("Can't load contact info.", ex);
			request.setAttribute("errorMessage", "Internal server error. Sorry.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			return;
		}
		if (contact == null) {
			logger.error("Not existing contact info requested. Id: " + buf);
			request.setAttribute("errorMessage", "No such contact.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			return;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	    request.setAttribute("contact", contact);
	    request.setAttribute("action", Actions.UPDATE_CONTACT.substring(1));
	    request.setAttribute("dateFormat", dateFormat);
		try {
			request.setAttribute("countries", countryService.getCountryNames());
			request.setAttribute("codes", countryService.getCountryCodes());
		} catch (ServiceServerException ex) {
			logger.error("Can't get country list.", ex);
			request.setAttribute("errorMessage", "Internal server error. Sorry.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		}
	    request.setAttribute("marital", MaritalStatus.values());
	    request.setAttribute("genders", Gender.values());
		request.setAttribute("defaultPhoto", "img/contacts/default.jpg");
		logger.info("Contact info requested. Id: " + buf);
		request.getRequestDispatcher("jsp/contact_info.jsp").forward(request, response);
	}

}
