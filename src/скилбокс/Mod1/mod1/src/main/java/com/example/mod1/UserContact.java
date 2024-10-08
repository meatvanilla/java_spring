package com.example.mod1;

public class UserContact {
    private String name;
    private String phone;
    private String emailAddress;

    public UserContact(String name, String phone, String emailAddress) {
        this.name = name;
        this.phone = phone;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return name + " | " + phone + " | " + emailAddress;
    }
}