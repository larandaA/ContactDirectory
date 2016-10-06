package by.bsu.contactdirectory.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.service.ServiceServerException;
import by.bsu.contactdirectory.servlet.Actions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsu.contactdirectory.entity.Gender;
import by.bsu.contactdirectory.entity.MaritalStatus;
import by.bsu.contactdirectory.service.CountryService;

public class SearchEditAction implements Action {

	private static Logger logger = LogManager.getLogger(SearchContactsAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("SearchEdit action requested.");

		try {
			request.setAttribute(COUNTRIES_ATTRIBUTE, new CountryService().getCountryNames());
			request.setAttribute(MARITAL_ATTRIBUTE, MaritalStatus.values());
			request.setAttribute(GENDERS_ATTRIBUTE, Gender.values());
			request.getRequestDispatcher(Actions.SEARCH_JSP).forward(request, response);
		} catch (ServiceServerException ex) {
			logger.error("Failed to get countries.", ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Internal server error. Sorry.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		}
	}

}
