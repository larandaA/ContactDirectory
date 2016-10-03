package by.bsu.contactdirectory.util.email;


import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Created by Alexandra on 15.09.2016.
 */
public class EmailSender {

    private static String username;
    private static String password;
    private static String adminEmail;
    private static Properties props;

    private static Logger logger = LogManager.getLogger(EmailSender.class);

    private static boolean inited = false;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        EmailSender.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        EmailSender.password = password;
    }

    public static String getAdminEmail() {
        return adminEmail;
    }

    public static void setAdminEmail(String adminEmail) {
        EmailSender.adminEmail = adminEmail;
    }

    public static Properties getProps() {
        return props;
    }

    public static void setProps(Properties props) {
        EmailSender.props = props;
    }

    public static void init(String pathToProperties) {
        //ResourceBundle resourceBundle = PropertyResourceBundle.getBundle(pathToProperties);
        if (parseProperties(pathToProperties)) {
            EmailSender.inited = true;
            logger.info("Email sender is successfully inited.");
        } else {
            logger.error("Can't initialize email sender.");
        }
        /*EmailSender.username = "javatestar@gmail.com";
        EmailSender.password = "testTEST12";
        EmailSender.adminEmail = "larandaansil@gmail.com";
        EmailSender.props = new Properties();
        EmailSender.props.put("mail.smtp.auth", "true");
        EmailSender.props.put("mail.smtp.starttls.enable", "true");
        EmailSender.props.put("mail.smtp.host", "smtp.gmail.com");
        EmailSender.props.put("mail.smtp.port", "587");*/
    }

    private static boolean parseProperties(String pathToProperties) {
        try {
            EmailPropertiesParser.parse(pathToProperties);
        } catch(IOException ex) {
            return false;
        }
        return true;
    }


    public static void sendEmailToAdmin(String text, String topic) throws EmailSenderException {
        if (!inited) {
            throw new EmailSenderException("Email configurations are not set.");
        }
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(adminEmail));
            message.setSubject(topic);
            message.setText(text);

            Transport.send(message);

        } catch (MessagingException mex) {
            throw new EmailSenderException(mex);
        }
    }

    public static void sendEmailsToContacts(String[] to, String text, String topic) throws EmailSenderException {
        if (!inited) {
            throw new EmailSenderException("Email configurations are not set.");
        }
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            InternetAddress[] addresses = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                addresses[i] = new InternetAddress(to[i]);
            }
            message.addRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(topic);
            message.setText(text);

            Transport.send(message);
        }catch (MessagingException mex) {
            throw new EmailSenderException(mex);
        }
    }

    public static void sendEmailToContact(String to, String text, String topic) throws EmailSenderException {
        if (!inited) {
            throw new EmailSenderException("Email configurations are not set.");
        }
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(topic);
            message.setText(text);

            Transport.send(message);
        }catch (MessagingException mex) {
            throw new EmailSenderException(mex);
        }
    }

}
