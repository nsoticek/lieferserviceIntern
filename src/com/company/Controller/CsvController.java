package com.company.Controller;

import com.company.DbHelper.CsvDb;
import com.company.DbHelper.DbConnector;
import com.company.models.Write;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvController {

    public static void writeCsv(String path, String fileName, Enum write, DbConnector dbConnector) {
        // Write new csv Dish(OrderID;customerID;totalPrice) or ingredient (Zutat;Anzahl)
        File exportFile = new File(path + "\\" + fileName + ".csv");
        try {
            FileWriter fileWriter = new FileWriter(exportFile, false);

            if (write.equals(Write.ORDER)) {
                fileWriter.write("BestellId;KundeNr;Gesamtpreis\n");
                fileWriter.write(CsvDb.getOrdersCsvString(dbConnector));
            } else if (write.equals(Write.INGREDIENT)) {
                fileWriter.write("Zutat;Anzahl\n");
                fileWriter.write(CsvDb.getIngredientsCsvString(dbConnector));
            }

            System.out.println("Datei erfolgreich erstellt!");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
