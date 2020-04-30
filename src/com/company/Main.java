package com.company;

import com.company.Controller.*;
import com.company.models.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        while (true) {
            mainMenu();
        }
    }

    private static void mainMenu() {
        String menu = "\n1. Bestellungen einsehen" + "\n2. Neues Menü anlegen" + "\n3. Neuen Lieferort hinzufügen" +
                "\n4. Auswertung der Daten" + "\n5. Bestellungen exportieren" + "\n6. Zutatenliste exportieren";

        System.out.println("----Dashboard----");
        boolean isNewIngredient;
        EvaluationController evaluationController = new EvaluationController();

        // Main menu
        switch (userInput(menu)) {
            case "1": // Show all orders
                OrderController.start();
                break;
            case "2": // Create new dish
                String dishName = userInput("Bezeichnung der Speise: ");
                double price = Double.parseDouble(userInput("Verkaufspreis: (mit .)"));
                Dish dish = new Dish(dishName, price);
                isNewIngredient = true;

                // Create Ingredient
                while (isNewIngredient) {
                    String ingredientName = userInput("Bezeichnung der Zutat: ");
                    double extraPrice = Double.parseDouble(userInput("Extrapreis: (mit .)"));
                    Ingredient ingredient = new Ingredient(ingredientName, extraPrice);
                    dish.addIngredient(ingredient);
                    isNewIngredient = checkIfAnotherIngredient("Willst du eine weitere Zutat hinzufügen: (JA/NEIN)");
                }
                // Insert in DB
                DishController.insertDishAndIngredient(dish);
                break;
            case "3": // Add new city
                addLocation();
                break;
            case "4": // Evaluation
                evaluationMenu(evaluationController);
                break;
            case "5": // CSV export (OrderID;customerID;totalPrice)
                exportCsv(Write.ORDER);
                break;
            case "6": // CSV export (Zutat;Anzahl)
                exportCsv(Write.INGREDIENT);
                break;

            default:
                System.out.println("Error");
        }
    }

    private static void evaluationMenu(EvaluationController evaluationController) {
        String menuEvaluation = "\n1. Wie viele Bestellungen gab es schon?\n" +
                "2. Wie viele Bestellungen gab es je Kunde?\n" +
                "3. Wie viele Bestellungen gab es je Ortschaft?\n" +
                "4. Was sind all meine Umsätze nach den Kriterien 1 - 3 (Gesamt, je Kunde, je Ortschaft)\n" +
                "5. Was wurde am häufigsten bestellt und wie oft?\n" +
                "6. In absteigender Reihenfolge, wie oft bestellt wurde.\n";

        switch (userInput(menuEvaluation)) {
            case "1": // How many orders
                evaluationController.printSumOfAllOrders();
                break;
            case "2": // How many orders per customer
                evaluationController.printSumOfAllOrdersPerCustomer();
                break;
            case "3": // How many orders per town
                evaluationController.printSumOfAllOrdersPerTown();
                break;
            case "4": // All sales according to criteria 1 - 3 (total, per customer, per location)
                evaluationController.printTotalRevenue();
                evaluationController.printSalesPerCustomer();
                evaluationController.printSalesPerLocation();
                break;
            case "5": // Ordered most often
                evaluationController.printMostOftenOrderedDish();
                break;
            case "6": // DESC order orderes
                evaluationController.printAllOrdersDesc();
                break;
            default:
                System.out.println("Error");
        }
    }

    private static void exportCsv(Write writeTyp) {
        String path = userInput("Wo willst du die Datei speichern? Pfad angeben: (Ohne datei!)");
        String fileName = userInput("Dateiname: (ohne .csv)");
        CsvController.writeCsv(path, fileName, writeTyp);
    }

    private static void addLocation() {
        String city = userInput("Gib einen Ort an: ");
        double deliveryPrice = Double.parseDouble(userInput("Gib den Preis der Lieferung an: "));
        Location location = new Location(city, deliveryPrice);
        RestaurantController.addDeliveryLocation(location);
    }

    private static boolean checkIfAnotherIngredient(String anotherIngredient) {
        // Check if user wants to set another ingredient (JA/NEIN)
        boolean isNewIngredient = false;
        String userInput = userInput(anotherIngredient);

        if (userInput.equalsIgnoreCase("JA")) {
            isNewIngredient = true;
        }
        return isNewIngredient;
    }

    private static String userInput(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine();
    }
}
