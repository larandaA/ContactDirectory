package by.bsu.contactdirectory.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.entity.PhoneType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsu.contactdirectory.entity.Gender;
import by.bsu.contactdirectory.entity.MaritalStatus;
import by.bsu.contactdirectory.service.CountryService;
import by.bsu.contactdirectory.service.ServiceServerException;
import by.bsu.contactdirectory.servlet.Actions;

public class CreateContactAction implements Action {

	private CountryService countryService = new CountryService();
	private static Logger logger = LogManager.getLogger(CreateContactAction.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		request.setAttribute("action", Actions.SAVE_CONTACT.substring(1));
        //request.setAttribute("dateFormat", dateFormat);
		try {
			request.setAttribute("countries", countryService.getCountryNames());
			request.setAttribute("codes", countryService.getCountryCodes());
		} catch (ServiceServerException ex) {
			logger.error("Can't get list of coutries.", ex);
			request.setAttribute("errorMessage", "Internal server error, sorry.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
			return;
		}
		logger.info("Requesting create contact page.");
        request.setAttribute("marital", MaritalStatus.values());
        request.setAttribute("genders", Gender.values());
		request.setAttribute("types", PhoneType.values());
		request.setAttribute("defaultPhoto", "img/contacts/default.jpg");
		request.getRequestDispatcher("jsp/contact_info.jsp").forward(request, response);
	}

}
