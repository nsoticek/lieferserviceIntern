package com.company.Controller;

import com.company.DbHelper.DbConnector;
import com.company.DbHelper.RestaurantDb;
import com.company.models.Location;
import com.company.models.Order;

import java.util.ArrayList;

public class RestaurantController {

    public static void addDeliveryLocation(Location location, DbConnector dbConnector) {
        RestaurantDb.insertLocation(location, dbConnector);
        int locationId = RestaurantDb.getIdFromLocation(location, dbConnector);
        RestaurantDb.insertDeliveryPrice(locationId, location, dbConnector);
    }

    public static void printAllOrders(DbConnector dbConnector) {
        ArrayList<Order> orders = RestaurantDb.getAllOrders(dbConnector);

        for (int i = 0; i < orders.size(); i++) {
            System.out.println(orders.get(i).getId() + ". Kundennr. " + orders.get(i).getCustomer().getId() +
                    " " + orders.get(i).getDish().getType() + "  " + orders.get(i).getDate());
        }
    }
}
