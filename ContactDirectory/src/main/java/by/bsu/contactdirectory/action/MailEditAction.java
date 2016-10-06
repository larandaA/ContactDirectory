package by.bsu.contactdirectory.action;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.service.ServiceServerException;
import by.bsu.contactdirectory.servlet.Actions;
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
		logger.info("MailEdit action requested.");

		String[] strIds = request.getParameterValues(CHECKED_ATTRIBUTE);
		try {
			LinkedList<String> emailList = new LinkedList<>();
			LinkedList<Integer> idList = new LinkedList<>();
			fillLists(strIds, emailList, idList);

			if (emailList.size() > 0) {
				String emails = String.join(", ", emailList);
				request.setAttribute(EMAILS_ATTRIBUTE, emails);
				request.setAttribute(IDS_ATTRIBUTE, idList);
				request.setAttribute(TEMPLATES_ATTRIBUTE, STRenderer.templates);
				logger.info(String.format("Email message form requested for emails: %s", emails));
				request.getRequestDispatcher(Actions.EMAIL_JSP).forward(request, response);
			} else {
				String errorMessage = "No one from chosen contacts has an email.";
				request.getSession().setAttribute(ERROR_MESSAGE_ATTRIBUTE, errorMessage);
				logger.info("Sending email can't be performed. No one from chosen contacts has an email.");
				response.sendRedirect(Actions.CONTACT_LIST.substring(1));
			}
		} catch (ServiceServerException ex) {
			logger.error("Can't load contact info.", ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Internal server error. Sorry.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		} catch (ActionException ex) {
			logger.error(ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Invalid parameters.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		}

	}

	private void fillLists(String[] strIds, List<String> emailList, List<Integer> idList) throws ActionException, ServiceServerException {
		if (strIds == null || strIds.length == 0) {
			throw new ActionException("No contacts selected.");
		}
		try {
			for (int i = 0; i < strIds.length; i++) {
				Contact contact = contactService.getContact(Integer.parseInt(strIds[i]));
				if (contact != null && contact.getEmail() != null && !contact.getEmail().isEmpty()) {
					emailList.add(contact.getEmail());
					idList.add(contact.getId());
				}
			}
		} catch (NumberFormatException ex) {
			throw new ActionException(String.format("Invalid id params got: %s", Arrays.deepToString(strIds)));
		}
	}

}
