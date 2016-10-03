package by.bsu.contactdirectory.service;

import by.bsu.contactdirectory.dao.ContactDao;
import by.bsu.contactdirectory.dao.DaoException;
import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.util.email.EmailSender;
import by.bsu.contactdirectory.util.email.EmailSenderException;
import by.bsu.contactdirectory.util.template.STRenderer;
import by.bsu.contactdirectory.util.validator.ContactValidator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexandra on 15.09.2016.
 */
public class EmailService {

    public void sendEmails(String[] ids, String topic, String text, String template) throws ServiceClientException, ServiceServerException {
        if (ids == null || ids.length == 0) {
            throw new ServiceClientException();
        }
        if (topic == null || topic.isEmpty()) {
            throw new ServiceClientException();
        }
        if (text == null || text.isEmpty()) {
            throw new ServiceClientException();
        }
        List<Integer> idList= new LinkedList<>();
        for(int i = 0; i < ids.length; i++) {
            try {
                idList.add(Integer.parseInt(ids[i]));
            } catch (NumberFormatException ex) {
                throw new ServiceClientException(ex);
            }
        }
        List<Contact> contactList = new LinkedList<>();
        for (int id : idList) {
            try {
                contactList.add(ContactDao.getInstance().findContactById(id));
            } catch (DaoException ex) {
                throw new ServiceServerException(ex);
            }
        }
        try {
            if (template == null || template.isEmpty()) {
                List<String> emailList = new LinkedList<>();
                for (Contact contact : contactList) {
                    if (contact.getEmail() != null && !contact.getEmail().isEmpty()) {
                        emailList.add(contact.getEmail());
                    }
                }
                EmailSender.sendEmailsToContacts(emailList.toArray(new String[emailList.size()]), text, topic);
            } else {
                for (Contact contact : contactList) {
                    if (contact.getEmail() == null || contact.getEmail().isEmpty()) {
                        continue;
                    }
                    String newText = STRenderer.render(template, contact.getFirstName()+ " " + contact.getLastName(), text);
                    EmailSender.sendEmailToContact(contact.getEmail(), newText, topic);
                }
            }
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
