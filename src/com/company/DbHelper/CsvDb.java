package com.company.DbHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CsvDb {

    public static String getOrdersCsvString(DbConnector dbConnector) {
        // get String (id;customer;totalPrice) for csv writer
        String csvText = "";
        ResultSet rs = dbConnector.fetchData("SELECT order_table.id, customer, total_price+delivery_price.price AS cost " +
                "FROM order_table INNER JOIN delivery_price ON order_table.delivery_cost = delivery_price.id");
        try {
            while (rs.next()) {
               int id = rs.getInt("id");
               int customer = rs.getInt("customer");
               double totalPrice = rs.getDouble("cost");

                csvText += id + ";" + customer + ";" + totalPrice + "\n";
            }
        } catch (SQLException e) {
            System.out.println("Error bei login!");
        } finally {
            dbConnector.closeConnection();
        }
        return csvText;
    }

    public static String getIngredientsCsvString(DbConnector dbConnector) {
        // Get string (Zutat;Anzahl) for csv writer
        String csvText = "";
        ResultSet rs = dbConnector.fetchData("SELECT ingredient.name, COUNT(*) AS quantity " +
                "INNER JOIN ingredient ON order_details_change.ingredient = ingredient.id " +
                "GROUP BY ingredient.name");
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");

                csvText += name + ";" + quantity + "\n";
            }
        } catch (SQLException e) {
            System.out.println("Error bei login!");
        } finally {
            dbConnector.closeConnection();
        }
        return csvText;
    }
}
