package by.bsu.contactdirectory.action;

/**
 * Created by Alexandra on 21.09.2016.
 */
class ActionException extends Exception {

    public ActionException() {
        super();
    }

    public ActionException(String message) {
        super(message);
    }

    public ActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionException(Throwable cause) {
        super(cause);
    }
}
