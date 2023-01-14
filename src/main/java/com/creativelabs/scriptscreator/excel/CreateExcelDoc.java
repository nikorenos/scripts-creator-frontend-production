package com.creativelabs.scriptscreator.excel;

import com.creativelabs.scriptscreator.scriptshandle.ExtractDialoguesFromScript;
import com.creativelabs.scriptscreator.scriptshandle.FileOperations;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CreateExcelDoc {

    public void createExcelDoc(String scriptPath) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Persons");
        sheet.setColumnWidth(0, 14000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 20000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Nazwa kwestii");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Kto wypowiada");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Treść");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Status");
        headerCell.setCellStyle(headerStyle);

        CellStyle specialStyle = workbook.createCellStyle();
        specialStyle.setWrapText(true);
        XSSFFont lightBlueDialogueFont = workbook.createFont();
        lightBlueDialogueFont.setFontName("Arial");
        lightBlueDialogueFont.setColor(IndexedColors.LIGHT_BLUE.getIndex());
        specialStyle.setFont(lightBlueDialogueFont);

        CellStyle specialStyle2 = workbook.createCellStyle();
        specialStyle2.setWrapText(true);
        XSSFFont orangeDialogueFont = workbook.createFont();
        orangeDialogueFont.setFontName("Arial");
        orangeDialogueFont.setColor(IndexedColors.ORANGE.getIndex());
        specialStyle2.setFont(orangeDialogueFont);

        CellStyle specialStyle3 = workbook.createCellStyle();
        specialStyle3.setWrapText(true);
        XSSFFont greenDialogueFont = workbook.createFont();
        greenDialogueFont.setFontName("Arial");
        greenDialogueFont.setColor(IndexedColors.RED.getIndex());
        specialStyle3.setFont(greenDialogueFont);

        CellStyle normalStyle = workbook.createCellStyle();
        normalStyle.setWrapText(true);
        XSSFFont normalDialogueFont = workbook.createFont();
        normalDialogueFont.setFontName("Arial");
        normalStyle.setFont(normalDialogueFont);


        ExtractDialoguesFromScript extractDialoguesFromScript = new ExtractDialoguesFromScript();
        List<List<String>> dialogues = extractDialoguesFromScript.extractDialoguesFromScript(scriptPath);
        for (int i = 0; i < dialogues.size(); i++) {

            Row row = sheet.createRow(i + 1);
            String currentEntry;
            for (int n = 0; n < dialogues.get(i).size(); n++) {
                currentEntry = dialogues.get(i).get(1);
                Cell cell = row.createCell(n);
                cell.setCellValue(dialogues.get(i).get(n));
                cell.setCellStyle(normalStyle);
                if (n == 2) {
                    if (currentEntry.equals("MORRIS")) {
                        cell.setCellStyle(specialStyle);
                    }
                    if (currentEntry.equals("Wpis do dziennika")) {
                        cell.setCellStyle(specialStyle2);
                    }
                    if (currentEntry.equals("Description")) {
                        cell.setCellStyle(specialStyle2);
                    }
                    if (currentEntry.equals("Choice")) {
                        cell.setCellStyle(specialStyle3);
                    }
                }
            }
        }

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }

    public static void main(String[] args) throws IOException {
        String scriptPath = "E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\Dialoge\\DIA_SideQuest_City_Alcoholic.d";
        CreateExcelDoc createExcelDoc = new CreateExcelDoc();
        createExcelDoc.createExcelDoc(scriptPath);
        FileOperations.openFile("E:\\dev\\scripts-creator-frontend-production\\temp.xlsx");
    }

}
