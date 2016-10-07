package by.bsu.contactdirectory.util.validator;

import java.util.Calendar;
import java.util.regex.Pattern;

import by.bsu.contactdirectory.entity.Attachment;
import by.bsu.contactdirectory.entity.Phone;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;

import by.bsu.contactdirectory.entity.Contact;

public class ContactValidator {

	private static UrlValidator urlValidator = new UrlValidator(new String[] {"http", "https"});
	
	public static boolean validate(Contact contact) {
		if (contact == null) {
			return false;
		}
		if (!validateRequiredName(contact.getFirstName()) 
				|| !validateRequiredName(contact.getLastName())) {
			return false;
		}
		if (!validateNotRequiredName(contact.getPatronymic())) {
			return false;
		}
		if (!validateBirthDate(contact.getBirthDate())) {
			return false;
		}
		if (!validateWebSite(contact.getWebSite())) {
			return false;
		}
		if (!validateEmail(contact.getEmail())) {
			return false;
		}
		if (!validatePlaceOfWork(contact.getPlaceOfWork())) {
			return false;
		}
		if (!AddressValidator.validate(contact.getAddress())) {
			return false;
		}
		if (contact.getPhones() != null) {
			for (Phone phone : contact.getPhones()) {
				if (!PhoneValidator.validate(phone)) {
					return false;
				}
			}
		}
		if (contact.getAttachments() != null) {
			for (Attachment attachment : contact.getAttachments()) {
				if (!AttachmentValidator.validate(attachment)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean validateRequiredName(String name) {
		if (name == null || name.trim().isEmpty()) {
			return false;
		}
		if (name.trim().length() > 40) {
			return false;
		}
		return true;
	}
	
	public static boolean validateNotRequiredName(String name) {
		if (name == null || name.trim().isEmpty()) {
			return true;
		}
		if (name.trim().length() > 40) {
			return false;
		}
		return true;
	}

	public static boolean validateBirthDate(Calendar date) {
		if(date == null) {
			return true;
		}
		Calendar currentDate = Calendar.getInstance();
		if (date.get(Calendar.YEAR) < currentDate.get(Calendar.YEAR)) {
			return true;
		}
		if (date.get(Calendar.YEAR) > currentDate.get(Calendar.YEAR)) {
			return false;
		}
		if (date.get(Calendar.MONTH) < currentDate.get(Calendar.MONTH)) {
			return true;
		}
		if (date.get(Calendar.MONTH) > currentDate.get(Calendar.MONTH)) {
			return false;
		}
		if (date.get(Calendar.DAY_OF_MONTH) > currentDate.get(Calendar.DAY_OF_MONTH)) {
			return false;
		}
		return true;
	}
	
	public static boolean validateWebSite(String webSite) {
		if (webSite == null || webSite.trim().isEmpty()) {
			return true;
		}
		if (webSite.trim().length() > 200) {
			return false;
		}
		return urlValidator.isValid(webSite.trim()) || urlValidator.isValid("https://" + webSite.trim());
	}
	
	public static boolean validateEmail(String email) {
		if (email == null || email.trim().isEmpty()) {
			return true;
		}
		if (email.trim().length() > 100) {
			return false;
		}
		return EmailValidator.getInstance().isValid(email.trim());
	}
	
	public static boolean validatePlaceOfWork(String work) {
		if (work == null || work.trim().isEmpty()) {
			return true;
		}
		if (work.trim().length() > 100) {
			return false;
		}
		return true;
	}
}
