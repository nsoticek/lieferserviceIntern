package com.company.View;

import java.util.HashMap;

public class EvaluationView {

    public void printSumOfAllOrders(int sumOfAllOrders) {
        System.out.println("Summe aller Bestellungen: " + sumOfAllOrders);
    }

    public void printSumOfAllOrdersPerCustomer(HashMap<Integer, Integer> sumOfAllOrdersPerCustomer) {
        // iterate trough hashmap and print it
        for (Integer i : sumOfAllOrdersPerCustomer.keySet()) {
            System.out.println("Kundennr: " + i + " Bestellungen: " + sumOfAllOrdersPerCustomer.get(i));
        }
    }

    public void printSumOfAllOrdersPerTown(HashMap<String, Integer> sumOfAllOrdersPerTown) {
        // iterate trough hashmap and print it
        for (String i : sumOfAllOrdersPerTown.keySet()) {
            System.out.println("Ort: " + i + " Bestellungen: " + sumOfAllOrdersPerTown.get(i));
        }
    }

    public void printTotalRevenue(double totalRevenue) {
        System.out.println("Gesamtumsatz: " + totalRevenue);
    }

    public void printSalesPerCustomer(String output) {
        System.out.println("Umsatz je Kunde: ");
        System.out.println(output);
    }

    public void printSalesPerLocation(String output) {
        System.out.println("Ort mit höchstem Umsatz: ");
        System.out.println(output);
    }

    public void printMostOftenOrderedDish(HashMap<String, Integer> dish) {
        // iterate trough hashmap and print it
        for (String i : dish.keySet()) {
            System.out.println("Name: " + i + " Bestellungen: " + dish.get(i));
        }
    }

    public void printAllOrdersDesc(HashMap<String, Integer> orders) {
        // iterate trough hashmap and print it
        for (String i : orders.keySet()) {
            System.out.println("Name: " + i + " Bestellungen: " + orders.get(i));
        }
    }
}
