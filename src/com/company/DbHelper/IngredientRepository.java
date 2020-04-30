package com.company.DbHelper;

import com.company.models.Dish;
import com.company.models.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientRepository implements IRepository<Ingredient>{
    private DbConnector connector;

    public IngredientRepository() {
        this.connector = DbConnector.getInstance();
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public Ingredient findOne(int id) {
        return null;
    }

    @Override
    public void create(Ingredient ingredient) {
            boolean isInserted = connector.insertData("INSERT INTO `ingredient`(`name`, `extraprice`) " +
                    "VALUES ('" + ingredient.getType() + "', " + ingredient.getExtraPrice() + ")");
            if (isInserted) {
                System.out.println("Daten wurden aktualisiert");
        }
    }

    public int getIngredientIdFromDb(String ingredientType) {
        // Get the ID of the current ingredient
        int id = 0;
        ResultSet rs = connector.fetchData("SELECT `id` FROM `ingredient` " +
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
            connector.closeConnection();
        }
        return id;
    }
}
