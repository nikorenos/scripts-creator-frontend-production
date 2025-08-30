package com.creativelabs.scriptscreator.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadExcelFile {

    public List<List<String>> readExcelFile(String path, String sheetName) throws IOException {
        FileInputStream file = new FileInputStream(path);
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = null;
        if (sheetName != null) sheet = workbook.getSheet(sheetName);
        if (sheet == null) sheet = workbook.getSheet("Kopia arkusza " + sheetName);
        else workbook.getSheetAt(0);

        List<List<String>> dialogues = new ArrayList<>();
        System.out.println("Nazwa ścieżki: " + path);
        System.out.println("Nazwa postaci: " + sheetName);
        for (Row row : sheet) {

            if (row.getCell(1) != null) {
                if (!row.getCell(1).getStringCellValue().isEmpty() && !row.getCell(0).getStringCellValue().equals("Nazwa kwestii")) {
                    List<String> dialogue = new ArrayList<>();
                    dialogue.add(row.getCell(0).getStringCellValue());
                    dialogue.add(row.getCell(1).getStringCellValue());
                    dialogue.add(row.getCell(2).getStringCellValue());
                    dialogues.add(dialogue);
                }
            }
        }
        return dialogues;
    }

//    public static void main(String[] args) throws IOException {
//        String path = "E:\\Programowanie\\Data\\input.xlsx"
//
//        ReadExcelFile readExcelFile = new ReadExcelFile();
//        List<List<String>> dialogues = readExcelFile.readExcelFile(path);
//
//        System.out.println(dialogues);
//        System.out.println(dialogues.get(2).get(0));
//        System.out.println(dialogues.get(2).get(1));
//
//    }

    public static void main(String[] args) {
        String inputFile = "E:\\Programowanie\\Data\\input.xlsx";
        String searchKeyword = "XARDAS";
        String columnNameToSearch = "Kto wypowiada";
        String columnNameToRetrieve = "Nazwa kwestii";

        List<String> listOfNames = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(inputFile);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            int columnIndexToSearch = -1;
            int columnIndexToRetrieve = -1;

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null && cell.getStringCellValue().equals(columnNameToSearch)) {
                    columnIndexToSearch = i;
                }
                if (cell != null && cell.getStringCellValue().equals(columnNameToRetrieve)) {
                    columnIndexToRetrieve = i;
                }
            }

            if (columnIndexToSearch == -1 || columnIndexToRetrieve == -1) {
                System.out.println("Column not found.");
                return;
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Cell cellToSearch = row.getCell(columnIndexToSearch);
                Cell cellToRetrieve = row.getCell(columnIndexToRetrieve);

                if (cellToSearch != null && cellToSearch.getStringCellValue().equals(searchKeyword) &&
                        cellToRetrieve != null) {
                    listOfNames.add(cellToRetrieve.getStringCellValue());
                }
            }

            System.out.println("List of values for " + searchKeyword + ":");
            for (String value : listOfNames) {
                System.out.println(value);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        String folderPath = "E:\\Programowanie\\Data\\Files";

        // List files in the folder
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            // Ensure the number of names matches the number of files
            if (listOfNames.size() != files.length) {
                System.out.println("Number of names doesn't match the number of files.");
                return;
            }

            // Rename each file with the corresponding name
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String newName = listOfNames.get(i);
                File newFile = new File(folderPath + "\\" + newName + ".wav");
                if (file.renameTo(newFile)) {
                    System.out.println("File " + file.getName() + " renamed to " + newName);
                } else {
                    System.out.println("Failed to rename file: " + file.getName() + " to: " + newName);
                }
            }
        } else {
            System.out.println("No files found in the folder.");
        }
    }
}
