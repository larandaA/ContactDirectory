package by.bsu.contactdirectory.connectionpool;

import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Alexandra on 18.08.2016.
 */
class PoolPropertiesParser {

    private static final String USER_KEY = "user";
    private static final String PASSWORD_KEY = "password";
    private static final String USE_UNICODE_KEY = "useUnicode";
    private static final String CHARSET_ENCODING_KEY = "characterEncoding";
    private static final String DATABASE_URL_KEY = "databaseUrl";
    private static final String FIRST_PART_URL = "jdbc:mysql://";
    private static final String MINIMUM_CONNECTIONS_KEY = "minimumConnections";
    private static final String MAXIMUM_CONNECTIONS_KEY = "maximumConnections";

    private static final int DEFAULT_MINIMUM_CONNECTIONS = 10;
    private static final int DEFAULT_MAXIMUM_CONNECTIONS = 50;
    private static final String DEFAULT_USER = "alex_ryzhevich";
    private static final String DEFAULT_PASSWORD = "password";
    private static final String DEFAULT_USE_UNICODE = "true";
    private static final String DEFAULT_CHARSET_ENCODING = "UTF-8";

    static void parse(ConnectionPool cp, String pathToProperties) {
        InputStream input = null;
        try {
            ClassLoader classLoader = PoolPropertiesParser.class.getClassLoader();
            input = classLoader.getResourceAsStream(pathToProperties);
            Properties properties = new Properties();
            properties.load(input);
            try {
                cp.setMinimumConnections(Integer.valueOf(properties.getProperty(MINIMUM_CONNECTIONS_KEY)));
            } catch (IllegalFormatException ex) {
                cp.setMinimumConnections(DEFAULT_MINIMUM_CONNECTIONS);
            }
            try {
                cp.setMaximumConnections(Integer.valueOf(properties.getProperty(MAXIMUM_CONNECTIONS_KEY)));
            } catch (IllegalFormatException ex) {
                cp.setMaximumConnections(DEFAULT_MAXIMUM_CONNECTIONS);
            }

            String databaseUrl = properties.getProperty(DATABASE_URL_KEY);
            if (databaseUrl != null) {
                cp.setUrl(FIRST_PART_URL + databaseUrl);
            } else {
                throw new RuntimeException("Database url is not set.");
            }

            String user = properties.getProperty(USER_KEY);
            if (user == null || user.isEmpty()) {
                user = DEFAULT_USER;
            }
            String password = properties.getProperty(PASSWORD_KEY);
            if (password == null || password.isEmpty()) {
                password = DEFAULT_PASSWORD;
            }
            String useUnicode = properties.getProperty(USE_UNICODE_KEY);
            if (useUnicode == null || useUnicode.isEmpty()) {
                useUnicode = DEFAULT_USE_UNICODE;
            }
            String charsetEncoding = properties.getProperty(CHARSET_ENCODING_KEY);
            if (charsetEncoding == null || charsetEncoding.isEmpty()) {
                charsetEncoding = DEFAULT_CHARSET_ENCODING;
            }

            Properties connectionProperties = new Properties();
            connectionProperties.setProperty(USER_KEY, user);
            connectionProperties.setProperty(PASSWORD_KEY, password);
            connectionProperties.setProperty(USE_UNICODE_KEY, useUnicode);
            connectionProperties.setProperty(CHARSET_ENCODING_KEY, charsetEncoding);
            cp.setConnectionProperties(connectionProperties);

            input.close();
        } catch (IOException e) { }
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) { }
            }
        }

    }
}
