package by.bsu.contactdirectory.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.service.ServiceServerException;
import by.bsu.contactdirectory.servlet.Actions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsu.contactdirectory.service.ContactService;

public class DeleteContactAction implements Action {
	
	private ContactService contactService = new ContactService();
	private static Logger logger = LogManager.getLogger(DeleteContactAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("DeleteContact action requested.");

		try {
			int id = getContactId(request);
			contactService.deleteContact(id);
			logger.info(String.format("Contact deleted. Id: %d", id));
			response.sendRedirect(Actions.CONTACT_LIST.substring(1));
		} catch (ActionException ex) {
			logger.error(ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Invalid parameters.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		} catch (ServiceServerException ex) {
			logger.error("Can't delete contact.", ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Internal server error. Sorry.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		}
	}

	private int getContactId(HttpServletRequest request) throws ActionException {
		String buf = request.getParameter(ID_ATTRIBUTE);
		int id;
		if (buf == null) {
			throw new ActionException("No contact selected.");
		}
		try {
			id = Integer.parseInt(buf);
		} catch (IllegalArgumentException ex) {
			throw new ActionException(String.format("Invalid id got: %s.", buf));
		}
		return id;
	}
}
