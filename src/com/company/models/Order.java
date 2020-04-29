package com.company.models;

import java.util.Date;

public class Order {

    private int id;
    private Customer customer;
    private Dish dish;
    private Date date;

    public Order(int id, Customer customer, Dish dish, Date date) {
        this.id = id;
        this.customer = customer;
        this.dish = dish;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Dish getDish() {
        return dish;
    }

    public Date getDate() {
        return date;
    }
}
