package io.cucumber.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.json.JSONArray;

import java.io.File;
import java.io.FileReader;
import java.util.List;

public class CsvUtils {

    public JSONArray updateJsonWithCsvArray(File csvFile) {

        JSONArray jsonArray = new JSONArray();
        try {
            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(csvFile);

            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();

            jsonArray.putAll(allData);
            // print Data
            for (String[] row : allData) {
                for (String cell : row) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
