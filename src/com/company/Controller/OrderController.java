package com.company.Controller;

import com.company.DbHelper.OrderRepository;
import com.company.View.OrderView;

public class OrderController {

    public static void start() {
        OrderView orderView = new OrderView();
        OrderRepository orderRepository = new OrderRepository();

        orderView.printAllOrders(orderRepository.findAll());
    }
}
