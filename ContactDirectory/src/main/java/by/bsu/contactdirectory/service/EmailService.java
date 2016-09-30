package by.bsu.contactdirectory.service;

import by.bsu.contactdirectory.dao.ContactDao;
import by.bsu.contactdirectory.dao.DaoException;
import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.util.email.EmailSender;
import by.bsu.contactdirectory.util.email.EmailSenderException;
import by.bsu.contactdirectory.util.validator.ContactValidator;

import java.util.List;

/**
 * Created by Alexandra on 15.09.2016.
 */
public class EmailService {

    public void sendEmails(String emails, String topic, String text) throws ServiceClientException, ServiceServerException {
        if (emails == null || emails.isEmpty()) {
            throw new ServiceClientException();
        }
        if (topic == null || topic.isEmpty()) {
            throw new ServiceClientException();
        }
        if (text == null || text.isEmpty()) {
            throw new ServiceClientException();
        }
        String[] emailArray = emails.split(", ");
        for (int i = 0; i < emailArray.length; i++) {
            emailArray[i] = emailArray[i].trim();
            if (!ContactValidator.validateEmail(emailArray[i])) {
                throw new ServiceClientException("One of emails is not valid.");
            }
        }
        try {
            EmailSender.sendEmailsToContacts(emailArray, text, topic);
        } catch (EmailSenderException ex) {
            throw new ServiceServerException(ex);
        }
    }

    public void sendBirthdayList() throws ServiceServerException {
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
            try {
                EmailSender.sendEmailToAdmin(message.toString(), "Daily notification from ContactDirectory");
            } catch (EmailSenderException ex) {
                throw new ServiceServerException(ex);
            }
        } catch (DaoException ex) {
            throw new ServiceServerException(ex);
        }
    }
}
