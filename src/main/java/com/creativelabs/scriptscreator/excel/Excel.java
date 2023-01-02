package com.creativelabs.scriptscreator.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Excel {

    public static void main(String[] args) throws IOException {

        List<List<String>> dialogues = new ArrayList<>();

        List<String> dialogue = new ArrayList<>();
        dialogue.add("DIA_Otmar_TempleGate_12_01");
        dialogue.add("");
        dialogue.add("Masz pieczęć wyznawców? Doskonale!");

        dialogues.add(dialogue);

        List<String> dialogue2 = new ArrayList<>();
        dialogue2.add("DIA_Otmar_TempleGate_15_02");
        dialogue2.add("Morris");
        dialogue2.add("Tak, co dalej?");
        dialogues.add(dialogue2);

        System.out.println(dialogues);



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
        //headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Kto wypowiada");

        headerCell = header.createCell(2);
        headerCell.setCellValue("Treść");

        headerCell = header.createCell(3);
        headerCell.setCellValue("Status");

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);


        for (int i = 0; i < dialogues.size(); i++) {

            Row row = sheet.createRow(i+1);

            for (int n = 0; n < dialogues.get(i).size(); n++) {
                Cell cell = row.createCell(n);
                cell.setCellValue(dialogues.get(i).get(n));
                cell.setCellStyle(style);
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
