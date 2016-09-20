package by.bsu.contactdirectory.service;

/**
 * Created by Alexandra on 20.09.2016.
 */
public class ServiceClientException extends Exception {

    public ServiceClientException() {
        super();
    }

    public ServiceClientException(String message) {
        super(message);
    }

    public ServiceClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceClientException(Throwable cause) {
        super(cause);
    }
}
