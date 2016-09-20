package by.bsu.contactdirectory.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.service.ServiceClientException;
import by.bsu.contactdirectory.service.ServiceServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsu.contactdirectory.entity.*;
import by.bsu.contactdirectory.service.ContactService;

public class SaveContactAction implements Action {
	
	private ContactService contactService = new ContactService();
	private static Logger logger = LogManager.getLogger(SaveContactAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("page");
		request.getSession().removeAttribute("searchObject");

		Contact contact = new Contact();
		contact.setFirstName(request.getParameter("firstName"));
		contact.setLastName(request.getParameter("lastName"));
		contact.setPatronymic(request.getParameter("patronymic"));		
		String buf = request.getParameter("gender");
		if (buf == null || buf.isEmpty()) {
			contact.setGender(null);
		} else {
			try {
				contact.setGender(Gender.valueOf(buf.toUpperCase()));
			} catch (IllegalArgumentException ex) {
				logger.error("Invalid gender got: " + buf);
				request.setAttribute("errorMessage", "Invalid parameter.");
				request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
				return;
			}
		}
		contact.setCitizenship(request.getParameter("citizenship"));
		buf = request.getParameter("maritalStatus");
		if (buf == null || buf.isEmpty()) {
			contact.setMaritalStatus(null);
		} else {
			try {
				contact.setMaritalStatus(MaritalStatus.valueOf(buf.toUpperCase()));
			} catch (IllegalArgumentException ex) {
				logger.error("Invalid marital status got:"+ buf);
				request.setAttribute("errorMessage", "Invalid parameter.");
				request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
				return;
			}
		}		
		contact.setEmail(request.getParameter("email"));
		contact.setWebSite(request.getParameter("webSite"));
		contact.setPlaceOfWork(request.getParameter("placeOfWork"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		buf = request.getParameter("birthDate");
		if (buf == null || buf.isEmpty()) {
			contact.setBirthDate(null);
		} else {
			try{
				Calendar birthDate = Calendar.getInstance();
				birthDate.setTime(dateFormat.parse(buf));
				contact.setBirthDate(birthDate);
			} catch (ParseException ex) {
				logger.error("Invalid birth date got: " + buf);
				request.setAttribute("errorMessage", "Invalid parameter.");
				request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
				return;
			}
		}
		Address address = new Address();
		address.setCountry(request.getParameter("country"));
		address.setCity(request.getParameter("city"));
		address.setLocalAddress(request.getParameter("localAddress"));
		address.setIndex(request.getParameter("index"));
		contact.setAddress(address);

		Photo photo = new Photo();
		contact.setPhoto(photo);
		
		try {
			contactService.createContact(contact);
			logger.info("New contact created successfully.");
			response.sendRedirect("http://127.0.0.1:8080/ContactDirectory/");
		} catch (ServiceServerException ex) {
			logger.error("Failed to create contact." + ex);
			request.setAttribute("errorMessage", "Internal server error. Sorry.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		} catch (ServiceClientException ex) {
			logger.error("Validation failed.");
			request.setAttribute("errorMessage", "Invalid parameters.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		}
	}

}
