package by.bsu.contactdirectory.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.service.ContactService;

public class DeleteContactAction implements Action {
	
	private ContactService contactService = new ContactService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String buf = request.getParameter("id");
		if (buf == null) {
			response.sendError(400, "No contact selected");
		} else {
			try {
				int id = Integer.parseInt(buf);
				contactService.deleteContact(id);
				response.sendRedirect("http://127.0.0.1:8080/ContactDirectory/ContactList");
			} catch (IllegalArgumentException ex) {
				response.sendError(400, "Illegal argument");
			}
		}		
	}

}
