package ru.lom.d.services;

import ru.lom.d.data.Contact;

import java.util.List;

public interface ContactService {
    void addContact(Contact contact);

    List<Contact> getAllContact();

    void removeContact(Contact contact);

    List<Contact> getContactsByRegex(String searchParam);

    void updateContact(Contact selectedContact);
}
