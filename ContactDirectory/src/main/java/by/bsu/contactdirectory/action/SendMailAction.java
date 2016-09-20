package by.bsu.contactdirectory.action;

import by.bsu.contactdirectory.service.EmailService;

import by.bsu.contactdirectory.service.ServiceClientException;
import by.bsu.contactdirectory.service.ServiceServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendMailAction implements Action {

	private EmailService emailService = new EmailService();
	private static Logger logger = LogManager.getLogger(SendMailAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emails = request.getParameter("emails");
		String topic = request.getParameter("topic");
		String text = request.getParameter("text");
		try {
			emailService.sendEmails(emails, topic, text);
			logger.info("Emais send to contacts: " + emails);
			response.sendRedirect("http://127.0.0.1:8080/ContactDirectory/ContactList");
		} catch (ServiceServerException ex) {
			logger.error("Failed to send emails.", ex);
			request.setAttribute("errorMessage", "Failed to send emails. Sorry.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		} catch(ServiceClientException ex) {
			logger.error("Invalid parameters got." + emails, ex);
			request.setAttribute("errorMessage", "Invalid parameters.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		}
	}

}
