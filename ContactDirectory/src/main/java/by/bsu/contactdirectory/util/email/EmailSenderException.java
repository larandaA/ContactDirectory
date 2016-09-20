package by.bsu.contactdirectory.util.email;

/**
 * Created by Alexandra on 20.09.2016.
 */
public class EmailSenderException extends Exception {
    public EmailSenderException() {
        super();
    }

    public EmailSenderException(String message) {
        super(message);
    }

    public EmailSenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailSenderException(Throwable cause) {
        super(cause);
    }
}
