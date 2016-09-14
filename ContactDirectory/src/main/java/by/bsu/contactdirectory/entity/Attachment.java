package by.bsu.contactdirectory.entity;

import java.util.Calendar;

/**
 * Created by Alexandra on 04.09.2016.
 */
public class Attachment {

    private int id;
    private String path;
    private String name;
    private Calendar downloadDate;
    private String comment;
    private int contactId;

    private Contact contact;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(Calendar downloadDate) {
        this.downloadDate = downloadDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
