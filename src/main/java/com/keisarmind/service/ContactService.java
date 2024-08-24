package com.keisarmind.service;

import com.keisarmind.dao.ContatoDAO;
import com.keisarmind.model.Contact;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactService {
    private static final Logger logger = Logger.getLogger(ContactService.class.getName());
    private final ContatoDAO contatoDAO;

    public ContactService(ContatoDAO contatoDAO) {
        this.contatoDAO = contatoDAO;
    }

    public List<Contact> getAllContacts() {
        logger.log(Level.INFO, "Fetching all contacts");
        return contatoDAO.getAllcontacts();
    }

    public Optional<Contact> findContactById(int id) {
        logger.log(Level.INFO, "Finding contact with ID: {0}", id);
        Contact contact = contatoDAO.findContactById(id);
        if (contact == null) {
            logger.log(Level.WARNING, "No contact found with ID: {0}", id);
            return Optional.empty();
        }
        return Optional.of(contact);
    }

    public boolean saveContact(Contact contact) {
        logger.log(Level.INFO, "Saving new contact: {0}", contact.getName());
        if (isValidContact(contact)) {
            contatoDAO.saveContact(contact);
            logger.log(Level.INFO, "Contact saved successfully.");
            return true;
        } else {
            logger.log(Level.WARNING, "Invalid contact data: {0}", contact);
            return false;
        }
    }

    public boolean updateContact(Contact contact) {
        logger.log(Level.INFO, "Updating contact with ID: {0}", contact.getId());
        if (isValidContact(contact) && contatoDAO.findContactById(contact.getId()) != null) {
            contatoDAO.updateContact(contact);
            logger.log(Level.INFO, "Contact updated successfully.");
            return true;
        } else {
            logger.log(Level.WARNING, "Failed to update contact. Either contact is invalid or does not exist.");
            return false;
        }
    }

    public boolean deleteContact(int id) {
        logger.log(Level.INFO, "Deleting contact with ID: {0}", id);
        if (contatoDAO.findContactById(id) != null) {
            contatoDAO.deleteContact(id);
            logger.log(Level.INFO, "Contact deleted successfully.");
            return true;
        } else {
            logger.log(Level.WARNING, "Failed to delete contact. Contact with ID: {0} does not exist.", id);
            return false;
        }
    }

    private boolean isValidContact(Contact contact) {
        if (contact == null) {
            return false;
        }
        // Example of basic validation
        return contact.getName() != null && !contact.getName().isEmpty() &&
                contact.getEmail() != null && !contact.getEmail().isEmpty() &&
                contact.getAge() > 0;
    }
}
