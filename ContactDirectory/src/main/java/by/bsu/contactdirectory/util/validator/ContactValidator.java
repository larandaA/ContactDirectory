package by.bsu.contactdirectory.util.validator;

import java.util.regex.Pattern;
import org.apache.commons.validator.routines.EmailValidator;

import by.bsu.contactdirectory.entity.Contact;

public class ContactValidator {
	
	private static final String NAME_PATTERN = "^[a-zA-z]+([ '-][a-zA-Z]+)*$";
	
	private static Pattern namePattern = Pattern.compile(NAME_PATTERN);
	
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
		if (!validateWebSite(contact.getWebSite())) {
			return false;
		}
		if (!validateEmail(contact.getEmail())) {
			return false;
		}
		if (!validatePlaceOfWork(contact.getPlaceOfWork())) {
			return false;
		}
		
		return true;
	}
	
	public static boolean validateRequiredName(String name) {
		if (name == null || name.isEmpty()) {
			return false;
		}
		if (name.length() > 40) {
			return false;
		}
		return namePattern.matcher(name).matches();
	}
	
	public static boolean validateNotRequiredName(String name) {
		if (name == null || name.isEmpty()) {
			return true;
		}
		if (name.length() > 40) {
			return false;
		}
		return namePattern.matcher(name).matches();
	}
	
	public static boolean validateWebSite(String webSite) {
		if (webSite == null || webSite.isEmpty()) {
			return true;
		}
		if (webSite.length() > 200) {
			return false;
		}
		return true;
	}
	
	public static boolean validateEmail(String email) {
		if (email == null || email.isEmpty()) {
			return true;
		}
		if (email.length() > 100) {
			return false;
		}
		return EmailValidator.getInstance().isValid(email);
	}
	
	public static boolean validatePlaceOfWork(String work) {
		if (work == null || work.isEmpty()) {
			return true;
		}
		if (work.length() > 100) {
			return false;
		}
		return true;
	}
}
