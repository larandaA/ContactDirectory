package by.bsu.contactdirectory.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.entity.SearchObject;
import by.bsu.contactdirectory.service.ContactService;
import by.bsu.contactdirectory.service.SearchService;

public class ContactListAction implements Action {
	
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
				//
				response.sendError(400, "No such page");
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
		if (soObject == null) {
			contacts = contactService.getContactList(page);
			/*if (contacts.isEmpty()) {
				response.sendError(400, "No such page");
				return;
			}*/
			pageAmount = contactService.getPageAmount();
		} else {
			SearchObject so = (SearchObject) soObject;
			contacts = searchService.searchContacts(so, page);
			/*if (contacts.isEmpty()) {
				response.sendError(400, "No such page");
				return;
			}*/
			pageAmount = searchService.getPageAmount(so);
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
