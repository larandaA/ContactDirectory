package by.bsu.contactdirectory.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.service.ServiceServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsu.contactdirectory.entity.Gender;
import by.bsu.contactdirectory.entity.MaritalStatus;
import by.bsu.contactdirectory.service.CountryService;

public class SearchEditAction implements Action {

	private static Logger logger = LogManager.getLogger(SearchContactsAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("countries", new CountryService().getCountryNames());
		} catch (ServiceServerException ex) {
			logger.error("Failed to get countries.", ex);
			request.setAttribute("errorMessage", "Internal server error. Sorry.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			return;
		}
        request.setAttribute("marital", MaritalStatus.values());
        request.setAttribute("genders", Gender.values());
		logger.info("Search edit page requested.");
		request.getRequestDispatcher("jsp/search.jsp").forward(request, response);		
	}

}
