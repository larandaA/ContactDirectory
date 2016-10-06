package by.bsu.contactdirectory.util.validator;

import by.bsu.contactdirectory.entity.Attachment;

import java.util.regex.Pattern;

/**
 * Created by Alexandra on 15.09.2016.
 */
public class AttachmentValidator {

    public static boolean validate(Attachment attachment) {
        if (attachment == null) {
            return true;
        }
        if (!validateName(attachment.getName())) {
            return false;
        }
        if (attachment.getId() == 0 && !validatePath(attachment.getPath())) {
            return false;
        }
        return true;
    }

    public static boolean validateName(String name) {
        if(name == null || name.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean validatePath(String path) {
        if (path == null || path.trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
