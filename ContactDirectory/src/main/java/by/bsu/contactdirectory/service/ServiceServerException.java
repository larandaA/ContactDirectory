package by.bsu.contactdirectory.service;

/**
 * Created by Alexandra on 20.09.2016.
 */
public class ServiceServerException extends Exception {

    public ServiceServerException() {
        super();
    }

    public ServiceServerException(String message) {
        super(message);
    }

    public ServiceServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceServerException(Throwable cause) {
        super(cause);
    }
}
