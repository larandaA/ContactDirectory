package by.bsu.contactdirectory.action;

import by.bsu.contactdirectory.entity.*;
import by.bsu.contactdirectory.service.ContactService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.IllegalFormatException;

public class UpdateContactAction implements Action {

	private ContactService contactService = new ContactService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		Contact contact = new Contact();
		String buf = request.getParameter("id");
		try {
			contact.setId(Integer.parseInt(buf));
		} catch (IllegalFormatException ex) {
			ex.printStackTrace();
			response.sendError(400, "Invalid parameters");
			return;
		}
		contact.setFirstName(request.getParameter("firstName"));
		contact.setLastName(request.getParameter("lastName"));
		contact.setPatronymic(request.getParameter("patronymic"));
		buf = request.getParameter("gender");
		if (buf == null || buf.isEmpty()) {
			contact.setGender(null);
		} else {
			try {
				contact.setGender(Gender.valueOf(buf.toUpperCase()));
			} catch (IllegalArgumentException ex) {
				//
				response.sendError(400, "Invalid parameters");
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
				//
				response.sendError(400, "Invalid parameters");
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
				//
				response.sendError(400, "Invalid parameters");
				return;
			}
		}
		Address address = new Address();
		address.setCountry(request.getParameter("country"));
		address.setCity(request.getParameter("city"));
		address.setLocalAddress(request.getParameter("localAddress"));
		address.setIndex(request.getParameter("index"));
		address.setContactId(contact.getId());
		contact.setAddress(address);

		Photo photo = new Photo();
		photo.setContactId(contact.getId());
		contact.setPhoto(photo);

		if (contactService.updateContact(contact)){
			response.sendRedirect("http://127.0.0.1:8080/ContactDirectory/");
		} else {
			response.sendError(400, "Invalid parameters");
		}
		
	}

}
