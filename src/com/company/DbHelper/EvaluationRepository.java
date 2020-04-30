package com.company.DbHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;

public class EvaluationRepository {
    private DbConnector connector;

    public EvaluationRepository() {
        this.connector = DbConnector.getInstance();
    }

    public int getSumOfAllOrders() {
        // How many orders in order_table (DB)
        int ordersCount = 0;
        ResultSet rs = connector.fetchData("SELECT COUNT(*) FROM `order_table`");
        if (rs == null) {
            System.out.println("Error bei getIdFromLocation! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                ordersCount = rs.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            System.out.println("Error bei login!");
        } finally {
            connector.closeConnection();
        }
        return ordersCount;
    }

    public HashMap<Integer, Integer> getSumOfAllOrdersPerCustomer() {
        HashMap<Integer, Integer> sumOfAllOrdersPerCustomer = new HashMap<Integer, Integer>();

        ResultSet rs = connector.fetchData("SELECT customer, COUNT(*) AS number_of_orders FROM order_table, customer " +
                "WHERE customer = customer.id " +
                "GROUP BY customer " +
                "ORDER BY number_of_orders DESC");
        if (rs == null) {
            System.out.println("Error bei getSumOfAllOrdersPerCustomer! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int customer = rs.getInt("customer");
                int numberOfOrders = rs.getInt("number_of_orders");

                sumOfAllOrdersPerCustomer.put(customer, numberOfOrders);
            }
        } catch (SQLException e) {
            System.out.println("Error bei login!");
        } finally {
            connector.closeConnection();
        }
        return sumOfAllOrdersPerCustomer;
    }

    public HashMap<String, Integer> getSumOfAllOrdersPerTown() {
        HashMap<String, Integer> sumOfAllOrdersPerTown = new HashMap<String, Integer>();

        ResultSet rs = connector.fetchData("SELECT customer.city, COUNT(*) AS number_of_orders FROM order_table, customer " +
        "WHERE customer = customer.id GROUP BY customer.city ORDER BY number_of_orders DESC");
        if (rs == null) {
            System.out.println("Error bei getSumOfAllOrdersPerTown! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                String city = rs.getString("city");
                int numberOfOrders = rs.getInt("number_of_orders");

                sumOfAllOrdersPerTown.put(city, numberOfOrders);
            }
        } catch (SQLException e) {
            System.out.println("Error bei login!");
        } finally {
            connector.closeConnection();
        }
        return sumOfAllOrdersPerTown;
    }

    public HashMap<String, Integer> getMostOftenOrderedDish() {
        HashMap<String, Integer> mostOftenOrderdDish = new HashMap<String, Integer>();

        ResultSet rs = connector.fetchData("SELECT dish.name, COUNT(*) AS number_of_orders " +
                "FROM order_details_change, dish " +
                "WHERE dish = dish.id GROUP BY dish " +
                "ORDER BY number_of_orders DESC LIMIT 1");
        if (rs == null) {
            System.out.println("Error bei getMostOftenOrderdDish! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                int numberOfOrders = rs.getInt("number_of_orders");

                mostOftenOrderdDish.put(name, numberOfOrders);
            }
        } catch (SQLException e) {
            System.out.println("Error bei login!");
        } finally {
            connector.closeConnection();
        }
        return mostOftenOrderdDish;
    }

    public HashMap<String, Integer> getAllOrdersDesc() {
        HashMap<String, Integer> allOrdersDec = new HashMap<String, Integer>();

        ResultSet rs = connector.fetchData("SELECT dish.name, COUNT(*) AS number_of_orders FROM order_details_change, dish \n" +
                "WHERE dish = dish.id \n" +
                "GROUP BY dish \n" +
                "ORDER BY number_of_orders DESC");
        if (rs == null) {
            System.out.println("Error bei getAllOrdersDesc! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                int numberOfOrders = rs.getInt("number_of_orders");

                allOrdersDec.put(name, numberOfOrders);
            }
        } catch (SQLException e) {
            System.out.println("Error bei login!");
        } finally {
            connector.closeConnection();
        }
        return allOrdersDec;
    }

    public double getTotalRevenue() {
        double totalRevenue = 0;
        ResultSet rs = connector.fetchData("SELECT SUM(total_price+delivery_price.price) AS total_revenue " +
                "FROM `order_table` INNER JOIN delivery_price ON order_table.delivery_cost = delivery_price.id");
        if (rs == null) {
            System.out.println("Error bei getTotalRevenue! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                totalRevenue = rs.getDouble("total_revenue");
            }
        } catch (SQLException e) {
            System.out.println("Error bei login!");
        } finally {
            connector.closeConnection();
        }
        return totalRevenue;
    }

    public String getSalesPerCustomer() {
        String output = "";
        ResultSet rs = connector.fetchData("SELECT order_table.id, customer, total_price+delivery_price.price AS cost " +
                "FROM order_table INNER JOIN delivery_price ON order_table.delivery_cost = delivery_price.id");
        if (rs == null) {
            System.out.println("Error bei getTotalRevenue! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                int customerId = rs.getInt("customer");
                double cost = rs.getDouble("cost");

                output += "Bestellnr: " + id + ". Kudennr: " + customerId + " Preis: " + cost + "\n";
            }
        } catch (SQLException e) {
            System.out.println("Error bei login!");
        } finally {
            connector.closeConnection();
        }
        return output;
    }

    public String getSalesPerLocation() {
        String output = "";
        ResultSet rs = connector.fetchData("SELECT order_table.id, delivery_price.location, " +
                "SUM(total_price+delivery_price.price) AS cost, COUNT(*) AS order_number FROM order_table " +
                "INNER JOIN delivery_price ON order_table.delivery_cost = delivery_price.id " +
                "GROUP BY delivery_price.location");
        if (rs == null) {
            System.out.println("Error bei getTotalRevenue! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                int locationId = rs.getInt("location");
                double cost = rs.getInt("cost");
                int orderNumber = rs.getInt("order_number");

                output = "Ort: " + locationId + " Umsatz: " + cost + " Bestellungen: " + orderNumber + "\n";
            }
        } catch (SQLException e) {
            System.out.println("Error bei login!");
        } finally {
            connector.closeConnection();
        }
        return output;
    }
}
