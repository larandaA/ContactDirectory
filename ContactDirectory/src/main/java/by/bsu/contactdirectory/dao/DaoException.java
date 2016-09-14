package by.bsu.contactdirectory.dao;

/**
 * Created by Alexandra on 04.09.2016.
 */
public class DaoException extends Exception {

    public DaoException() {}

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

}
