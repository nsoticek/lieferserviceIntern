package com.company.DbHelper;

import com.company.models.Customer;
import com.company.models.Dish;
import com.company.models.Location;
import com.company.models.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class RestaurantDb {

    public static ArrayList<Order> getAllOrders(DbConnector dbConnector) {
        // Get all orders from DB
        ArrayList<Order> orders = new ArrayList<>();

        ResultSet rs = dbConnector.fetchData("SELECT order_table.id, order_table.customer, " +
                "dish.id, dish.name AS dish_name, dish.price, " +
                "order_table.date " +
                "FROM ((order_details_change " +
                "INNER JOIN order_table ON order_details_change.order_index = order_table.id) " +
                "INNER JOIN dish ON order_details_change.dish = dish.id) " +
                "ORDER BY order_table.id DESC");
        if (rs == null) {
            System.out.println("Error bei getAllOrders! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int orderId = rs.getInt("id");
                int customer = rs.getInt("customer");
                //int dishId = rs.getInt("dish_id");
                String dishName = rs.getString("dish_name");
                double price = rs.getDouble("price");
                Date date = rs.getDate("date");

                orders.add(new Order(orderId, new Customer(customer), new Dish(dishName, price), date));
            }
        } catch (SQLException e) {
            System.out.println("Error bei login!");
        } finally {
            dbConnector.closeConnection();
        }
        return orders;
    }

    public static int getIdFromLocation(Location location, DbConnector dbConnector) {
        // Get ID from current location
        int id = 0;
        ResultSet rs = dbConnector.fetchData("SELECT `id` FROM `location` WHERE `name` = '" + location.getName() + "'");
        if (rs == null) {
            System.out.println("Error bei getIdFromLocation! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error bei login!");
        } finally {
            dbConnector.closeConnection();
        }
        return id;
    }

    public static boolean insertLocation(Location location, DbConnector dbConnector) {
        // Set value in table `location`
        boolean isInserted = dbConnector.insertData("INSERT INTO `location`(`name`) VALUES ('" + location.getName() + "')");
        if (isInserted) {
            System.out.println("Daten wurden aktualisiert");
        }
        return isInserted;
    }

    public static boolean insertDeliveryPrice(int locationId, Location location, DbConnector dbConnector) {
        // Set value in table `location`
        boolean isInserted = dbConnector.insertData("INSERT INTO `delivery_price`(`location`, `price`) " +
                "VALUES (" + locationId + ", " + location.getExtraPrice() + ")");
        if (isInserted) {
            System.out.println("Daten wurden aktualisiert");
        }
        return isInserted;
    }
}
