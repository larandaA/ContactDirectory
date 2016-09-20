package by.bsu.contactdirectory.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.service.ServiceServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.entity.SearchObject;
import by.bsu.contactdirectory.service.ContactService;
import by.bsu.contactdirectory.service.SearchService;

public class ContactListAction implements Action {

	private static Logger logger = LogManager.getLogger(ContactListAction.class);
	private ContactService contactService = new ContactService();
	private SearchService searchService = new SearchService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		String buf = request.getParameter("page");
		if (buf != null && !buf.isEmpty()) {
			try {
				page = Integer.parseInt(buf);
			} catch (IllegalArgumentException ex) {
				logger.error("Can't go to page.", ex);
				request.setAttribute("errorMessage", "No such page: " + buf);
				request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
				return;
			}
		} else {
			Object pageObj = request.getSession().getAttribute("page");
			if (pageObj != null) {
				page = (Integer) pageObj;
			}
		}

		int pageAmount = 0;
		List<Contact> contacts = null;
		Object soObject = request.getSession().getAttribute("searchObject");
		try {
			if (soObject == null) {
				contacts = contactService.getContactList(page);
				pageAmount = contactService.getPageAmount();

				logger.info("Requesting contact list from page: " + page);
			} else {
				SearchObject so = (SearchObject) soObject;
				contacts = searchService.searchContacts(so, page);
				pageAmount = searchService.getPageAmount(so);

				logger.info("Requesting for search result from page: " + page);
			}
		} catch (ServiceServerException ex) {
			logger.error("Can't get contact list", ex);
			request.setAttribute("errorMessage", "Server internal error, sorry.");
			request.getRequestDispatcher("jsp/err.jsp").forward(request, response);
		}


		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

		request.getSession().removeAttribute("page");
		request.getSession().setAttribute("page", page);

        request.setAttribute("contacts", contacts);
        request.setAttribute("dateFormat", dateFormat);
        request.setAttribute("currentPage", page);
        request.setAttribute("nextPage", page + 1);
        request.setAttribute("previousPage", page - 1);
        request.setAttribute("availableNext", page < pageAmount);
        request.setAttribute("availablePrevious", page > 1);
        request.getRequestDispatcher("jsp/contact_list.jsp").forward(request, response);
		
	}

}
