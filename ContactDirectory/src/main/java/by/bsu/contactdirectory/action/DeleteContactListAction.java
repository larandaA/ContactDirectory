package by.bsu.contactdirectory.action;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.service.ContactService;

public class DeleteContactListAction implements Action {
	
	private ContactService contactService = new ContactService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] strIds = request.getParameterValues("checked");
		if (strIds != null && strIds.length > 0){
			LinkedList<Integer> ids = new LinkedList<>();
			for(int i = 0; i < strIds.length; i++) {
				ids.add(Integer.parseInt(strIds[i]));
			}
			contactService.deleteContactList(ids);
			response.sendRedirect("http://127.0.0.1:8080/ContactDirectory/");
		}
		else {
			response.sendError(400, "No contacts selected");
		}
	}

}
