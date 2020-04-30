package com.company.DbHelper;

import com.company.models.Customer;
import com.company.models.Dish;
import com.company.models.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderRepository implements IRepository{
    private DbConnector connector;

    public OrderRepository() {
        this.connector = DbConnector.getInstance();
    }

    @Override
    public List<Order> findAll() {
        // Get all orders from DB
        ArrayList<Order> orders = new ArrayList<>();

        ResultSet rs = connector.fetchData("SELECT order_table.id, order_table.customer, " +
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
            connector.closeConnection();
        }
        return orders;
    }

    @Override
    public Object findOne(int id) {
        return null;
    }

    @Override
    public void create(Object entity) {

    }
}
