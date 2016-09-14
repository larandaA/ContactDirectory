package by.bsu.contactdirectory.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.entity.Address;
import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.entity.Gender;
import by.bsu.contactdirectory.entity.MaritalStatus;
import by.bsu.contactdirectory.service.ContactService;

public class SaveContactAction implements Action {
	
	private ContactService contactService = new ContactService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Contact contact = new Contact();
		contact.setFirstName(request.getParameter("firstName"));
		contact.setLastName(request.getParameter("lastName"));
		contact.setPatronymic(request.getParameter("patronymic"));		
		String buf = request.getParameter("gender");
		if (buf == null) {
			contact.setGender(null);
		} else {
			contact.setGender(Gender.valueOf(buf.toUpperCase()));
		}
		contact.setCitizenship(request.getParameter("citizenship"));
		buf = request.getParameter("maritalStatus");
		if (buf == null) {
			contact.setMaritalStatus(null);
		} else {
			contact.setMaritalStatus(MaritalStatus.valueOf(buf.toUpperCase()));
		}		
		contact.setEmail(request.getParameter("email"));
		contact.setWebSite(request.getParameter("webSite"));
		contact.setPlaceOfWork(request.getParameter("placeOfWork"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		buf = request.getParameter("birthDate");
		if (buf == null) {
			contact.setBirthDate(null);
		} else {
			try{
				Calendar birthDate = Calendar.getInstance();
				birthDate.setTime(dateFormat.parse(buf));
				contact.setBirthDate(birthDate);
			} catch (ParseException ex) {
				ex.printStackTrace();
				contact.setBirthDate(null);
			}
		}
		Address address = new Address();
		address.setCountry(request.getParameter("country"));
		address.setCity(request.getParameter("city"));
		address.setLocalAddress(request.getParameter("localAddress"));
		address.setIndex(request.getParameter("index"));
		contact.setAddress(address);
		
		if (contactService.createContact(contact)){
			response.sendRedirect("http://127.0.0.1:8080/ContactDirectory/");
		} else {
			response.sendError(400, "Invalid parameters");
		}
	}

}
