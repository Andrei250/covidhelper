package com.example.covidhelper;

public class Volunteer {
    private String name;
    private String phone_number;
    private String email;

    public Volunteer() {
    }

    public Volunteer(String name, String phone_number, String email) {
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
