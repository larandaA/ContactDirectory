package by.bsu.contactdirectory.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {

	String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
	String PAGE_ATTRIBUTE = "page";
	String SEARCH_OBJECT_ATTRIBUTE = "searchObject";
	String CONTACTS_ATTRIBUTE = "contacts";
	String DATE_FORMAT_ATTRIBUTE = "dateFormat";
	String PAGE_AMOUNT_ATTRIBUTE = "pageAmount";
	String CURRENT_PAGE_ATTRIBUTE = "currentPage";
	String NEXT_PAGE_ATTRIBUTE = "nextPage";
	String PREVIOUS_PAGE_ATTRIBUTE = "previousPage";
	String AVAILABLE_NEXT_ATTRIBUTE = "availableNext";
	String AVAILABLE_PREVIOUS_ATTRIBUTE = "availablePrevious";
	String ACTION_ATTRIBUTE = "action";
	String COUNTRIES_ATTRIBUTE = "countries";
	String CODES_ATTRIBUTE = "codes";
	String MARITAL_ATTRIBUTE = "marital";
	String GENDERS_ATTRIBUTE = "genders";
	String TYPES_ATTRIBUTE = "types";
	String DEFAULT_PHOTO_ATTRIBUTE = "defaultPhoto";
	String ID_ATTRIBUTE = "id";
	String CHECKED_ATTRIBUTE = "checked";
	String FIRST_NAME_ATTRIBUTE = "firstName";
	String LAST_NAME_ATTRIBUTE = "lastName";
	String PATRONYMIC_ATTRIBUTE = "patronymic";
	String MARITAL_STATUS_ATTRIBUTE = "maritalStatus";
	String GENDER_ATTRIBUTE = "gender";
	String ATT_FILE_ATTRIBUTE = "attFile";
	String PHOTO_ATTRIBUTE = "photo";
	String EMAIL_ATTRIBUTE = "email";
	String WEB_SITE_ATTRIBUTE = "webSite";
	String BIRTH_DATE_ATTRIBUTE = "birthDate";
	String CITIZENSHIP_ATTRIBUTE = "citizenship";
	String COUNTRY_ATTRIBUTE = "country";
	String CITY_ATTRIBUTE = "city";
	String LOCAL_ADDRESS_ATTRIBUTE = "localAddress";
	String INDEX_ATTRIBUTE = "index";
	String DELETE_FILE_ATTRIBUTE = "deleteFileWithPath";
	String NO_PHOTO_ATTRIBUTE = "noPhoto";
	String DELETE_ATT_ATTRIBUTE = "deleteAttWithId";
	String CREATE_PHONE_ATTRIBUTE = "createPhone";
	String UPDATE_PHONE_ATTRIBUTE = "updatePhone";
	String CREATE_ATT_ATTRIBUTE = "createAtt";
	String UPDATE_ATT_ATTRIBUTE = "updateAtt";
	String PLACE_OF_WORK_ATTRIBUTE = "placeOfWork";
	String DELETE_PHONE_ATTRIBUTE = "deletePhoneWithId";
	String DELETE_PHOTO_ATTRIBUTE = "deletePhotoWithPath";
	String TOPIC_ATTRIBUTE = "topic";
	String TEXT_ATTRIBUTE = "text";
	String TEMPLATE_ATTRIBUTE = "template";
	String BIRTH_DATE_BIGGER_ATTRIBUTE = "birthDateBigger";
	String BIRTH_DATE_LESS_ATTRIBUTE = "birthDateLess";
	String EMAILS_ATTRIBUTE = "emails";
	String TEMPLATES_ATTRIBUTE = "templates";
	String IDS_ATTRIBUTE = "ids";
	String CONTACT_ATTRIBUTE = "contact";
	String TIME_FORMAT_ATTRIBUTE = "timeFormat";
	String PHOTO_NAME_PARAMETER = "photoName";
	String FILE_NAME_PARAMETER = "fileName";

	String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";
	String DEFAULT_TIME_FORMAT = "dd.MM.yyyy HH:mm";
	
	void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
