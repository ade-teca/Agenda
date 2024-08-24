package com.keisarmind.dao;

import com.keisarmind.model.Contact;

import java.util.List;

public interface ContatoDAO {
    List<Contact> getAllcontacts();

    Contact findContactById(int id);

    void saveContact(Contact contact);

    void updateContact(Contact contact);

    void deleteContact(int id);


}
