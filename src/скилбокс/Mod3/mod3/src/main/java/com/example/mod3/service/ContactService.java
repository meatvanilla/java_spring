package com.example.mod3.service;

import com.example.mod3.model.Contact;
import com.example.mod3.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        return (List<Contact>) contactRepository.findAll();
    }

    public void addContact(Contact contact) {
        contactRepository.save(contact);
    }

    public void updateContact(Contact contact) {
        contactRepository.save(contact);
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    public Contact getContactById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }
}
