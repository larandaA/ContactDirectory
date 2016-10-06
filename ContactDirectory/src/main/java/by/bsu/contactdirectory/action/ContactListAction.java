package by.bsu.contactdirectory.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.service.ServiceServerException;
import by.bsu.contactdirectory.servlet.Actions;
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
		logger.info("ContactList action requested.");
		try {
			int page = getRequestedPage(request);
			String errorMessage = getErrorMessage(request);
			List<Contact> contacts = new LinkedList<>();
			int pageAmount = getContactList(contacts, page, request);

			if (contacts.isEmpty()) {
				if (page == 1) {
					page = 0;
					errorMessage = "There's no contacts in directory.";
				} else {
					errorMessage = "This page is empty.";
				}
			}

			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, errorMessage);
			request.setAttribute(CONTACTS_ATTRIBUTE, contacts);
			request.setAttribute(DATE_FORMAT_ATTRIBUTE, new SimpleDateFormat(DEFAULT_DATE_FORMAT));
			request.setAttribute(PAGE_AMOUNT_ATTRIBUTE, pageAmount);
			request.setAttribute(CURRENT_PAGE_ATTRIBUTE, page);
			request.setAttribute(NEXT_PAGE_ATTRIBUTE, page + 1);
			request.setAttribute(PREVIOUS_PAGE_ATTRIBUTE, page - 1);
			request.setAttribute(AVAILABLE_NEXT_ATTRIBUTE, page < pageAmount);
			request.setAttribute(AVAILABLE_PREVIOUS_ATTRIBUTE, page > 1);
			request.getRequestDispatcher(Actions.CONTACT_LIST_JSP).forward(request, response);
		} catch (ActionException ex) {
			logger.error(ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Invalid parameters.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		} catch (ServiceServerException ex) {
			logger.error("Can't get contact list", ex);
			request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, "Server internal error, sorry.");
			request.getRequestDispatcher(Actions.ERR_JSP).forward(request, response);
		}
	}

	private int getRequestedPage(HttpServletRequest request) throws ActionException {
		int page = 1;
		String buf = request.getParameter(PAGE_ATTRIBUTE);
		if (buf != null && !buf.isEmpty()) {
			try {
				page = Integer.parseInt(buf);
			} catch (IllegalArgumentException ex) {
				throw new ActionException(String.format("No such page: %s", buf), ex);
			}
		} else {
			Object pageObj = request.getSession().getAttribute(PAGE_ATTRIBUTE);
			if (pageObj != null) {
				page = (Integer) pageObj;
			}
		}
		request.getSession().removeAttribute(PAGE_ATTRIBUTE);
		request.getSession().setAttribute(PAGE_ATTRIBUTE, page);
		return page;
	}

	private String getErrorMessage(HttpServletRequest request) throws ActionException {
		String errorMessage = null;
		Object errMsgObj = request.getSession().getAttribute(ERROR_MESSAGE_ATTRIBUTE);
		if (errMsgObj != null) {
			request.getSession().removeAttribute(ERROR_MESSAGE_ATTRIBUTE);
			errorMessage = (String) errMsgObj;
		}
		return errorMessage;
	}

	private int getContactList(List<Contact> contacts, int page, HttpServletRequest request) throws ServiceServerException {
		int pageAmount = 0;
		Object soObject = request.getSession().getAttribute(SEARCH_OBJECT_ATTRIBUTE);
		if (soObject == null) {
			contacts.addAll(contactService.getContactList(page));
			pageAmount = contactService.getPageAmount();
			logger.info(String.format("Requesting contact list from page: %d", page));
		} else {
			SearchObject so = (SearchObject) soObject;
			contacts.addAll(searchService.searchContacts(so, page));
			pageAmount = searchService.getPageAmount(so);
			logger.info(String.format("Requesting for search result from page: %d", page));
		}
		return pageAmount;
	}
}
