package com.creativelabs.scriptscreator.fileshandle;

import com.creativelabs.scriptscreator.excel.ReadExcelFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.creativelabs.scriptscreator.fileshandle.CopyFilesFromSubfolders.moveFilesToDestinationFolder;
import static com.creativelabs.scriptscreator.scriptshandle.FileAndFolderOperations.getFoldersNames;

public class RenameFiles {
    public static void main(String[] args) throws IOException {
//        String folderWithFilesPath = "C:\\Users\\akcja\\Desktop\\Nowy folder\\Temp\\Nowy folder\\Nowe twarze";
//        String baseName = "Hum_Head_V";
//        int startingNumber = 230;
//        renameFilesInFolder(folderWithFilesPath, baseName, startingNumber);

        RenameFiles renameFiles = new RenameFiles();
        for (String folderName : getFoldersNames(new File("E:\\Dubbbing ZW2\\ToRename").toPath())) {
            renameWavFilesInFolder(folderName, renameFiles.collectDialogueNames(folderName.substring(folderName.lastIndexOf("\\") + 1), true));
        }
        moveFilesToDestinationFolder("E:\\Dubbbing ZW2\\ToRename", "E:\\Dubbbing ZW2\\Renamed");

    }

    public static void renameFilesInFolder(String folderPath, String baseName, int startingNumber) {
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("The specified folder path does not exist or is not a directory.");
            return;
        }

        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No files found in the specified folder.");
            return;
        }

        int count = startingNumber;
        for (File file : files) {
            if (file.isFile()) {
                String extension = getFileExtension(file);
                String newFileName = baseName + count + "_C0" + (extension.isEmpty() ? "" : "." + extension);
                File newFile = new File(folder, newFileName);
                if (file.renameTo(newFile)) {
                    System.out.println("Renamed " + file.getName() + " to " + newFile.getName());
                } else {
                    System.out.println("Failed to rename " + file.getName());
                }
                count++;
            }
        }
    }

    public List<String> collectDialogueNames(String npcName, Boolean forHero) throws IOException {
        ReadExcelFile readExcelFile = new ReadExcelFile();
        String excelFilePath = "E:\\Dubbbing ZW2\\GoldenGate2_Dialogues_Final.xlsx";
        String actualNpcName = npcName;
        if (forHero) actualNpcName = "Morris";

        String finalActualNpcName = actualNpcName;
        return readExcelFile.readExcelFile(excelFilePath, npcName).stream()
                .filter(sublist -> sublist.size() > 1 && finalActualNpcName.toUpperCase().equals(sublist.get(1)))
                .map(sublist -> sublist.get(0))
                .collect(Collectors.toList());
    }

    public static void renameWavFilesInFolder(String folderPath, List<String> newNames) {
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("The specified folder path does not exist or is not a directory.");
            return;
        }

        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No files found in the specified folder.");
            return;
        }

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isFile()) {
                String extension = getFileExtension(file);
                String newFileName = "lack of extension";
                if (extension.equals("wav")) newFileName = newNames.get(i) + "." + extension;
                else System.out.println("lack of extension: " + file.getName());
                File newFile = new File(folder, newFileName);
                if (file.renameTo(newFile)) {
                    System.out.println("Renamed " + file.getName() + " to " + newFile.getName());
                } else {
                    System.out.println("Failed to rename file: " + file.getName() + " to: " + newFile.getName());
                }
            }
        }
    }

    public static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndex = name.lastIndexOf('.');
        if (lastIndex == -1) {
            return "";
        }
        return name.substring(lastIndex + 1);
    }
}
