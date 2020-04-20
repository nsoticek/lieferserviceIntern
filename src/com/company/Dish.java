package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public void addToDb() {
        // Add dish to DB
        String sqlCommand = "INSERT INTO `dish`(`name`, `price`) " +
                "VALUES ('" + this.type + "', " + this.price + ")";
        executeUpdate(sqlCommand);

        addIngredientsToDb();
    }

    private void addIngredientsToDb() {
        // Add ingredients to DB
        for (int i = 0; i < ingredients.size(); i++) {
            boolean isIngredientExisting = false;
            // Check if ingredient exists
            String sqlCommand = "SELECT `id` FROM `ingredient` WHERE `name` = '" + ingredients.get(i).getType() + "'";
            Integer id = getIdFromDb(sqlCommand);
            if (id != 0) {
                isIngredientExisting = true;
            }

            if(!isIngredientExisting) {
                // Add to Db if ingredient is not existing
                String sqlCommand1 = "INSERT INTO `ingredient`(`name`, `extraprice`) " +
                        "VALUES ('" + ingredients.get(i).getType() + "', " + ingredients.get(i).getExtraPrice() + ")";
                executeUpdate(sqlCommand1);
            }
        }
    }

    public void joinIngredient() {
        // Get the ID of the current dish -> from DB; required for the next command
        String sqlCommand = "SELECT `id` FROM `dish` WHERE `name` = '" + this.type + "'";
        int dishId = getIdFromDb(sqlCommand);

        for (int i = 0; i < ingredients.size(); i++) {
            // Loop through the ingredients from the current dish and insert it in DB
            // This command is used to check if ingredient already exists; If yes get the id of it; required for the next command
            String sqlCommandIngredient = "SELECT `id` FROM `ingredient` WHERE `name` = '" + ingredients.get(i).getType() + "'";
            int ingredientId = getIdFromDb(sqlCommandIngredient);

            // Insert data in dish_ingredient table on DB
            String sqlJoinCommand = "INSERT INTO `dish_ingredient`(`dish`, `ingredient`)" +
                    " VALUES (" + dishId + ", " + ingredientId + ");";
            executeUpdate(sqlJoinCommand);
        }
    }

    private void executeUpdate(String sqlCommand) {
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int getIdFromDb(String sqlCommand) {
        int id = 0;
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }
}
