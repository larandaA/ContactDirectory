package by.bsu.contactdirectory.util.email;


import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


/**
 * Created by Alexandra on 15.09.2016.
 */
public class EmailSender {

    public static boolean sendSingle(String from, String to, String text, String topic) {
        String host = "localhost";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(topic);
            message.setText(text);

            System.out.println("sending email to admin");
            Transport.send(message);

        }catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean sendMany(String from, String[] to, String text, String topic) {

        String host = "localhost";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            InternetAddress[] addresses = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                addresses[i] = new InternetAddress(to[i]);
            }
            message.addRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(topic);
            message.setText(text);

            System.out.println("sending email to group");
            Transport.send(message);
        }catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }

        return true;
    }

}
