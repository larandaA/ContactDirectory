package by.bsu.contactdirectory.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StartPageAction implements Action {

	private static Logger logger = LogManager.getLogger(StartPageAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Start page requested.");
		request.getRequestDispatcher("jsp/start.jsp").forward(request, response);
	}

}
