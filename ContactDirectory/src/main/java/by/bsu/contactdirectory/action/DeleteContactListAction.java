package by.bsu.contactdirectory.action;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.service.ServiceServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsu.contactdirectory.service.ContactService;

public class DeleteContactListAction implements Action {
	
	private ContactService contactService = new ContactService();
	private static Logger logger = LogManager.getLogger(DeleteContactListAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] strIds = request.getParameterValues("checked");
		if (strIds != null && strIds.length > 0){
			try {
				LinkedList<Integer> ids = new LinkedList<>();
				for (int i = 0; i < strIds.length; i++) {
					ids.add(Integer.parseInt(strIds[i]));
				}
				contactService.deleteContactList(ids);
				logger.info(String.format("Contacts deleted: %s", Arrays.deepToString(strIds)));
				response.sendRedirect("ContactList");
			} catch (NumberFormatException ex) {
				logger.error("Illegal checked list got.");
				request.setAttribute("errorMessage", "Invalid parameter.");
				request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			} catch(ServiceServerException ex) {
				logger.error("Can't delete contact.", ex);
				request.setAttribute("errorMessage", "Internal server error. Sorry.");
				request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			}
		}
		else {
			logger.error("Null checked list got.");
			request.setAttribute("errorMessage", "No contacts selected.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		}
	}

}
