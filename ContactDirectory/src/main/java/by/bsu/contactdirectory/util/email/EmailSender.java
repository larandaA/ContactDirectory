package by.bsu.contactdirectory.util.email;


import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


/**
 * Created by Alexandra on 15.09.2016.
 */
public class EmailSender {

    private static String username;
    private static String password;
    private static String adminEmail;
    private static Properties props;

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
        //parseResourceBundle(resourceBundle);
        EmailSender.username = "javatestar@gmail.com";
        EmailSender.password = "testTEST12";
        EmailSender.adminEmail = "larandaansil@gmail.com";
        EmailSender.props = new Properties();
        EmailSender.props.put("mail.smtp.auth", "true");
        EmailSender.props.put("mail.smtp.starttls.enable", "true");
        EmailSender.props.put("mail.smtp.host", "smtp.gmail.com");
        EmailSender.props.put("mail.smtp.port", "587");
        EmailSender.inited = true;
    }

    private static void parseResourceBundle(ResourceBundle resourceBundle) {
        ResourceBundleParser.parse(resourceBundle);
    }


    public static boolean sendEmailToAdmin(String text, String topic) {
        if (!inited) {
            //add
            return false;
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
            mex.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean sendEmailsToContacts(String[] to, String text, String topic) {
        if (!inited) {
            //add
            return false;
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
            mex.printStackTrace();
            return false;
        }

        return true;
    }

}
