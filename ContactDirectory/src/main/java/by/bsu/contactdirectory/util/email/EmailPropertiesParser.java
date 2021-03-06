package by.bsu.contactdirectory.util.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Alexandra on 16.09.2016.
 */
public class EmailPropertiesParser {

    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String ADMIN_EMAIL_KEY = "adminEmail";
    private static final String MAIL_SMTP_STARTTLS_ENABLE_KEY = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_AUTH_KEY = "mail.smtp.auth";
    private static final String MAIL_SMTP_HOST_KEY = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT_KEY = "mail.smtp.port";

    private static final String DEFAULT_USERNAME = "javatestar@gmail.com";
    private static final String DEFAULT_PASSWORD = "testTEST12";
    private static final String DEFAULT_ADMIN_EMAIL = "larandaansil@gmail.com";
    private static final String DEFAULT_MAIL_SMTP_STARTTLS_ENABLE = "true";
    private static final String DEFAULT_MAIL_SMTP_AUTH = "true";
    private static final String DEFAULT_MAIL_SMTP_HOST = "smtp.gmail.com";
    private static final String DEFAULT_MAIL_SMTP_PORT = "587";


    static void parse(String pathToProperties) throws IOException {
        InputStream input = null;
        try {
            ClassLoader classLoader = EmailPropertiesParser.class.getClassLoader();
            input = classLoader.getResourceAsStream(pathToProperties);
            Properties properties = new Properties();
            properties.load(input);
            String username = properties.getProperty(USERNAME_KEY);
            if (username == null || username.isEmpty()) {
                EmailSender.setUsername(DEFAULT_USERNAME);
            } else {
                EmailSender.setUsername(username);
            }

            String password = properties.getProperty(PASSWORD_KEY);
            if (password == null || password.isEmpty()) {
                EmailSender.setPassword(DEFAULT_PASSWORD);
            } else {
                EmailSender.setPassword(password);
            }

            String adminEmail = properties.getProperty(ADMIN_EMAIL_KEY);
            if (adminEmail == null || adminEmail.isEmpty()) {
                EmailSender.setAdminEmail(DEFAULT_ADMIN_EMAIL);
            } else {
                EmailSender.setAdminEmail(adminEmail);
            }

            Properties props = new Properties();

            String startTlsEnable = properties.getProperty(MAIL_SMTP_STARTTLS_ENABLE_KEY);
            if (startTlsEnable == null || startTlsEnable.isEmpty()) {
                props.put(MAIL_SMTP_STARTTLS_ENABLE_KEY, DEFAULT_MAIL_SMTP_STARTTLS_ENABLE);
            } else {
                props.put(MAIL_SMTP_STARTTLS_ENABLE_KEY, startTlsEnable);
            }

            String auth = properties.getProperty(MAIL_SMTP_AUTH_KEY);
            if (auth == null || auth.isEmpty()) {
                props.put(MAIL_SMTP_AUTH_KEY, DEFAULT_MAIL_SMTP_AUTH);
            } else {
                props.put(MAIL_SMTP_AUTH_KEY, auth);
            }

            String host = properties.getProperty(MAIL_SMTP_HOST_KEY);
            if (host == null || host.isEmpty()) {
                props.put(MAIL_SMTP_HOST_KEY, DEFAULT_MAIL_SMTP_HOST);
            } else {
                props.put(MAIL_SMTP_HOST_KEY, host);
            }

            String port = properties.getProperty(MAIL_SMTP_PORT_KEY);
            if (port == null || port.isEmpty()) {
                props.put(MAIL_SMTP_PORT_KEY, DEFAULT_MAIL_SMTP_PORT);
            } else {
                props.put(MAIL_SMTP_PORT_KEY, port);
            }

            EmailSender.setProps(props);

        } finally {
            try {
                input.close();
            } catch (IOException e) {}
        }
    }
}
