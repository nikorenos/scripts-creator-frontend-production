package com.creativelabs.scriptscreator.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadExcelFile {

    public List<List<String>> readExcelFile(String path) throws IOException {
        FileInputStream file = new FileInputStream(path);
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);

        List<List<String>> dialogues = new ArrayList<>();
        for (Row row : sheet) {

            if (!row.getCell(0).getStringCellValue().isEmpty() && !row.getCell(0).getStringCellValue().equals("Nazwa kwestii")) {
                List<String> dialogue = new ArrayList<>();
                dialogue.add(row.getCell(0).getStringCellValue());
                dialogue.add(row.getCell(2).getStringCellValue());
                dialogues.add(dialogue);
            }
        }
        return dialogues;
    }

    public static void main(String[] args) throws IOException {
        String path = "E:\\dev\\scripts-creator-frontend-production\\temp.xlsx";

        ReadExcelFile readExcelFile = new ReadExcelFile();
        List<List<String>> dialogues = readExcelFile.readExcelFile(path);

        System.out.println(dialogues);
        System.out.println(dialogues.get(2).get(0));
        System.out.println(dialogues.get(2).get(1));

    }
}
