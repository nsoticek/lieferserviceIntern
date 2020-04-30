package com.company.Controller;

import com.company.DbHelper.DeliveryPriceRepository;
import com.company.DbHelper.LocationRepository;
import com.company.models.DeliveryPrice;
import com.company.models.Location;

public class RestaurantController {

    public static void addDeliveryLocation(Location location) {
        LocationRepository locationRepository = new LocationRepository();
        DeliveryPriceRepository deliveryPriceRepository = new DeliveryPriceRepository();

        locationRepository.create(location);
        // Get ID from current location
        int locationId = locationRepository.getIdFromLocation(location);

        DeliveryPrice deliveryPrice = new DeliveryPrice(locationId, location.getExtraPrice());
        // Insert deliveryPrice in table 'delivery_price' on DB
        deliveryPriceRepository.create(deliveryPrice);
    }
}
