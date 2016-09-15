package by.bsu.contactdirectory.util.validator;

import by.bsu.contactdirectory.entity.Address;

import java.util.regex.Pattern;

/**
 * Created by Alexandra on 15.09.2016.
 */
public class AddressValidator {

    private static final String SYMBOL_PATTERN = "^[a-zA-z]+([ '-][a-zA-Z]+)*$";
    private static final String COMBINED_PATTERN = "^[a-zA-Z0-9' -\\.]+$";

    private static Pattern symbolPattern = Pattern.compile(SYMBOL_PATTERN);
    private static Pattern combinedPattern = Pattern.compile(COMBINED_PATTERN);

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
        if (!validateCombinedField(address.getLocalAddress())) {
            return false;
        }
        if (!validateCombinedField(address.getIndex())) {
            return false;
        }
        return true;
    }

    public static boolean validateSymbolField(String field) {
        if (field == null || field.isEmpty()) {
            return true;
        }
        return symbolPattern.matcher(field).matches();
    }

    public static boolean validateCombinedField(String field) {
        if (field == null || field.isEmpty()) {
            return true;
        }
        return combinedPattern.matcher(field).matches();
    }
}
