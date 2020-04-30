package com.company.models;

public class DeliveryPrice {

    private int locationId;
    private double price;

    public DeliveryPrice(int locationId, double price) {
        this.locationId = locationId;
        this.price = price;
    }

    public int getLocationId() {
        return locationId;
    }

    public double getPrice() {
        return price;
    }
}
