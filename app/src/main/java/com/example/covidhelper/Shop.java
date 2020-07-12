package com.example.covidhelper;

import java.util.HashMap;
import java.util.Map;

public class Shop {
    private String name;
    private String phone_number;
    private String email;
    private String address;
    private Map<String, Schedule> schedule = new HashMap<>();
    private Map<String, Product> stock = new HashMap<>();

    public Shop () {
    }

    public Shop(String name, String phone_number, String email, String address,
                Map<String, Schedule> schedule, Map<String, Product> stock) {
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.address = address;
        this.schedule = schedule;
        this.stock = stock;
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

    public void setSchedule(HashMap<String, Schedule> schedule) {
        this.schedule = schedule;
    }

    public Map<String, Product> getStock() {
        return stock;
    }

    public void setStock(HashMap<String, Product> stock) {
        this.stock = stock;
    }
}
