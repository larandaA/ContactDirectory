package by.bsu.contactdirectory.util.preparator;

import by.bsu.contactdirectory.entity.*;
import by.bsu.contactdirectory.util.file.FileNameGenerator;

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
        preparePhoto(contact.getPhoto());
        if (contact.getPhones() != null) {
            contact.getPhones().forEach(ContactPreparator::preparePhone);
        }
        if (contact.getAttachments() != null) {
            contact.getAttachments().forEach(ContactPreparator::prepareAttachment);
        }
    }

    private static void prepareAttachment(Attachment attachment) {
        attachment.setPath(prepareString(attachment.getPath()));
        attachment.setComment(prepareString(attachment.getComment()));
    }

    private static void prepareAddress(Address address) {
        address.setIndex(prepareString(address.getIndex()));
        address.setLocalAddress(prepareString(address.getLocalAddress()));
        address.setCity(prepareString(address.getCity()));
        address.setCountry(prepareString(address.getCountry()));
    }

    private static void preparePhone(Phone phone) {
        phone.setPhoneNumber(prepareString(phone.getPhoneNumber()));
        phone.setComment(prepareString(phone.getComment()));
    }

    private static void preparePhoto(Photo photo) {
        if (photo == null) {
            return;
        }
        if (photo.getPath() == null || photo.getPath().isEmpty()) {
            photo.setPath(FileNameGenerator.defaultPhotoPath);
        }
    }

    private static String prepareString(String string) {
        if (string == null || string.trim().isEmpty()) {
            return null;
        }
        return string.trim();
    }
}
