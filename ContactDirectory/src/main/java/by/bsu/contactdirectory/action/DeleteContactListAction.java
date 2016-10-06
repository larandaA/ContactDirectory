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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsu.contactdirectory.service.ContactService;

public class DeleteContactListAction implements Action {
	
	private ContactService contactService = new ContactService();
	private static Logger logger = LogManager.getLogger(DeleteContactListAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("DeleteContactList action requested.");

		try {
			List<Integer> idList = getIdList(request);
			contactService.deleteContactList(idList);
			logger.info(String.format("Contacts deleted: %s", Arrays.deepToString(idList.toArray())));
			response.sendRedirect(Actions.CONTACT_LIST.substring(1));
		} catch (ActionException ex) {
			logger.error(ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Invalid parameters.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		} catch(ServiceServerException ex) {
			logger.error("Can't delete contact.", ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Internal server error. Sorry.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		}
	}

	private List<Integer> getIdList(HttpServletRequest request) throws ActionException {
		List<Integer> list = new LinkedList<>();
		String[] strIds = request.getParameterValues(CHECKED_ATTRIBUTE);
		if (strIds != null && strIds.length > 0){
			try {
				for (int i = 0; i < strIds.length; i++) {
					list.add(Integer.parseInt(strIds[i]));
				}
			} catch (NumberFormatException ex) {
				throw new ActionException(String.format("Invalid id list got: %s", Arrays.deepToString(strIds)));
			}
		}
		else {
			throw new ActionException("No contacts selected.");
		}
		return list;
	}

}
