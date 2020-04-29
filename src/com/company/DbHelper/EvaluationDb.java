package com.company.DbHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;

public class EvaluationDb {

    public static int getSumOfAllOrders(DbConnector dbConnector) {
        // How many orders in order_table (DB)
        int ordersCount = 0;
        ResultSet rs = dbConnector.fetchData("SELECT COUNT(*) FROM `order_table`");
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
            dbConnector.closeConnection();
        }
        return ordersCount;
    }

    public static HashMap<Integer, Integer> getSumOfAllOrdersPerCustomer(DbConnector dbConnector) {
        HashMap<Integer, Integer> sumOfAllOrdersPerCustomer = new HashMap<Integer, Integer>();

        ResultSet rs = dbConnector.fetchData("SELECT customer, COUNT(*) AS number_of_orders FROM order_table, customer " +
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
            dbConnector.closeConnection();
        }
        return sumOfAllOrdersPerCustomer;
    }

    public static HashMap<String, Integer> getSumOfAllOrdersPerTown(DbConnector dbConnector) {
        HashMap<String, Integer> sumOfAllOrdersPerTown = new HashMap<String, Integer>();

        ResultSet rs = dbConnector.fetchData("SELECT customer.city, COUNT(*) AS number_of_orders FROM order_table, customer " +
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
            dbConnector.closeConnection();
        }
        return sumOfAllOrdersPerTown;
    }

    public static HashMap<String, Integer> getMostOftenOrderedDish(DbConnector dbConnector) {
        HashMap<String, Integer> mostOftenOrderdDish = new HashMap<String, Integer>();

        ResultSet rs = dbConnector.fetchData("SELECT dish.name, COUNT(*) AS number_of_orders FROM order_details, dish \n" +
                "WHERE dish = dish.id \n" +
                "GROUP BY dish \n" +
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
            dbConnector.closeConnection();
        }
        return mostOftenOrderdDish;
    }

    public static HashMap<String, Integer> getAllOrdersDesc(DbConnector dbConnector) {
        HashMap<String, Integer> allOrdersDec = new HashMap<String, Integer>();

        ResultSet rs = dbConnector.fetchData("SELECT dish.name, COUNT(*) AS number_of_orders FROM order_details, dish \n" +
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
            dbConnector.closeConnection();
        }
        return allOrdersDec;
    }

    public static double getTotalRevenue(DbConnector dbConnector) {
        double totalRevenue = 0;
        ResultSet rs = dbConnector.fetchData("SELECT SUM(total_price+delivery_price.price) AS total_revenue " +
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
            dbConnector.closeConnection();
        }
        return totalRevenue;
    }

    public static String getSalesPerCustomer(DbConnector dbConnector) {
        String output = "";
        ResultSet rs = dbConnector.fetchData("SELECT order_table.id, customer, total_price+delivery_price.price AS cost " +
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
            dbConnector.closeConnection();
        }
        return output;
    }

    public static String getSalesPerLocation(DbConnector dbConnector) {
        String output = "";
        ResultSet rs = dbConnector.fetchData("SELECT order_table.id, delivery_price.location, " +
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
            dbConnector.closeConnection();
        }
        return output;
    }
}
