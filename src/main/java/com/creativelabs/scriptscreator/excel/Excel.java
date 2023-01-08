package com.creativelabs.scriptscreator.excel;

import com.creativelabs.scriptscreator.scriptshandle.ExtractDialoguesFromScript;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Excel {

    public static void main(String[] args) throws IOException {


        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Persons");
        sheet.setColumnWidth(0, 16000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 20000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        //headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        //headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Nazwa kwestii");

        headerCell = header.createCell(1);
        headerCell.setCellValue("Kto wypowiada");

        headerCell = header.createCell(2);
        headerCell.setCellValue("Treść");

        headerCell = header.createCell(3);
        headerCell.setCellValue("Status");

        CellStyle specialStyle = workbook.createCellStyle();
        specialStyle.setWrapText(true);
        XSSFFont greenDialogueFont = ((XSSFWorkbook) workbook).createFont();
        greenDialogueFont.setFontName("Arial");
        greenDialogueFont.setColor(IndexedColors.LIGHT_BLUE.getIndex());
        specialStyle.setFont(greenDialogueFont);

        CellStyle normalStyle = workbook.createCellStyle();
        normalStyle.setWrapText(true);
        XSSFFont normalDialogueFont = ((XSSFWorkbook) workbook).createFont();
        normalDialogueFont.setFontName("Arial");
        normalStyle.setFont(normalDialogueFont);


        ExtractDialoguesFromScript extractDialoguesFromScript = new ExtractDialoguesFromScript();
        String scriptPath = "E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\Dialoge\\DIA_MainQuest_WoodForFeast.d";
        List<List<String>> dialogues = extractDialoguesFromScript.extractDialoguesFromScript(scriptPath);
        for (int i = 0; i < dialogues.size(); i++) {

            Row row = sheet.createRow(i + 1);
            String currentNpc = "";
            for (int n = 0; n < dialogues.get(i).size(); n++) {
                currentNpc = dialogues.get(i).get(1);
                Cell cell = row.createCell(n);
                cell.setCellValue(dialogues.get(i).get(n));
                if ((n == 2) && currentNpc.equals("MORRIS")) {
                    cell.setCellStyle(specialStyle);
                } else {
                    cell.setCellStyle(normalStyle);
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

}
