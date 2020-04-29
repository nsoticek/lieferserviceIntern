package com.company.Controller;

import com.company.DbHelper.DbConnector;
import com.company.DbHelper.EvaluationDb;

import java.util.HashMap;

public class EvaluationController {

    public static void printSumOfAllOrders(DbConnector dbConnector) {
        System.out.println(EvaluationDb.getSumOfAllOrders(dbConnector));
    }

    public static void printSumOfAllOrdersPerCustomer(DbConnector dbConnector) {
        HashMap<Integer, Integer> sumOfAllOrdersPerCustomer = EvaluationDb.getSumOfAllOrdersPerCustomer(dbConnector);

        // iterate trough hashmap and print it
        for (Integer i : sumOfAllOrdersPerCustomer.keySet()) {
            System.out.println("Kundennr: " + i + " Bestellungen: " + sumOfAllOrdersPerCustomer.get(i));
        }
    }

    public static void printSumOfAllOrdersPerTown(DbConnector dbConnector) {
        HashMap<String, Integer> sumOfAllOrdersPerTown = EvaluationDb.getSumOfAllOrdersPerTown(dbConnector);

        // iterate trough hashmap and print it
        for (String i : sumOfAllOrdersPerTown.keySet()) {
            System.out.println("Ort: " + i + " Bestellungen: " + sumOfAllOrdersPerTown.get(i));
        }
    }

    public static void printTotalRevenue(DbConnector dbConnector) {
        System.out.println("Gesamtumsatz: " + EvaluationDb.getTotalRevenue(dbConnector));
    }

    public static void printSalesPerCustomer(DbConnector dbConnector) {
        System.out.println("Umsatz je Kunde: ");
        System.out.println(EvaluationDb.getSalesPerCustomer(dbConnector));
    }

    public static void printSalesPerLocation(DbConnector dbConnector) {
        System.out.println("Umsatz je Ortschaft: ");
        System.out.println(EvaluationDb.getSalesPerLocation(dbConnector));
    }

    public static void printMostOftenOrderedDish(DbConnector dbConnector) {
        HashMap<String, Integer> dish = EvaluationDb.getMostOftenOrderedDish(dbConnector);

        // iterate trough hashmap and print it
        for (String i : dish.keySet()) {
            System.out.println("Name: " + i + " Bestellungen: " + dish.get(i));
        }
    }

    public static void printAllOrdersDesc(DbConnector dbConnector) {
        HashMap<String, Integer> orders = EvaluationDb.getAllOrdersDesc(dbConnector);

        // iterate trough hashmap and print it
        for (String i : orders.keySet()) {
            System.out.println("Name: " + i + " Bestellungen: " + orders.get(i));
        }
    }
}
