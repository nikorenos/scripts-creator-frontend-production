package com.creativelabs.scriptscreator.translator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static com.creativelabs.scriptscreator.translator.DeepLTranslator.translateDeepL;
import static java.nio.file.Files.newInputStream;

public class CsvFolderProcessor {

    public static void main(String[] args) {
        String folderPath = "E:\\Materiały ZW\\Translation\\for_translation";

        File folder = new File(folderPath);
        if (!folder.isDirectory()) {
            System.err.println("The specified path is not a directory.");
            return;
        }

        File[] csvFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        if (csvFiles == null || csvFiles.length == 0) {
            System.out.println("No CSV files found in the specified folder.");
            return;
        }

        for (File csvFile : csvFiles) {
            processCsvFile(csvFile);
        }
    }

    private static void processCsvFile(File csvFile) {
        System.out.println("Processing file: " + csvFile.getName());

        try (
            CSVReader reader = new CSVReader(new InputStreamReader(newInputStream(csvFile.toPath()), StandardCharsets.UTF_8))
        ) {
            List<String[]> allRows = reader.readAll();
            if (allRows.isEmpty()) {
                System.out.println("The file " + csvFile.getName() + " is empty. Skipping.");
                return;
            }

            String[] header = allRows.get(0);
            int sourceIndex = -1;

            // Find the "source" column index
            for (int i = 0; i < header.length; i++) {
                if ("source".equalsIgnoreCase(header[i])) {
                    sourceIndex = i;
                    break;
                }
            }

            if (sourceIndex == -1) {
                System.out.println("The 'source' column was not found in " + csvFile.getName() + ". Skipping.");
                return;
            }

            // Add "target" column if it doesn't exist
            int targetIndex = -1;
            for (int i = 0; i < header.length; i++) {
                if ("target".equalsIgnoreCase(header[i])) {
                    targetIndex = i;
                    break;
                }
            }

            if (targetIndex == -1) {
                header = appendColumn(header, "target");
                targetIndex = header.length - 1;
            }

            List<String[]> updatedRows = new ArrayList<>();
            updatedRows.add(header);

            for (int rowIndex = 1; rowIndex < allRows.size(); rowIndex++) {
                String[] row = allRows.get(rowIndex);
                String sourceValue = row[sourceIndex];
                String targetValue = translateDeepL(sourceValue, "pl", "en-GB");

                if (row.length <= targetIndex) {
                    row = appendColumn(row, targetValue);
                } else {
                    row[targetIndex] = targetValue;
                }

                updatedRows.add(row);
            }

            try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(Files.newOutputStream(csvFile.toPath()), StandardCharsets.UTF_8))) {
                writer.writeAll(updatedRows);
            }
            System.out.println("Updated file: " + csvFile.getName());

        } catch (IOException | CsvException e) {
            System.err.println("Error processing file " + csvFile.getName() + ": " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] appendColumn(String[] row, String newValue) {
        String[] newRow = new String[row.length + 1];
        System.arraycopy(row, 0, newRow, 0, row.length);
        newRow[row.length] = newValue;
        return newRow;
    }
}

