package com.company.DbHelper;

import com.company.models.DishIngredient;

import java.util.List;

public class DishIngredientRepository implements IRepository<DishIngredient>{
    private DbConnector connector;

    public DishIngredientRepository() {
        this.connector = DbConnector.getInstance();
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public DishIngredient findOne(int id) {
        return null;
    }

    @Override
    public void create(DishIngredient dishIngredient) {
        boolean isInserted = connector.insertData("INSERT INTO `dish_ingredient`(`dish`, `ingredient`)" +
                " VALUES (" + dishIngredient.getDishId() + ", " + dishIngredient.getIngredientId() + ");");
        if (isInserted) {
            System.out.println("Daten wurden aktualisiert");
        }
    }
}
