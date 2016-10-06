package by.bsu.contactdirectory.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.servlet.Actions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StartPageAction implements Action {

	private static Logger logger = LogManager.getLogger(StartPageAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("StartPage action requested.");

		request.getSession().removeAttribute(PAGE_ATTRIBUTE);
		request.getSession().removeAttribute(SEARCH_OBJECT_ATTRIBUTE);
		request.getRequestDispatcher(Actions.START_JSP).forward(request, response);
	}

}
