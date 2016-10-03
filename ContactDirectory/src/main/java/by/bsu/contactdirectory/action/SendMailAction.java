package by.bsu.contactdirectory.action;

import by.bsu.contactdirectory.service.EmailService;

import by.bsu.contactdirectory.service.ServiceClientException;
import by.bsu.contactdirectory.service.ServiceServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendMailAction implements Action {

	private EmailService emailService = new EmailService();
	private static Logger logger = LogManager.getLogger(SendMailAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String[] ids = request.getParameterValues("id");
		String topic = request.getParameter("topic");
		String text = request.getParameter("text");
		String template = request.getParameter("template");
		try {
			emailService.sendEmails(ids, topic, text, template);
			logger.info(String.format("Emais send to contacts: %s", Arrays.deepToString(ids)));
			response.sendRedirect("ContactList");
		} catch (ServiceServerException ex) {
			logger.error("Failed to send emails.", ex);
			request.setAttribute("errorMessage", "Failed to send emails. Sorry.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		} catch(ServiceClientException ex) {
			logger.error(String.format("Invalid parameters got: %s", Arrays.deepToString(ids)), ex);
			request.setAttribute("errorMessage", "Invalid parameters.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		}
	}

}
