package com.company.DbHelper;

import com.company.models.Dish;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DishRepository implements IRepository<Dish> {
    private DbConnector connector;

    public DishRepository() {
        this.connector = DbConnector.getInstance();
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public Dish findOne(int id) {
        return null;
    }

    @Override
    public void create(Dish dish) {
        boolean isInserted = connector.insertData("INSERT INTO `dish`(`name`, `price`) " +
                "VALUES ('" + dish.getType() + "', " + dish.getPrice() + ")");
        if (isInserted) {
            System.out.println("Daten wurden aktualisiert");
        }
    }

    public int getDishIdFromDb(Dish dish) {
        // // Get the ID of the current dish -> from DB
        int id = 0;
        ResultSet rs = connector.fetchData("SELECT `id` FROM `dish` WHERE `name` = '" + dish.getType() + "'");
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
            connector.closeConnection();
        }
        return id;
    }
}
