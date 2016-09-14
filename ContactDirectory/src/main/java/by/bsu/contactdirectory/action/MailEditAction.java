package by.bsu.contactdirectory.action;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.service.ContactService;

public class MailEditAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] strIds = request.getParameterValues("checked");
		if (strIds == null || strIds.length == 0) {
			response.sendError(400, "No contacts selected");
			return;
		}
		LinkedList<String> emailList = new LinkedList<>();
		try {
			for(int i = 0; i < strIds.length; i++) {
				Contact contact = new ContactService().getContact(Integer.parseInt(strIds[i]));
				if(contact != null && contact.getEmail() != null && !contact.getEmail().isEmpty()) {
					emailList.add(contact.getEmail());
				}
			}
		} catch (IllegalArgumentException ex) {
			response.sendError(400, "No such contact");
		}
		String emails = String.join(", ", emailList);
		request.setAttribute("emails", emails);
		request.getRequestDispatcher("jsp/email.jsp").forward(request, response);
	}

}
