package by.bsu.contactdirectory.action;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.service.ServiceServerException;
import by.bsu.contactdirectory.util.template.STRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.service.ContactService;

public class MailEditAction implements Action {

	private static Logger logger = LogManager.getLogger(MailEditAction.class);
	private ContactService contactService = new ContactService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String[] strIds = request.getParameterValues("checked");
		if (strIds == null || strIds.length == 0) {
			logger.error("Null contact ids got.");
			request.setAttribute("errorMessage", "No contacts selected.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			return;
		}
		LinkedList<String> emailList = new LinkedList<>();
		LinkedList<Integer> idList = new LinkedList<>();
		try {
			for(int i = 0; i < strIds.length; i++) {
				Contact contact = new ContactService().getContact(Integer.parseInt(strIds[i]));
				if(contact != null && contact.getEmail() != null && !contact.getEmail().isEmpty()) {
					emailList.add(contact.getEmail());
					idList.add(contact.getId());
				}
			}
		} catch (IllegalArgumentException ex) {
			logger.error("Invalid ids got: " + Arrays.deepToString(strIds));
			request.setAttribute("errorMessage", "Invalid parameters.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			return;
		} catch (ServiceServerException ex) {
			logger.error("Can't load contact info.", ex);
			request.setAttribute("errorMessage", "Internal server error. Sorry.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			return;
		}
		if (emailList.size() > 0) {
			String emails = String.join(", ", emailList);
			request.setAttribute("emails", emails);
			request.setAttribute("ids", idList);
			request.setAttribute("templates", STRenderer.templates);
			logger.info(String.format("Email message form requested for emails: %s", emails));
			request.getRequestDispatcher("jsp/email.jsp").forward(request, response);
		} else {
			String errorMessage = "No one from chosen contacts has an email.";
			request.getSession().setAttribute("errorMessage", errorMessage);
			logger.info("Sending email can't be performed. No one from chosen contacts has an email.");
			response.sendRedirect("ContactList");
		}
	}

}
