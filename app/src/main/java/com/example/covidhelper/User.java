package com.example.covidhelper;

public class User {
    private String full_name, email, phone, address;

    public User() {
    }

    public User(String full_name, String email, String phone, String address) {
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String getFullName() {
        return full_name;
    }

    public void setFullName(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String Address) {
        this.address = address;
    }
}
