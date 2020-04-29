package com.company;

import com.company.Controller.CsvController;
import com.company.Controller.DishController;
import com.company.Controller.EvaluationController;
import com.company.Controller.RestaurantController;
import com.company.DbHelper.DbConnector;
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
        DbConnector dbConnector = new DbConnector();

        // Main menu
        switch (userInput(menu)) {
            case "1": // Show orders
                RestaurantController.printAllOrders(dbConnector);
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
                DishController.insertDishAndIngredient(dish, dbConnector);
                break;
            case "3": // Add new city
                String city = userInput("Gib einen Ort an: ");
                double deliveryPrice = Double.parseDouble(userInput("Gib den Preis der Lieferung an: "));
                Location location = new Location(city, deliveryPrice);
                RestaurantController.addDeliveryLocation(location, dbConnector);
                break;
            case "4": // Evaluation
                evaluationMenu(dbConnector);
                break;
            case "5": // CSV export (OrderID;customerID;totalPrice)
                String path = userInput("Wo willst du die Datei speichern? Pfad angeben: (Ohne datei!)");
                String fileName = userInput("Dateiname: (ohne .csv)");
                CsvController.writeCsv(path, fileName, Write.ORDER, dbConnector);
                break;
            case "6": // CSV export (Zutat;Anzahl)
                path = userInput("Wo willst du die Datei speichern? Pfad angeben: (Ohne datei!)");
                fileName = userInput("Dateiname: (ohne .csv)");
                CsvController.writeCsv(path, fileName, Write.INGREDIENT, dbConnector);
                break;

            default:
                System.out.println("Error");
        }
    }

    private static void evaluationMenu(DbConnector dbConnector) {
        String menuEvaluation = "\n1. Wie viele Bestellungen gab es schon?\n" +
                "2. Wie viele Bestellungen gab es je Kunde?\n" +
                "3. Wie viele Bestellungen gab es je Ortschaft?\n" +
                "4. Was sind all meine Umsätze nach den Kriterien 1 - 3 (Gesamt, je Kunde, je Ortschaft)\n" +
                "5. Was wurde am häufigsten bestellt und wie oft?\n" +
                "6. In absteigender Reihenfolge, wie oft bestellt wurde.\n";

        switch (userInput(menuEvaluation)) {
            case "1": // How many orders
                EvaluationController.printSumOfAllOrders(dbConnector);
                break;
            case "2": // How many orders per customer
                EvaluationController.printSumOfAllOrdersPerCustomer(dbConnector);
                break;
            case "3": // How many orders per town
                EvaluationController.printSumOfAllOrdersPerTown(dbConnector);
                break;
            case "4": // All sales according to criteria 1 - 3 (total, per customer, per location)
                EvaluationController.printTotalRevenue(dbConnector);
                EvaluationController.printSalesPerCustomer(dbConnector);
                EvaluationController.printSalesPerLocation(dbConnector);
                break;
            case "5": // Ordered most often
                EvaluationController.printMostOftenOrderedDish(dbConnector);
                break;
            case "6": // DESC order orderes
                EvaluationController.printAllOrdersDesc(dbConnector);
                break;
            default:
                System.out.println("Error");
        }
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
        String userInput = scanner.nextLine();
        return userInput;
    }
}
