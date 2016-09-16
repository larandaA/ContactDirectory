package by.bsu.contactdirectory.util.validator;

import by.bsu.contactdirectory.entity.Phone;

/**
 * Created by Alexandra on 15.09.2016.
 */
public class PhoneValidator {

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

    public static boolean validatePhoneNumber(Integer number) {
        if (number == null || number < 99999) {
            return false;
        }
        return true;
    }

    public static boolean validateOperatorCode(Integer code) {
        if (code == null) {
            return true;
        }
        if (code >= 100000) {
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
        return true;
    }
}
