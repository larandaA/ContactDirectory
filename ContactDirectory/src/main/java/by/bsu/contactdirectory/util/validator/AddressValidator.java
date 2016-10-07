package by.bsu.contactdirectory.util.validator;

import by.bsu.contactdirectory.entity.Address;

import java.util.regex.Pattern;

/**
 * Created by Alexandra on 15.09.2016.
 */
public class AddressValidator {

    public static boolean validate(Address address) {
        if (address == null) {
            return true;
        }
        if (!validateSymbolField(address.getCountry())) {
            return false;
        }
        if (!validateSymbolField(address.getCity())) {
            return false;
        }
        return true;
    }

    public static boolean validateSymbolField(String field) {
        if (field == null || field.trim().isEmpty()) {
            return true;
        }
        return true;
    }
}
