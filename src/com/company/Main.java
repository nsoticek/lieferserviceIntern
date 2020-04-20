package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    /**TODO Bestellübersicht (Menüpunkt 1.) -> die Zutaten in einer Zeile ausgeben
     * */

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant("Pizzeria Antonio)", "Bludenz");

        while (true) {
            mainMenu(restaurant);
        }
    }

    private static void mainMenu(Restaurant restaurant) {
        String menu =  "\n1. Bestellungen einsehen" + "\n2. Neues Menü anlegen" + "\n3. Neuen Lieferort hinzufügen" +
        "\n4. Auswertung der Daten" + "\n5. Bestellungen exportieren" + "\n6. Zutatenliste exportieren";
        String menuEvaluation = "\n1. Wie viele Bestellungen gab es schon?\n" +
                "2. Wie viele Bestellungen gab es je Kunde?\n" +
                "3. Wie viele Bestellungen gab es je Ortschaft?\n" +
                "4. Was sind all meine Umsätze nach den Kriterien 1 - 3 (Gesamt, je Kunde, je Ortschaft)\n" +
                "5. Was wurde am häufigsten bestellt und wie oft?\n" +
                "6. In absteigender Reihenfolge, wie oft bestellt wurde.\n";
        String messageNewDish = "Bezeichnung der Speise: ";
        String messageNewDishPrice = "Verakufspreis: (mit .)";
        String messageNewIngredient = "Bezeichnung der Zutat: ";
        String messageNewIngredientPrice = "Extrapreis: (mit .)";
        String anotherIngredient = "Willst du eine weitere Zutat hinzufügen: (JA/NEIN)";
        String messageLocation = "Gib einen Ort an: ";
        String messagePriceLoaction = "Gib den Preis der Lieferung an: ";

        System.out.println("----Dashboard----");
        boolean isNewIngredient;

        // Main menu
        switch (userInput(menu)) {
            case "1": // Show orders
                restaurant.printAllOrders();
                break;
            case "2": // Create new dish
                // Create Dish
                String newDishName = userInput(messageNewDish);
                double price = Double.parseDouble(userInput(messageNewDishPrice));
                Dish dish = createNewDish(newDishName, price);
                isNewIngredient = true;
                // Create Ingredient
                while (isNewIngredient) {
                    String newIngredientName = userInput(messageNewIngredient);
                    double extraPrice = Double.parseDouble(userInput(messageNewIngredientPrice));
                    Ingredient ingredient = createNewIngredient(newIngredientName, extraPrice);
                    dish.addIngredient(ingredient);
                    isNewIngredient = checkIfAnotherIngredient(anotherIngredient);
                }
                dish.addToDb();
                dish.joinIngredient();
                break;
            case "3": // Add new city
                String city = userInput(messageLocation);
                double deliveryPrice = Double.parseDouble(userInput(messagePriceLoaction));
                Location location = new Location(city, deliveryPrice);
                restaurant.addDeliveryLocation(location);
                break;
            case "4": // Evaluation
                switch (userInput(menuEvaluation)) {
                    case "1": // How many orders
                        restaurant.printHowManyOrders();
                        break;
                    case "2": // How many orders per customer
                        restaurant.printHowManyOrdersPerCustomer();
                        break;
                    case "3": // How many orders per town
                        restaurant.printHowManyOrdersPerTown();
                        break;
                    case "4": // All sales according to criteria 1 - 3 (total, per customer, per location)
                        restaurant.printSales();
                        break;
                    case "5": // Ordered most often
                        restaurant.printOrderedMostOften();
                        break;
                    case "6": // DESC order orderes
                        restaurant.printOrdersDesc();
                        break;
                    default:
                        System.out.println("Error");
                }
                break;
            case "5": // CSV export (OrderID;customerID;totalPrice)
                String path = userInput("Wo willst du die Datei speichern? Pfad angeben: (Ohne datei!)");
                String fileName = userInput("Dateiname: (ohne .csv)");
                writeCsv(restaurant, path, fileName, Write.ORDER);
                break;
            case "6":
                path = userInput("Wo willst du die Datei speichern? Pfad angeben: (Ohne datei!)");
                fileName = userInput("Dateiname: (ohne .csv)");
                writeCsv(restaurant, path, fileName, Write.INGREDIENT);
                break;
            default:
                System.out.println("Error");
        }
    }

    private static boolean checkIfAnotherIngredient(String anotherIngredient) {
        // Check if user wants to set another ingredient (JA/NEIN)
        Scanner scanner = new Scanner(System.in);
        boolean isNewIngredient = false;

        System.out.println(anotherIngredient);
        String userInput = scanner.nextLine();

        if (userInput.equalsIgnoreCase("JA")) {
            isNewIngredient = true;
        }
        return isNewIngredient;
    }

    private static Ingredient createNewIngredient(String newIngredientName, double extraPrice) {
        Ingredient ingredient = new Ingredient(newIngredientName, extraPrice);
        return ingredient;
    }

    private static Dish createNewDish(String newDishName, double price) {
        Dish dish = new Dish(newDishName, price);
        return dish;
    }

    private static String userInput(String message) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(message);
        String userInput = scanner.nextLine();
        return userInput;
    }

    private static void writeCsv(Restaurant restaurant, String path, String fileName, Enum write) {
        // Write new csv (OrderID;customerID;totalPrice)
        File exportFile = new File(path + "\\" + fileName + ".csv");
        try {
            FileWriter fileWriter = new FileWriter(exportFile, false);

            if(write.equals(Write.ORDER)) {
                fileWriter.write("BestellId;KundeNr;Gesamtpreis\n");
                fileWriter.write(restaurant.getOrderText());
            } else if (write.equals(Write.INGREDIENT)) {
                fileWriter.write("Zutat;Anzahl\n");
                fileWriter.write(restaurant.usedIngredients());
            }

            System.out.println("Datei erfolgreich erstellt!");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
