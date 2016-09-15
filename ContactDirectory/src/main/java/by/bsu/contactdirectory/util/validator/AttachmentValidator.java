package by.bsu.contactdirectory.util.validator;

import by.bsu.contactdirectory.entity.Attachment;

import java.util.regex.Pattern;

/**
 * Created by Alexandra on 15.09.2016.
 */
public class AttachmentValidator {

    private static final String PATH_PATTERN = "(?:[a-zA-Z]\\:)\\\\([\\w-]+\\\\)*\\w([\\w-.])+";

    private static Pattern pathPattern = Pattern.compile(PATH_PATTERN);

    public static boolean validate(Attachment attachment) {
        if (attachment == null) {
            return true;
        }
        return true;
    }

    public static boolean validateName(String name) {
        if(name == null || name.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean validatePath(String path) {
        if (path == null || path.isEmpty()) {
            return false;
        }

        return pathPattern.matcher(path).matches();
    }
}
