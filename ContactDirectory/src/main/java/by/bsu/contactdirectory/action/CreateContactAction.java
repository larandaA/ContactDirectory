package by.bsu.contactdirectory.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.entity.Gender;
import by.bsu.contactdirectory.entity.MaritalStatus;
import by.bsu.contactdirectory.service.CountryService;
import by.bsu.contactdirectory.servlet.Actions;

public class CreateContactAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("action", Actions.SAVE_CONTACT.substring(1));
        //request.setAttribute("dateFormat", dateFormat);
		request.setAttribute("countries", new CountryService().getCountryNames());
        request.setAttribute("marital", MaritalStatus.values());
        request.setAttribute("genders", Gender.values());
		request.getRequestDispatcher("jsp/contact_info.jsp").forward(request, response);
	}

}
