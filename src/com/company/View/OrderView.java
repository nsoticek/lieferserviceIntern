package com.company.View;

import com.company.DbHelper.OrderRepository;
import com.company.models.Order;

import java.util.List;

public class OrderView {

    public void printAllOrders(List<Order> orders) {
        for (int i = 0; i < orders.size(); i++) {
            System.out.println(orders.get(i).getId() + ". Kundennr. " + orders.get(i).getCustomer().getId() +
                    " " + orders.get(i).getDish().getType() + "  " + orders.get(i).getDate());
        }
    }
}
