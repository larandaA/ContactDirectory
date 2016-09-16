package by.bsu.contactdirectory.util.preparator;

import by.bsu.contactdirectory.entity.Address;
import by.bsu.contactdirectory.entity.Attachment;
import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.entity.Phone;

/**
 * Created by Alexandra on 16.09.2016.
 */
public class ContactPreparator {

    public static void prepare(Contact contact) {
        if (contact == null) {
            return;
        }

        contact.setPatronymic(prepareString(contact.getPatronymic()));
        contact.setWebSite(prepareString(contact.getWebSite()));
        contact.setEmail(prepareString(contact.getEmail()));
        contact.setCitizenship(prepareString(contact.getCitizenship()));
        contact.setPlaceOfWork(prepareString(contact.getPlaceOfWork()));

        prepareAddress(contact.getAddress());
        if (contact.getPhones() != null) {
            contact.getPhones().forEach(ContactPreparator::preparePhone);
        }
        if (contact.getAttachments() != null) {
            contact.getAttachments().forEach(ContactPreparator::prepareAttachment);
        }
    }

    private static void prepareAttachment(Attachment attachment) {
        attachment.setComment(prepareString(attachment.getComment()));
    }

    private static void prepareAddress(Address address) {
        address.setIndex(prepareString(address.getIndex()));
        address.setLocalAddress(prepareString(address.getLocalAddress()));
        address.setCity(prepareString(address.getCity()));
        address.setCountry(prepareString(address.getCountry()));
    }

    private static void preparePhone(Phone phone) {
        phone.setComment(prepareString(phone.getComment()));
    }

    private static String prepareString(String string) {
        if (string == null || string.isEmpty()) {
            return null;
        }
        return string;
    }
}
