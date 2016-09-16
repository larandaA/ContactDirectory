package by.bsu.contactdirectory.connectionpool;

import java.util.IllegalFormatException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Alexandra on 18.08.2016.
 */
class ResourceBundleParser {

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

    static void parse(ConnectionPool cp, ResourceBundle resourceBundle){
       // try {
       //     cp.setMinimumConnections(Integer.valueOf(resourceBundle.getString(MINIMUM_CONNECTIONS_KEY)));
       // }catch (IllegalFormatException ex){
            cp.setMinimumConnections(DEFAULT_MINIMUM_CONNECTIONS);
       // }
      //  try {
      //      cp.setMaximumConnections(Integer.valueOf(resourceBundle.getString(MAXIMUM_CONNECTIONS_KEY)));
      //  }catch (IllegalFormatException ex){
            cp.setMaximumConnections(DEFAULT_MAXIMUM_CONNECTIONS);
      //  }

      //  String databaseUrl = resourceBundle.getString(DATABASE_URL_KEY);
     //   if (databaseUrl != null) {
      //      cp.setUrl(FIRST_PART_URL + databaseUrl);
            cp.setUrl(FIRST_PART_URL + "localhost:3306/alexandra_ryzhevich_db");
       // }else{
      //      throw new RuntimeException("Not set database url");
       // }

       /* String user = resourceBundle.getString(USER_KEY);
        if (user == null || user.isEmpty()){
            user = DEFAULT_USER;
        }
        String password = resourceBundle.getString(PASSWORD_KEY);
        if (password == null || password.isEmpty()){
            password = DEFAULT_PASSWORD;
        }
        String useUnicode = resourceBundle.getString(USE_UNICODE_KEY);
        if (useUnicode == null || useUnicode.isEmpty()){
            useUnicode = DEFAULT_USE_UNICODE;
        }
        String charsetEncidung = resourceBundle.getString(CHARSET_ENCODING_KEY);
        if (charsetEncidung == null || charsetEncidung.isEmpty()){
            charsetEncidung = DEFAULT_CHARSET_ENCODING;
        }*/

        Properties connectionProperties = new Properties();
        connectionProperties.setProperty(USER_KEY, DEFAULT_USER/*user*/);
        connectionProperties.setProperty(PASSWORD_KEY, DEFAULT_PASSWORD/*password*/);
        connectionProperties.setProperty(USE_UNICODE_KEY, DEFAULT_USE_UNICODE/*useUnicode*/);
        connectionProperties.setProperty(CHARSET_ENCODING_KEY, DEFAULT_CHARSET_ENCODING/*charsetEncidung*/);
        cp.setConnectionProperties(connectionProperties);
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
