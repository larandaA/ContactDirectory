package by.bsu.contactdirectory.util.validator;

import by.bsu.contactdirectory.entity.Phone;

import java.util.regex.Pattern;

/**
 * Created by Alexandra on 15.09.2016.
 */
public class PhoneValidator {

    private static final String PHONE_PATTERN = "^\\s*[\\\\+]?\\d+(\\s?-?\\s?(\\(\\d+\\)|\\d+))*\\s*$";

    private static Pattern phonePattern = Pattern.compile(PHONE_PATTERN);

    public static boolean validate(Phone phone){
        if (phone == null) {
            return true;
        }
        if (!validatePhoneNumber(phone.getPhoneNumber())) {
            return false;
        }
        if (!validateOperatorCode(phone.getOperatorCode())) {
            return false;
        }
        if (!validateCountryCode(phone.getCountryCode())) {
            return false;
        }

        return true;
    }

    public static boolean validatePhoneNumber(String number) {
        if (number == null || number.trim().isEmpty() || number.trim().length() > 20) {
            return false;
        }
        return phonePattern.matcher(number).matches();
    }

    public static boolean validateOperatorCode(Integer code) {
        if (code == null) {
            return true;
        }
        if (code >= 100000) {
            return false;
        }
        if (code == 0) {
            return false;
        }
        return true;
    }

    public static boolean validateCountryCode(Integer code) {
        if (code == null) {
            return true;
        }
        if (code >= 10000) {
            return false;
        }
        if (code == 0) {
            return false;
        }
        return true;
    }
}
