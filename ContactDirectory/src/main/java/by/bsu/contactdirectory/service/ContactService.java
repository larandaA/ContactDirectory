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
import by.bsu.contactdirectory.util.file.FileDeleteManager;
import by.bsu.contactdirectory.util.file.FileNameGenerator;
import by.bsu.contactdirectory.util.preparator.ContactPreparator;
import by.bsu.contactdirectory.util.validator.ContactValidator;

public class ContactService {
	
	public static final int contactAmountPerPage = 2;

	public ContactService() {}
	
	public int getPageAmount() throws ServiceServerException {
		int amount = 0;
		try {
			amount = ContactDao.getInstance().count();
		} catch (DaoException ex) {
			throw new ServiceServerException(ex);
		}
		if (amount % contactAmountPerPage == 0) {
			return amount / contactAmountPerPage;
		}
		return amount / contactAmountPerPage + 1;
	}
	
	public List<Contact> getContactList(int page) throws ServiceServerException {
		List<Contact> contacts = null;
		int offset = (page - 1) * contactAmountPerPage;
		try {
			contacts = ContactDao.getInstance().findContactList(offset, contactAmountPerPage);
		} catch (DaoException ex) {
			throw new ServiceServerException(ex);
		}
		for(Contact contact : contacts) {
			try {
				contact.setAddress(AddressDao.getInstance().findById(contact.getId()));
			} catch (DaoException ex) {
				throw new ServiceServerException(ex);
			}
		}
		return contacts;
	}
	
	public Contact getContact(int id) throws ServiceServerException {
		Contact contact = null;
		try {
			contact = ContactDao.getInstance().findContactById(id);
			if (contact == null) {
				return null;
			}
		} catch (DaoException ex) {
			throw new ServiceServerException(ex);
		}
		try {
			contact.setAddress(AddressDao.getInstance().findById(contact.getId()));
		} catch (DaoException ex) {
			throw new ServiceServerException(ex);
		}
		try {
			contact.setPhoto(PhotoDao.getInstance().findById(contact.getId()));
		} catch (DaoException ex) {
			throw new ServiceServerException(ex);
		}
		try {
			contact.setPhones(PhoneDao.getInstance().findByContact(contact.getId()));
		} catch (DaoException ex) {
			contact.setPhones(new LinkedList<Phone>());
			throw new ServiceServerException(ex);
		}
		try {
			contact.setAttachments(AttachmentDao.getInstance().findByContact(contact.getId()));
		} catch (DaoException ex) {
			contact.setAttachments(new LinkedList<Attachment>());
			throw new ServiceServerException(ex);
		}
		return contact;
	}
	
	public void deleteContact(int id) throws ServiceServerException {
		List<String> deleteFiles = new LinkedList<>();
		try {
			List<Attachment> atts = AttachmentDao.getInstance().findByContact(id);
			for (Attachment att : atts) {
				deleteFiles.add(FileNameGenerator.filesPath + att.getPath());
			}
			deleteFiles.add(FileNameGenerator.photosPath + PhotoDao.getInstance().findById(id).getPath());
			ContactDao.getInstance().delete(id);
		} catch (DaoException ex) {
			throw new ServiceServerException(ex);
		}
		FileDeleteManager.deleteFiles(deleteFiles);
	}
	
	public void deleteContactList(List<Integer> ids) throws ServiceServerException {
		List<String> deleteFiles = new LinkedList<>();
		try {
			for(int id : ids) {
				List<Attachment> atts = AttachmentDao.getInstance().findByContact(id);
				for (Attachment att : atts) {
					deleteFiles.add(FileNameGenerator.filesPath + att.getPath());
				}
				deleteFiles.add(FileNameGenerator.photosPath + PhotoDao.getInstance().findById(id).getPath());
			}
			ContactDao.getInstance().deleteContacts(ids);
		} catch (DaoException ex) {
			throw new ServiceServerException(ex);
		}
		FileDeleteManager.deleteFiles(deleteFiles);
	}
	
	public void createContact(Contact contact, List<String> deleteFiles) throws ServiceServerException, ServiceClientException {
		if (!ContactValidator.validate(contact)) {
			throw new ServiceClientException("Contact parameters for creating are not valid.");
		}
		ContactPreparator.prepare(contact);

		try {
			ContactDao.getInstance().create(contact);

		} catch (DaoException ex) {
			throw new ServiceServerException(ex);
		}
		FileDeleteManager.deleteFiles(deleteFiles);
	}

	public void updateContact(Contact contact, List<String> deleteFiles, List<Integer> deletePhones, List<Integer> deleteAttachments) throws ServiceServerException, ServiceClientException {
		if (!ContactValidator.validate(contact)) {
			throw new ServiceClientException("Contact parameters for updating are not valid.");
		}
		ContactPreparator.prepare(contact);

		try {
			ContactDao.getInstance().update(contact, deletePhones, deleteAttachments);
		} catch (DaoException ex) {
			throw new ServiceServerException(ex);
		}
		FileDeleteManager.deleteFiles(deleteFiles);
	}
}
