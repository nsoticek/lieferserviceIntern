package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;

public class Restaurant {

    private String name;
    private String location;

    public Restaurant(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public void addDeliveryLocation(Location location) {
        // Set value in table `location`
        String sqlCommand = "INSERT INTO `location`(`name`) VALUES ('" + location.getName() + "')";
        executeUpdate(sqlCommand);

        // Get ID from current location
        String sqlCommandIngredient = "SELECT `id` FROM `location` WHERE `name` = '" + location.getName() + "'";
        String columnTitle = "id";
        int locationId = (int) getNumFromDB(sqlCommandIngredient, columnTitle);

        // Set value in table `delivery_price`
        String sqlInsertDeliveryPrice = "INSERT INTO `delivery_price`(`location`, `price`) " +
                "VALUES (" + locationId + ", " + location.getExtraPrice() + ")";
        executeUpdate(sqlInsertDeliveryPrice);
    }

    public void printHowManyOrders() {
        // Get and print how many orders in order_table (DB)
        String sqlCommand = "SELECT COUNT(*) FROM `order_table`";
        String columnTitle = "COUNT(*)";
        int count = (int) getNumFromDB(sqlCommand, columnTitle);
        System.out.println("Es wurden bereits " + count + " Bestellungen aufgegeben.");
    }

    public void printHowManyOrdersPerCustomer() {
        // Get and print how many orders per customer in table (DB)
        HashMap<String, Integer> customerOrders;
        String sqlCommand = "SELECT customer, COUNT(*) AS number_of_orders FROM order_table, customer " +
                "WHERE customer = customer.id " +
                "GROUP BY customer " +
                "ORDER BY number_of_orders DESC";
        String columnTitle = "customer";
        String columnTitle2 = "number_of_orders";
        customerOrders = getNumbersFromDB(sqlCommand, columnTitle, columnTitle2);

        // iterate trough the hashmap "customerOrders" and print it
        for (String i : customerOrders.keySet()) {
            System.out.println("Kundennr.: " + i + " Orders: " + customerOrders.get(i));
        }
    }

    public void printHowManyOrdersPerTown() {
        // Get and print how many orders per city in table (DB)
        HashMap<String, Integer> cityOrders;
        String sqlCommand = "SELECT customer.city, COUNT(*) AS number_of_orders FROM order_table, customer " +
                "WHERE customer = customer.id GROUP BY customer.city ORDER BY number_of_orders DESC";
        String columnTitle = "city";
        String columnTitle2 = "number_of_orders";
        cityOrders = getNumbersFromDB(sqlCommand, columnTitle, columnTitle2);

        // iterate trough the hashmap "customerOrders" and print it
        for (String i : cityOrders.keySet()) {
            System.out.println("Ort: " + i + " Orders: " + cityOrders.get(i));
        }
    }

    public void printOrderedMostOften() {
        // Get and print what was ordered most often
        HashMap<String, Integer> dishnameOrders;
        String sqlCommand = "SELECT dish.name, COUNT(*) AS number_of_orders FROM order_details, dish \n" +
                "WHERE dish = dish.id \n" +
                "GROUP BY dish \n" +
                "ORDER BY number_of_orders DESC LIMIT 1;";
        String columnTitle = "name";
        String columnTitle2 = "number_of_orders";
        dishnameOrders = getNumbersFromDB(sqlCommand, columnTitle, columnTitle2);

        // iterate trough the hashmap "dishnameSales" and print it
        for (String i : dishnameOrders.keySet()) {
            System.out.println("Speise: " + i + " | Wie oft verkauft: " + dishnameOrders.get(i) + ". mal");
        }
    }

    public void printOrdersDesc() {
        // Get and print what was ordered most often DESC
        HashMap<String, Integer> dishnameOrders;
        String sqlCommand = "SELECT dish.name, COUNT(*) AS number_of_orders FROM order_details, dish \n" +
                "WHERE dish = dish.id \n" +
                "GROUP BY dish \n" +
                "ORDER BY number_of_orders DESC";
        String columnTitle = "name";
        String columnTitle2 = "number_of_orders";
        dishnameOrders = getNumbersFromDB(sqlCommand, columnTitle, columnTitle2);

        // iterate trough the hashmap "dishnameSales" and print it
        for (String i : dishnameOrders.keySet()) {
            System.out.println("Speise: " + i + " | Wie oft verkauft: " + dishnameOrders.get(i) + ". mal");
        }
    }

    public void printAllOrders() {
        // Get and print all orders
        String sqlCommand = "SELECT order_table.id, order_table.customer, dish.name AS dish_name, " +
                "ingredient.name AS ingredient_name, order_table.date " +
                "FROM (((order_details_change " +
                "INNER JOIN order_table ON order_details_change.order_index = order_table.id) " +
                "INNER JOIN dish ON order_details_change.dish = dish.id) " +
                "INNER JOIN ingredient ON order_details_change.ingredient = ingredient.id) " +
                "ORDER BY order_table.id DESC";
        getAndPrintOrders(sqlCommand);
    }

    public void printSales() {
        // Get and print total revenue
        String sqlCommandRevenue = "SELECT SUM(total_price+delivery_price.price) AS total_revenue FROM `order_table` " +
                "INNER JOIN delivery_price ON order_table.delivery_cost = delivery_price.id";
        String column = "total_revenue";
        double totalRevenue = getNumFromDB(sqlCommandRevenue, column);
        System.out.println("Gesamtumsatz " + totalRevenue);

        // Get and print sales per customer
        String sqlCommandPerCustomer = "SELECT order_table.id, customer, total_price+delivery_price.price AS cost " +
                "FROM order_table INNER JOIN delivery_price ON order_table.delivery_cost = delivery_price.id";
        getAndPrintSalesPerCustomer(sqlCommandPerCustomer, false);

        // Get and print sales per location
        String sqlCommandPerLocation = "SELECT order_table.id, delivery_price.location, " +
                "SUM(total_price+delivery_price.price) AS cost, COUNT(*) AS order_number FROM order_table " +
                "INNER JOIN delivery_price ON order_table.delivery_cost = delivery_price.id " +
                "GROUP BY delivery_price.location";
        getAndPrintSalesPerCity(sqlCommandPerLocation);
    }

    public String getOrderText() {
        // Get string (OrderID;customerID;totalPrice) for csv writer
        String sqlCommandPerCustomer = "SELECT order_table.id, customer, total_price+delivery_price.price AS cost " +
                "FROM order_table INNER JOIN delivery_price ON order_table.delivery_cost = delivery_price.id";
        return getAndPrintSalesPerCustomer(sqlCommandPerCustomer, true);
    }

    public String usedIngredients() {
        // Get string (Zutat;Anzahl) for csv writer
        String sqlCommand = "SELECT ingredient.name, COUNT(*) AS quantity " +
                "FROM order_details_change " +
                "INNER JOIN ingredient ON order_details_change.ingredient = ingredient.id " +
                "GROUP BY ingredient.name";
        return getIngredientsList(sqlCommand);
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

    private double getNumFromDB(String sqlCommand, String columnTitle) {
        // Get a number from DB and save it to a var(int)
        double num = 0;
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                num = rs.getDouble(columnTitle);
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
        return num;
    }

    private HashMap<String, Integer> getNumbersFromDB(String sqlCommand, String columnTitle, String columnTitle2) {
        // Get two columns from DB and save it to a hashmap
        HashMap<String, Integer> numbers = new HashMap<String, Integer>();
        String str = "";
        int num = 0;
        int num2 = 0;
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                // Check if first value == string or int
                if (columnTitle.equalsIgnoreCase("customer")) {
                    num = rs.getInt(columnTitle);
                    str = String.valueOf(num);
                }
                if (columnTitle.equalsIgnoreCase("city") || columnTitle.equalsIgnoreCase("name"))
                    str = rs.getString(columnTitle);
                num2 = rs.getInt(columnTitle2);

                numbers.put(str, num2);
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
        return numbers;
    }

    private void getAndPrintOrders(String sqlCommand) {
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                int id = rs.getInt("id");
                int customer = rs.getInt("customer");
                String dishName = rs.getString("dish_name");
                String ingredientName = rs.getString("ingredient_name");
                Date date = rs.getDate("date");

                System.out.println(id + " " + "Kundennr. " + customer + " " + dishName + " " + ingredientName + " " + date);
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
    }

    private String getAndPrintSalesPerCustomer(String sqlCommand, boolean isWriting) {
        String csvText = "";
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                int id = rs.getInt("id");
                int customer = rs.getInt("customer");
                double totalPrice = rs.getDouble("cost");
                if(!isWriting)
                    System.out.println("Bestellnr. " + id + " Kundennr: " + customer + " Price: " + totalPrice);
                if(isWriting)
                    csvText += id + ";" + customer + ";" + totalPrice + "\n";
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
        return csvText;
    }

    private String getIngredientsList(String sqlCommand) {
        String csvText = "";
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");

                csvText += name + ";" + quantity + "\n";
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
        return csvText;
    }

    private void getAndPrintSalesPerCity(String sqlCommand) {
        Statement stmt = null;
        Connection conn = DbHelper.getConnectionToDb();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlCommand);
            while (rs.next()) {
                int id = rs.getInt("id");
                int location = rs.getInt("location");
                double totalPrice = rs.getDouble("cost");
                int orderNumber = rs.getInt("order_number");

                System.out.println("Bestellnr. " + id + " Ortsnr.: " + location + " Umsatz: " + totalPrice);
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
    }
}
