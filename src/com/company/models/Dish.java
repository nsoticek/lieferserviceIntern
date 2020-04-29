package com.company.models;

import com.company.models.Ingredient;

import java.util.ArrayList;

public class Dish {

    private String type;
    private double price;
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    public Dish(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
}