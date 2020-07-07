package com.example.covidhelper;

import java.util.HashMap;
import java.util.Map;

public class Shop {
    private String name;
    private String phone_number;
    private String email;
    private String address;
    Map<String, Schedule> schedule = new HashMap<>();

    public Shop () {
    }

    public Shop(String name, String phone_number, String email, String address) {
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, Schedule> schedule) {
        this.schedule = schedule;
    }
}
