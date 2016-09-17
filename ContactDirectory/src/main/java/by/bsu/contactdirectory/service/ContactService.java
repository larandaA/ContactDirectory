package by.bsu.contactdirectory.service;

import java.util.LinkedList;
import java.util.List;

import by.bsu.contactdirectory.dao.AddressDao;
import by.bsu.contactdirectory.dao.AttachmentDao;
import by.bsu.contactdirectory.dao.ContactDao;
import by.bsu.contactdirectory.dao.DaoException;
import by.bsu.contactdirectory.dao.PhoneDao;
import by.bsu.contactdirectory.dao.PhotoDao;
import by.bsu.contactdirectory.entity.Attachment;
import by.bsu.contactdirectory.entity.Contact;
import by.bsu.contactdirectory.entity.Phone;
import by.bsu.contactdirectory.entity.Photo;
import by.bsu.contactdirectory.util.preparator.ContactPreparator;
import by.bsu.contactdirectory.util.validator.ContactValidator;

public class ContactService {
	
	public static final int contactAmountPerPage = 2;

	public ContactService() {}
	
	public int getPageAmount() {
		int amount = 0;
		try {
			amount = ContactDao.getInstance().count();
		} catch (DaoException ex) {
			
		}
		if (amount % contactAmountPerPage == 0) {
			return amount / contactAmountPerPage;
		}
		return amount / contactAmountPerPage + 1;
	}
	
	public List<Contact> getContactList(int page) {
		List<Contact> contacts = null;
		int offset = (page - 1) * contactAmountPerPage;
		try {
			contacts = ContactDao.getInstance().findContactList(offset, contactAmountPerPage);
		} catch (DaoException ex) {
			//add log
			contacts = new LinkedList<Contact>();
		}
		for(Contact contact : contacts) {
			try {
				contact.setAddress(AddressDao.getInstance().findById(contact.getId()));
			} catch (DaoException ex) {
				//add log
			}
		}
		return contacts;
	}
	
	public Contact getContact(int id) {
		Contact contact = null;
		try {
			contact = ContactDao.getInstance().findContactById(id);
			if (contact == null) {
				return null;
			}
		} catch (DaoException ex) {
			//add log
			return null;
		}
		try {
			contact.setAddress(AddressDao.getInstance().findById(contact.getId()));
		} catch (DaoException ex) {
			//add log
		}
		try {
			contact.setPhoto(PhotoDao.getInstance().findById(contact.getId()));
		} catch (DaoException ex) {
			//add log
		}
		try {
			contact.setPhones(PhoneDao.getInstance().findByContact(contact.getId(), 0, 5));
		} catch (DaoException ex) {
			contact.setPhones(new LinkedList<Phone>());
			//add log
		}
		try {
			contact.setAttachments(AttachmentDao.getInstance().findByContact(contact.getId(), 0, 5));
		} catch (DaoException ex) {
			contact.setAttachments(new LinkedList<Attachment>());
			//add log
		}
		return contact;
	}
	
	public void deleteContact(int id) {
		try {
			ContactDao.getInstance().delete(id);
		} catch (DaoException ex) {
			//
		}
	}
	
	public void deleteContactList(List<Integer> ids) {
		ids.forEach(this::deleteContact);
	}
	
	public boolean createContact(Contact contact) {
		if (!ContactValidator.validate(contact)) {
			return false;
		}
		ContactPreparator.prepare(contact);

		/// remove this
		if (contact.getPhoto() == null) {
			Photo photo = new Photo();
			photo.setContactId(contact.getId());
			photo.setPath("img/contacts/default.jpg");
			contact.setPhoto(photo);
		}

		try {
			ContactDao.getInstance().create(contact);

		} catch (DaoException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateContact(Contact contact) {
		if (!ContactValidator.validate(contact)) {
			return false;
		}
		ContactPreparator.prepare(contact);

		/// remove this
		if (contact.getPhoto() == null) {
			System.out.println("adding defailt photo");
			Photo photo = new Photo();
			photo.setContactId(contact.getId());
			photo.setPath("img/contacts/default.jpg");
			contact.setPhoto(photo);
		}

		try {
			ContactDao.getInstance().update(contact);
		} catch (DaoException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
}
