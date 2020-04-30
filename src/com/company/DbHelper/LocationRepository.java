package com.company.DbHelper;

import com.company.models.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LocationRepository implements IRepository<Location> {
    private DbConnector connector;

    public LocationRepository() {
        this.connector = DbConnector.getInstance();
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public Location findOne(int id) {
        return null;
    }

    @Override
    public void create(Location location) {
        boolean isInserted = connector.insertData("INSERT INTO `location`(`name`) VALUES ('" + location.getName() + "')");
        if (isInserted) {
            System.out.println("Daten wurden aktualisiert");
        }
    }

    public int getIdFromLocation(Location location) {
        // Get ID from current location
        int id = 0;
        ResultSet rs = connector.fetchData("SELECT `id` FROM `location` WHERE `name` = '" + location.getName() + "'");
        if (rs == null) {
            System.out.println("Error bei getIdFromLocation! Konnte keine Daten abrufen.");
        }
        try {
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error bei login!");
        } finally {
            connector.closeConnection();
        }
        return id;
    }
}
