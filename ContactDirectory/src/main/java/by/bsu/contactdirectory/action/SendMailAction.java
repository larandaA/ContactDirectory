package by.bsu.contactdirectory.action;

import by.bsu.contactdirectory.service.EmailService;

import by.bsu.contactdirectory.service.ServiceClientException;
import by.bsu.contactdirectory.service.ServiceServerException;
import by.bsu.contactdirectory.servlet.Actions;
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
		logger.info("SendMail action requested.");

		String[] ids = request.getParameterValues(ID_ATTRIBUTE);
		String topic = request.getParameter(TOPIC_ATTRIBUTE);
		String text = request.getParameter(TEXT_ATTRIBUTE);
		String template = request.getParameter(TEMPLATE_ATTRIBUTE);
		try {
			emailService.sendEmails(ids, topic, text, template);
			logger.info(String.format("Emais send to contacts: %s", Arrays.deepToString(ids)));
			response.sendRedirect(Actions.CONTACT_LIST.substring(1));
		} catch (ServiceServerException ex) {
			logger.error("Failed to send emails.", ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Failed to send emails. Sorry.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		} catch(ServiceClientException ex) {
			logger.error(String.format("Invalid parameters got: %s", Arrays.deepToString(ids)), ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Invalid parameters.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		}
	}

}
