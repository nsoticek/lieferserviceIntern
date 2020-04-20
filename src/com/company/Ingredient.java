package com.company;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {

    private String type;
    private double extraPrice;

    public Ingredient(String type, double extraPrice) {
        this.type = type;
        this.extraPrice = extraPrice;
    }

    public String getType() {
        return type;
    }

    public double getExtraPrice() {
        return extraPrice;
    }
}
