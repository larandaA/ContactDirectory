package by.bsu.contactdirectory.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.service.ContactService;

public class ContactListAction implements Action {
	
	private ContactService contactService = new ContactService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		String buf = request.getParameter("page");
		if (buf != null) {
			try {
				page = Integer.parseInt(buf);
			} catch (IllegalArgumentException ex) {
				//
				response.sendError(400, "No such page");
				return;
			}
		}
		List<Contact> contacts = contactService.getContactList(page);
		if(contacts.isEmpty()) {
			response.sendError(400, "No such page");
		}
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        int pageAmount = contactService.getPageAmount();
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
