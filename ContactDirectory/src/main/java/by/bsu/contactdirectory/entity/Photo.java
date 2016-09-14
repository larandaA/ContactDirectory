package by.bsu.contactdirectory.entity;

/**
 * Created by Alexandra on 04.09.2016.
 */
public class Photo {

    private String path;
    private int contactId;

    private Contact contact;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
