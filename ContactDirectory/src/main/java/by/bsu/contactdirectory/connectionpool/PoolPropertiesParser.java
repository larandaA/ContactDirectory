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
        try {
            ClassLoader classLoader = PoolPropertiesParser.class.getClassLoader();
            InputStream input = classLoader.getResourceAsStream(pathToProperties);
            Properties properties = new Properties();
            properties.load(input);
            try {
                cp.setMinimumConnections(Integer.valueOf(properties.getProperty(MINIMUM_CONNECTIONS_KEY)));
            } catch (IllegalFormatException ex) {
                System.out.println("!!!error11111");
                cp.setMinimumConnections(DEFAULT_MINIMUM_CONNECTIONS);
            }
            try {
                cp.setMaximumConnections(Integer.valueOf(properties.getProperty(MAXIMUM_CONNECTIONS_KEY)));
            } catch (IllegalFormatException ex) {
                System.out.println("!!!error2222");
                cp.setMaximumConnections(DEFAULT_MAXIMUM_CONNECTIONS);
            }

            String databaseUrl = properties.getProperty(DATABASE_URL_KEY);
            if (databaseUrl != null) {
                cp.setUrl(FIRST_PART_URL + databaseUrl);
                //cp.setUrl(FIRST_PART_URL + "localhost:3306/alexandra_ryzhevich_db");
            } else {
                throw new RuntimeException("Not set database url");
            }

            String user = properties.getProperty(USER_KEY);
            if (user == null || user.isEmpty()) {
                System.out.println("!!!error3333");
                user = DEFAULT_USER;
            }
            String password = properties.getProperty(PASSWORD_KEY);
            if (password == null || password.isEmpty()) {
                System.out.println("!!!error4444");
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
            connectionProperties.setProperty(USER_KEY, /*DEFAULT_USER*/user);
            connectionProperties.setProperty(PASSWORD_KEY, /*DEFAULT_PASSWORD*/password);
            connectionProperties.setProperty(USE_UNICODE_KEY, /*DEFAULT_USE_UNICODE*/useUnicode);
            connectionProperties.setProperty(CHARSET_ENCODING_KEY, /*DEFAULT_CHARSET_ENCODING*/charsetEncoding);
            cp.setConnectionProperties(connectionProperties);

            input.close();
        } catch (IOException e) {

        }

    }

    private static boolean validateUser(String user){
        return true;
    }

    private static boolean validatePassword(String password){
        return true;
    }

    private static boolean validateUseUnicode(String useUnicode){
        return true;
    }

    private static boolean validateCharsetEncoding(String charsetEncoding){
        return true;
    }

    private static boolean validate(String str, String pattern){
        if (str == null){
            return false;
        }
        return true;
    }
}
