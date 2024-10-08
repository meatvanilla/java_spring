package com.example.mod1;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserContactService {
    private List<UserContact> contactsList = new ArrayList<>();

    public void addUserContact(UserContact userContact) {
        contactsList.add(userContact);
    }

    public boolean removeContactByEmail(String emailAddress) {
        return contactsList.removeIf(contact -> contact.getEmailAddress().equals(emailAddress));
    }

    public List<UserContact> listAllContacts() {
        return new ArrayList<>(contactsList);
    }
}