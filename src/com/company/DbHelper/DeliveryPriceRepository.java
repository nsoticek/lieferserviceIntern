package com.company.DbHelper;

import com.company.models.DeliveryPrice;

import java.util.List;

public class DeliveryPriceRepository implements IRepository<DeliveryPrice> {
    private DbConnector connector;

    public DeliveryPriceRepository() {
        this.connector = DbConnector.getInstance();
    }

    @Override
    public List<DeliveryPrice> findAll() {
        return null;
    }

    @Override
    public DeliveryPrice findOne(int id) {
        return null;
    }

    @Override
    public void create(DeliveryPrice deliveryPrice) {
        boolean isInserted = connector.insertData("INSERT INTO `delivery_price`(`location`, `price`) " +
                "VALUES (" + deliveryPrice.getLocationId() + ", " + deliveryPrice.getPrice() + ")");
        if (isInserted) {
            System.out.println("Daten wurden aktualisiert");
        }
    }
}
