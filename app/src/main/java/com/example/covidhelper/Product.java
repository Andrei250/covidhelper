package com.example.covidhelper;

public class Product {

    private String name;
    private double quantity;
    private String measure_unity;
    private double price;

    public Product () {
    }

    public Product(String name, double quantity, String measure_unity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.measure_unity = measure_unity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasureUnity() {
        return measure_unity;
    }

    public void setMeasureUnity(String measure_unity) {
        this.measure_unity = measure_unity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
