package by.bsu.contactdirectory.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.entity.PhoneType;
import by.bsu.contactdirectory.util.file.FileNameGenerator;
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
		logger.info("CreateContact action requested.");

		try {
			request.setAttribute(ACTION_ATTRIBUTE, Actions.SAVE_CONTACT.substring(1));
			request.setAttribute(COUNTRIES_ATTRIBUTE, countryService.getCountryNames());
			request.setAttribute(CODES_ATTRIBUTE, countryService.getCountryCodes());
			request.setAttribute(MARITAL_ATTRIBUTE, MaritalStatus.values());
			request.setAttribute(GENDERS_ATTRIBUTE, Gender.values());
			request.setAttribute(TYPES_ATTRIBUTE, PhoneType.values());
			request.setAttribute(DEFAULT_PHOTO_ATTRIBUTE, FileNameGenerator.defaultPhotoPath);
			request.getRequestDispatcher(Actions.CONTACT_INFO_JSP).forward(request, response);
		} catch (ServiceServerException ex) {
			logger.error("Can't get list of countries.", ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Internal server error, sorry.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		}
	}

}
