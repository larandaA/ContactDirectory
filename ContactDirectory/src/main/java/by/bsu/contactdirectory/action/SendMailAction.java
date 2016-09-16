package by.bsu.contactdirectory.action;

import by.bsu.contactdirectory.service.EmailService;
import by.bsu.contactdirectory.util.email.EmailSender;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendMailAction implements Action {

	private EmailService emailService = new EmailService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emails = request.getParameter("emails");
		String topic = request.getParameter("topic");
		String text = request.getParameter("text");
		if (emailService.sendEmails(emails, topic, text)) {
			response.sendRedirect("http://127.0.0.1:8080/ContactDirectory/ContactList");
		} else {
			response.sendError(400, "Invalid parameters");
		}
		//response.getWriter().append(Boolean.toString(EmailSender.sendSingle("larandaansil@gmail.com", "larandaansil@gmail.com", "test", "test")));
	}

}
