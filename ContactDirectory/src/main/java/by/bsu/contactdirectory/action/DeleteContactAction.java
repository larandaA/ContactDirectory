package by.bsu.contactdirectory.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.service.ServiceServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsu.contactdirectory.service.ContactService;

public class DeleteContactAction implements Action {
	
	private ContactService contactService = new ContactService();
	private static Logger logger = LogManager.getLogger(DeleteContactAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String buf = request.getParameter("id");
		if (buf == null) {
			logger.error("Null id got.");
			request.setAttribute("errorMessage", "No contact selected.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		} else {
			try {
				int id = Integer.parseInt(buf);
				contactService.deleteContact(id);
				logger.info(String.format("Contact deleted. Id: %d", id));
				response.sendRedirect("ContactList");
			} catch (IllegalArgumentException ex) {
				logger.error("Illegal id got: " + buf);
				request.setAttribute("errorMessage", "Invalid parameter.");
				request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			} catch (ServiceServerException ex) {
				logger.error("Can't delete contact.", ex);
				request.setAttribute("errorMessage", "Internal server error. Sorry.");
				request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			}
		}		
	}

}
