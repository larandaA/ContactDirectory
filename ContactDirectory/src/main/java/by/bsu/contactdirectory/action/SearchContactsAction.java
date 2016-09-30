package by.bsu.contactdirectory.action;

import by.bsu.contactdirectory.entity.Gender;
import by.bsu.contactdirectory.entity.MaritalStatus;
import by.bsu.contactdirectory.entity.SearchObject;
import by.bsu.contactdirectory.service.SearchService;

import by.bsu.contactdirectory.service.ServiceServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchContactsAction implements Action {

	private SearchService searchService = new SearchService();
	private static Logger logger = LogManager.getLogger(SearchContactsAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		SearchObject so = new SearchObject();
		so.setFirstName(request.getParameter("firstName"));
		so.setLastName(request.getParameter("lastName"));
		logger.debug(String.format("LastName: %s", so.getLastName()));
		so.setPatronymic(request.getParameter("patronymic"));
		String buf = request.getParameter("gender");
		if (buf != null && !buf.isEmpty()) {
			try {
				so.setGender(Gender.valueOf(buf.toUpperCase()));
			} catch (IllegalArgumentException ex) {
				logger.error("Invalid gender got: " + buf);
				request.setAttribute("errorMessage", "Invalid parameter.");
				request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
				return;
			}
		}
		so.setCitizenship(request.getParameter("citizenship"));
		buf = request.getParameter("maritalStatus");
		if (buf != null && !buf.isEmpty()) {
			try {
				so.setMaritalStatus(MaritalStatus.valueOf(buf.toUpperCase()));
			} catch (IllegalArgumentException ex) {
				logger.error("Invalid marital status got: " + buf);
				request.setAttribute("errorMessage", "Invalid parameter.");
				request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
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
				logger.error("Invalid birth date bigger limit got: " + buf);
				request.setAttribute("errorMessage", "Invalid parameter.");
				request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
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
				logger.error("Invalid birth date less limit got: " + buf);
				request.setAttribute("errorMessage", "Invalid parameter.");
				request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
				return;
			}
		}
		so.setCountry(request.getParameter("country"));
		so.setCity(request.getParameter("city"));
		so.setLocalAddress(request.getParameter("localAddress"));
		so.setIndex(request.getParameter("index"));

		request.getSession().removeAttribute("searchObject");
		request.getSession().removeAttribute("page");
		try {
			if (!searchService.isResultEmpty(so)) {
				request.getSession().setAttribute("searchObject", so);
			}
			logger.info("Search object successfully created.");
			response.sendRedirect("ContactList");
		} catch (ServiceServerException ex) {
			logger.error("Failed to count result amount.", ex);
			request.setAttribute("errorMessage", "Internal server error.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		}
	}

}
