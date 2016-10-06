package by.bsu.contactdirectory.action;

import by.bsu.contactdirectory.entity.Gender;
import by.bsu.contactdirectory.entity.MaritalStatus;
import by.bsu.contactdirectory.entity.SearchObject;
import by.bsu.contactdirectory.service.SearchService;

import by.bsu.contactdirectory.service.ServiceServerException;
import by.bsu.contactdirectory.servlet.Actions;
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
		logger.info("SearchContacts action requested.");

		try {
			SearchObject so = new SearchObject();
			setSearchObjectParams(request, so);

			request.getSession().removeAttribute(SEARCH_OBJECT_ATTRIBUTE);
			request.getSession().removeAttribute(PAGE_ATTRIBUTE);

			if (!searchService.isResultEmpty(so)) {
				request.getSession().setAttribute(SEARCH_OBJECT_ATTRIBUTE, so);
				logger.info("Search object successfully created.");
				response.sendRedirect(Actions.CONTACT_LIST.substring(1));
			} else {
				setNotFoundAttributes(request);
				request.getRequestDispatcher(Actions.CONTACT_LIST_JSP).forward(request, response);
			}

		} catch (ServiceServerException ex) {
			logger.error("Failed to count result amount.", ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Internal server error.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		} catch(ActionException ex) {
			logger.error(ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Invalid parameters.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		}
	}

	private void setSearchObjectParams(HttpServletRequest request, SearchObject so) throws ActionException {
		so.setFirstName(request.getParameter("firstName"));
		so.setLastName(request.getParameter("lastName"));
		so.setPatronymic(request.getParameter("patronymic"));
		so.setGender(ActionHelper.getGender(request));
		so.setCitizenship(request.getParameter("citizenship"));
		so.setMaritalStatus(ActionHelper.getMaritalStatus(request));
		so.setBirthDateBigger(ActionHelper.getCalendar(request, BIRTH_DATE_BIGGER_ATTRIBUTE));
		so.setBirthDateLess(ActionHelper.getCalendar(request, BIRTH_DATE_LESS_ATTRIBUTE));
		so.setCountry(request.getParameter("country"));
		so.setCity(request.getParameter("city"));
		so.setLocalAddress(request.getParameter("localAddress"));
		so.setIndex(request.getParameter("index"));
	}

	private void setNotFoundAttributes(HttpServletRequest request) {
		String errorMessage = "No result found.";
		int page = 0;
		int pageAmount = 0;
		request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, errorMessage);
		request.setAttribute(DATE_FORMAT_ATTRIBUTE, new SimpleDateFormat(DEFAULT_DATE_FORMAT));
		request.setAttribute(PAGE_AMOUNT_ATTRIBUTE, pageAmount);
		request.setAttribute(CURRENT_PAGE_ATTRIBUTE, page);
		request.setAttribute(NEXT_PAGE_ATTRIBUTE, page + 1);
		request.setAttribute(PREVIOUS_PAGE_ATTRIBUTE, page - 1);
		request.setAttribute(AVAILABLE_NEXT_ATTRIBUTE, page < pageAmount);
		request.setAttribute(AVAILABLE_PREVIOUS_ATTRIBUTE, page > 1);
	}

}
