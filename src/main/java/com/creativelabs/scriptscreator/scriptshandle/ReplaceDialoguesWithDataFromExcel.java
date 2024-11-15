package com.creativelabs.scriptscreator.scriptshandle;

import com.creativelabs.scriptscreator.excel.ReadExcelFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReplaceDialoguesWithDataFromExcel {

    private void replaceDialoguesAndLogEntriesInScript(String scriptPath, List<List<String>> dialogues) {
        StringBuilder script = new StringBuilder();
        int counter = 0;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(scriptPath));
            String line = reader.readLine();

            try {

                while (line != null) {
                    if ((dialogues.size() > counter)) {
                        if (line.toLowerCase().contains("description")) {
                            line = line.substring(0, line.indexOf("\"") + 1) + dialogues.get(counter).get(1) + "\";";
                            counter++;
                        }

                        if (line.toLowerCase().contains("AI_Output".toLowerCase())) {
                            if (line.contains(dialogues.get(counter).get(0))) {
                                String oldDialogue = line.substring(line.indexOf("\"); //") + 6);
                                line = line.replace(oldDialogue, dialogues.get(counter).get(1));
                            } else {
                                System.out.println("Could not find: " + dialogues.get(counter).get(1));
                            }

                            counter++;
                        }

                        if (line.toLowerCase().contains("Info_AddChoice".toLowerCase()) && line.contains("\"")) {
                            line = line.substring(0, line.indexOf("\"") + 1) + dialogues.get(counter).get(1) + line.substring(line.indexOf("\","));
                            counter++;
                        }

                        if (line.toLowerCase().contains("mission")) {
                            line = line.substring(0, line.indexOf("\"") + 1) + dialogues.get(counter).get(1) + "\");";
                            counter++;
                        }
                    }
                    script.append(line).append("\n");
                    line = reader.readLine();
                }
                reader.close();
                FileWriter writeScript = new FileWriter(scriptPath);
                writeScript.write(script.toString());
                writeScript.close();
                System.out.println("Dialogue successfully wrote to the file.");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (
                IOException e) {
            System.out.println("An error with dialogue occurred.");
            e.printStackTrace();
        }

    }

    public static List<String> getFirstElements(List<List<String>> dialogues) {
        List<String> firstElements = new ArrayList<>();
        for (List<String> innerList : dialogues) {
            if (!innerList.isEmpty() && (!innerList.get(1).equals("MORRIS"))) {
                firstElements.add(innerList.get(0));
            }
        }
        return firstElements;
    }


    public static void main(String[] args) throws IOException {
        ReplaceDialoguesWithDataFromExcel replaceDialoguesWithDataFromExcel = new ReplaceDialoguesWithDataFromExcel();
        ReadExcelFile readExcelFile = new ReadExcelFile();
        String excelFilePath = "E:\\dev\\scripts-creator-frontend-production\\temp.xlsx";
        String path = "E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\Dialoge\\DIA_MainQuest_BloodyCrystalTower_City.d";

        List<List<String>> dialogues = readExcelFile.readExcelFile(excelFilePath, null);
        replaceDialoguesWithDataFromExcel.replaceDialoguesAndLogEntriesInScript(path, dialogues);
    }
}
