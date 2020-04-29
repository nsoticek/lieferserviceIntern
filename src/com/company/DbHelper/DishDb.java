package com.company.DbHelper;

import com.company.models.Dish;
import com.company.models.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DishDb {

    public static boolean insertDish(Dish dish, DbConnector dbConnector) {
        // Set value in table `location`
        boolean isInserted = dbConnector.insertData("INSERT INTO `dish`(`name`, `price`) " +
                "VALUES ('" + dish.getType() + "', " + dish.getPrice() + ")");
        if (isInserted) {
            System.out.println("Daten wurden aktualisiert");
        }
        return isInserted;
    }

    public static void addIngredientsToDb(Dish dish, DbConnector dbConnector) {
        ArrayList<Ingredient> ingredients = dish.getIngredients();

        // Get ID of th current ingredient; if id == 0 --> ingredient does not exist --> insert new
        for (int i = 0; i < ingredients.size(); i++) {
            boolean isIngredientExisting = false;
            int id = getIngredientIdFromDb(ingredients.get(i).getType(), dbConnector);
            if (id != 0) {
                isIngredientExisting = true;
            }

            // Insert ingredient to DB
            if (!isIngredientExisting) {
                boolean isInserted = dbConnector.insertData("INSERT INTO `ingredient`(`name`, `extraprice`) " +
                        "VALUES ('" + ingredients.get(i).getType() + "', " + ingredients.get(i).getExtraPrice() + ")");
                if (isInserted) {
                    System.out.println("Daten wurden aktualisiert");
                }
            }
        }
    }

    public static int getIngredientIdFromDb(String ingredientType, DbConnector dbConnector) {
        // Get the ID of the current ingredient
        int id = 0;
        ResultSet rs = dbConnector.fetchData("SELECT `id` FROM `ingredient` " +
                "WHERE `name` = '" + ingredientType + "'");
        if (rs == null) {
            System.out.println("Error bei getIdFromDb! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error bei getIdFromDb!");
        } finally {
            dbConnector.closeConnection();
        }
        return id;
    }

    public static int getDishIdFromDb(Dish dish, DbConnector dbConnector) {
        // // Get the ID of the current dish -> from DB
        int id = 0;
        ResultSet rs = dbConnector.fetchData("SELECT `id` FROM `dish` WHERE `name` = '" + dish.getType() + "'");
        if (rs == null) {
            System.out.println("Error bei getDishIdFromDb! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error bei getDishIdFromDb!");
        } finally {
            dbConnector.closeConnection();
        }
        return id;
    }

    public static boolean insertDishIngredient(Dish dish, DbConnector dbConnector) {
        // Insert data in table dish_ingredient on DB
        ArrayList<Ingredient> ingredients = dish.getIngredients();
        boolean isInserted = false;

        // Loop through the ingredients from the current dish and insert it in DB
        for (int i = 0; i < ingredients.size(); i++) {
            // Get IDs from DB
            int dishId = getDishIdFromDb(dish, dbConnector);
            int ingredientId = getIngredientIdFromDb(ingredients.get(i).getType(), dbConnector);

            isInserted = dbConnector.insertData("INSERT INTO `dish_ingredient`(`dish`, `ingredient`)" +
                    " VALUES (" + dishId + ", " + ingredientId + ");");
            if (isInserted) {
                System.out.println("Daten wurden aktualisiert");
            } else {
                System.out.println("BROOO FAIL " + dishId + " " + ingredients.get(i).getId());
            }
        }
        return isInserted;
    }
}
