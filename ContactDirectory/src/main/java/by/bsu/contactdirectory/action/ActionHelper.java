package by.bsu.contactdirectory.action;

import by.bsu.contactdirectory.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Alexandra on 06.10.2016.
 */
public class ActionHelper {

    public static Attachment parseAttachment(String value) throws ActionException {
        if (value == null || value.isEmpty()) {
            return null;
        }
        String[] params = value.split("\\|", 4);
        if(params.length != 4) {
            throw new ActionException(String.format("Invalid attachment parameters: %s", Arrays.deepToString(params)));
        }
        Attachment att = new Attachment();
        if (!params[0].isEmpty()) {
            try {
                att.setId(Integer.parseInt(params[0]));
            } catch (NumberFormatException ex) {
                throw new ActionException(String.format("Invalid attachment id: %s", params[0]));
            }
        }
        att.setName(params[1]);
        att.setPath(params[2]);
        att.setComment(params[3]);
        return att;
    }

    public static Phone parsePhone(String value) throws ActionException {
        if (value == null || value.isEmpty()) {
            return null;
        }
        String[] params = value.split("\\|", 6);
        if(params.length != 6) {
            throw new ActionException(String.format("Invalid phone parameters: %s", Arrays.deepToString(params)));
        }
        Phone phone = new Phone();
        if (!params[0].isEmpty()) {
            try {
                phone.setId(Integer.parseInt(params[0]));
            } catch (NumberFormatException ex) {
                throw new ActionException(String.format("Invalid phone id: %s", params[0]));
            }
        }
        if (!params[1].isEmpty()) {
            try {
                phone.setCountryCode(Integer.parseInt(params[1]));
            } catch (NumberFormatException ex) {
                throw new ActionException(String.format("Invalid country code: %s", params[1]));
            }
        }
        if (!params[2].isEmpty()) {
            try {
                phone.setOperatorCode(Integer.parseInt(params[2]));
            } catch (NumberFormatException ex) {
                throw new ActionException(String.format("Invalid operator code: %s", params[2]));
            }
        }
        phone.setPhoneNumber(params[3]);
        if (!params[4].isEmpty()) {
            try {
                phone.setType(PhoneType.valueOf(params[4].toUpperCase()));
            } catch (IllegalArgumentException ex) {
                throw new ActionException(String.format("Invalid phone type: %s", params[4]));
            }
        }
        phone.setComment(params[5]);
        return phone;
    }

    public static Gender getGender(HttpServletRequest request) throws ActionException {
        String buf = request.getParameter(Action.GENDER_ATTRIBUTE);
        return getGenderFromString(buf);
    }

    public static Gender getGenderFromString(String buf) throws ActionException {
        Gender gender = null;
        if (buf != null && !buf.isEmpty()) {
            try {
                gender = Gender.valueOf(buf.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new ActionException(String.format("Invalid gender got: %s", buf));
            }
        }
        return gender;
    }

    public static MaritalStatus getMaritalStatus(HttpServletRequest request) throws ActionException {
        String buf = request.getParameter(Action.MARITAL_STATUS_ATTRIBUTE);
        return getMaritalStatusFromString(buf);
    }

    public static MaritalStatus getMaritalStatusFromString(String buf) throws ActionException {
        MaritalStatus maritalStatus = null;
        if (buf != null && !buf.isEmpty()) {
            try {
                maritalStatus = MaritalStatus.valueOf(buf.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new ActionException(String.format("Invalid marital status got: %s", buf));
            }
        }
        return maritalStatus;
    }

    public static Calendar getCalendar(HttpServletRequest request, String paramName) throws ActionException {
        String buf = request.getParameter(paramName);
        return getCalendarFromString(buf, paramName);
    }

    public static Calendar getCalendarFromString(String buf, String paramName) throws ActionException {
        Calendar cal = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(Action.DEFAULT_DATE_FORMAT);
        if (buf != null && !buf.isEmpty()) {
            try{
                cal = Calendar.getInstance();
                cal.setTime(dateFormat.parse(buf));
            } catch (ParseException ex) {
                throw new ActionException(String.format("Invalid %s bigger limit got: ", paramName, buf));
            }
        }
        return cal;
    }
}
