package com.company.models;

public class DishIngredient {

    private int dishId;
    private int ingredientId;

    public DishIngredient(int dishId, int ingredientId) {
        this.dishId = dishId;
        this.ingredientId = ingredientId;
    }

    public int getDishId() {
        return dishId;
    }

    public int getIngredientId() {
        return ingredientId;
    }
}
