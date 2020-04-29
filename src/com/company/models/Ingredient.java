package com.company.models;

public class Ingredient {

    private int id;
    private String type;
    private double extraPrice;

    public Ingredient(String type, double extraPrice) {
        this.type = type;
        this.extraPrice = extraPrice;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getExtraPrice() {
        return extraPrice;
    }
}
