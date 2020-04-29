package com.company.models;

public class Location {

    private String name;
    private double extraPrice;

    public Location(String name, double extraPrice) {
        this.name = name;
        this.extraPrice = extraPrice;
    }

    public String getName() {
        return name;
    }

    public double getExtraPrice() {
        return extraPrice;
    }
}
