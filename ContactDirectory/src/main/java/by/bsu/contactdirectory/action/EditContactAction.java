package by.bsu.contactdirectory.action;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.entity.Gender;
import by.bsu.contactdirectory.entity.MaritalStatus;
import by.bsu.contactdirectory.service.ContactService;
import by.bsu.contactdirectory.service.CountryService;
import by.bsu.contactdirectory.servlet.Actions;

public class EditContactAction implements Action {
	
	private ContactService contactService = new ContactService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String buf = request.getParameter("id");
		if (buf == null) {
			response.sendError(400, "No contact selected");
			return;
		}
		Contact contact = null;
		try {
			contact = contactService.getContact(Integer.parseInt(buf));
		} catch (IllegalArgumentException ex) {
			response.sendError(400, "No such contact");
			return;
		}
		if (contact == null) {
			response.sendError(400, "No such contact");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	    request.setAttribute("contact", contact);
	    request.setAttribute("action", Actions.UPDATE_CONTACT.substring(1));
	    request.setAttribute("dateFormat", dateFormat);
	    request.setAttribute("countries", new CountryService().getCountryNames());
	    request.setAttribute("marital", MaritalStatus.values());
	    request.setAttribute("genders", Gender.values());
		request.getRequestDispatcher("jsp/contact_info.jsp").forward(request, response);
	}

}
