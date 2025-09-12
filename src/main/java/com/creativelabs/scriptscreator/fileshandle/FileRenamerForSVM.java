package com.creativelabs.scriptscreator.fileshandle;

import java.io.File;

public class FileRenamerForSVM {

    public static void main(String[] args) {
        String folderPath = "E:\\Dubbbing ZW2\\NPC\\SVM HunterFeast Renamed\\Alissa";
        String newName = "Alissa";
        renameFiles(folderPath, newName);
    }

    private static void renameFiles(String folderPath, String newName) {
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid folder path: " + folderPath);
            return;
        }

        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No files found in folder: " + folderPath);
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                String oldName = file.getName();
                if (oldName.startsWith("SVM_7_")) {
                    String newFileName = oldName.replaceFirst("SVM_7_", "SVM_" + newName + "_");
                    File newFile = new File(folder, newFileName);
                    if (file.renameTo(newFile)) {
                        System.out.println("Renamed: " + oldName + " -> " + newFileName);
                    } else {
                        System.out.println("Failed to rename: " + oldName);
                    }
                }
            }
        }
    }
}

