package com.company.Controller;

import com.company.DbHelper.DbConnector;
import com.company.DbHelper.DishDb;
import com.company.models.Dish;

public class DishController {

    public static void insertDishAndIngredient(Dish dish, DbConnector dbConnector) {
        DishDb.insertDish(dish, dbConnector);
        DishDb.addIngredientsToDb(dish, dbConnector);
        DishDb.insertDishIngredient(dish, dbConnector);
    }
}
