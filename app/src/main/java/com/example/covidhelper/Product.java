package com.example.covidhelper;

public class Product {

    private String name;
    private double quantity;
    private String measure_unit;
    private double price;

    public Product () {
    }

    public Product(String name, double quantity, String measure_unit, double price) {
        this.name = name;
        this.quantity = quantity;
        this.measure_unit = measure_unit;
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

    public String getMeasureUnit() {
        return measure_unit;
    }

    public void setMeasureUnit(String measure_unit) {
        this.measure_unit = measure_unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
