package by.bsu.contactdirectory.service;

import by.bsu.contactdirectory.dao.ContactDao;
import by.bsu.contactdirectory.dao.DaoException;
import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.util.email.EmailSender;
import by.bsu.contactdirectory.util.validator.ContactValidator;

import java.util.List;

/**
 * Created by Alexandra on 15.09.2016.
 */
public class EmailService {

    public boolean sendEmails(String emails, String topic, String text) {
        if (emails == null || emails.isEmpty()) {
            return false;
        }
        if (topic == null || topic.isEmpty()) {
            return false;
        }
        if (text == null || text.isEmpty()) {
            return false;
        }
        String[] emailArray = emails.split(", ");
        for (String email : emailArray) {
            if (!ContactValidator.validateEmail(email)) {
                return false;
            }
        }
        return EmailSender.sendEmailsToContacts(emailArray, text, topic);
    }

    public boolean sendBirthdayList() {
        try {
            StringBuilder message = new StringBuilder("");
            List<Contact> contacts = ContactDao.getInstance().findContactListByBirthday();
            if (contacts.isEmpty()) {
                message.append("No one has birthday today.");
            } else {
                message.append("Contacts, who has birthday today:\n");
                for (Contact contact : contacts) {
                    message.append(contact.getFirstName() + contact.getLastName() + "\n");
                }
            }
            return EmailSender.sendEmailToAdmin(message.toString(), "Daily notification from ContactDirectory");
        } catch (DaoException ex) {
            //
            return false;
        }
    }
}
