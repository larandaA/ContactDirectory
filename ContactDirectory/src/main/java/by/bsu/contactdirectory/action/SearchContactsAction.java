package by.bsu.contactdirectory.action;

import by.bsu.contactdirectory.entity.Gender;
import by.bsu.contactdirectory.entity.MaritalStatus;
import by.bsu.contactdirectory.entity.SearchObject;
import by.bsu.contactdirectory.service.SearchService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchContactsAction implements Action {

	private SearchService searchService = new SearchService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SearchObject so = new SearchObject();
		so.setFirstName(request.getParameter("firstName"));
		so.setLastName(request.getParameter("lastName"));
		so.setPatronymic(request.getParameter("patronymic"));
		String buf = request.getParameter("gender");
		if (buf != null && !buf.isEmpty()) {
			try {
				so.setGender(Gender.valueOf(buf.toUpperCase()));
			} catch (IllegalArgumentException ex) {
				//
				response.sendError(400, "Invalid parameters");
				return;
			}
		}
		so.setCitizenship(request.getParameter("citizenship"));
		buf = request.getParameter("maritalStatus");
		if (buf != null && !buf.isEmpty()) {
			try {
				so.setMaritalStatus(MaritalStatus.valueOf(buf.toUpperCase()));
			} catch (IllegalArgumentException ex) {
				//
				response.sendError(400, "Invalid parameters");
				return;
			}
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		buf = request.getParameter("birthDateBigger");
		if (buf != null && !buf.isEmpty()) {
			try{
				Calendar birthDateBigger = Calendar.getInstance();
				birthDateBigger.setTime(dateFormat.parse(buf));
				so.setBirthDateBigger(birthDateBigger);
			} catch (ParseException ex) {
				//
				response.sendError(400, "Invalid parameters");
				return;
			}
		}
		buf = request.getParameter("birthDateLess");
		if (buf != null && !buf.isEmpty()) {
			try{
				Calendar birthDateLess = Calendar.getInstance();
				birthDateLess.setTime(dateFormat.parse(buf));
				so.setBirthDateLess(birthDateLess);
			} catch (ParseException ex) {
				//
				response.sendError(400, "Invalid parameters");
				return;
			}
		}
		so.setCountry(request.getParameter("country"));
		so.setCity(request.getParameter("city"));
		so.setLocalAddress(request.getParameter("localAddress"));
		so.setIndex(request.getParameter("index"));

		request.getSession().removeAttribute("searchObject");
		request.getSession().removeAttribute("page");
		if (!searchService.isResultEmpty(so)) {
			request.getSession().setAttribute("searchObject", so);
		}

		response.sendRedirect("http://127.0.0.1:8080/ContactDirectory/ContactList");
	}

}
