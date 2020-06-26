package com.example.covidhelper;

public class User {
    private String full_name, email, phone, adress;

    public User() {

    }

    public User(String full_name, String email, String phone, String adress) {
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.adress = adress;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
