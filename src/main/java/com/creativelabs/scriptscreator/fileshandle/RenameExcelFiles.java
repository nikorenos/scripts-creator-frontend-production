package com.creativelabs.scriptscreator.fileshandle;

import java.io.File;

public class RenameExcelFiles {
    public static void main(String[] args) {
        File root = new File("E:\\Dubbbing ZW2\\ToRename");
        if (!root.exists() || !root.isDirectory()) {
            System.out.println("The given path does not exist or is not a directory.");
            return;
        }

        File[] folders = root.listFiles(File::isDirectory);
        if (folders == null) return;

        for (File folder : folders) {
            File[] excelFiles = folder.listFiles(file ->
                    file.isFile() &&
                            (file.getName().toLowerCase().endsWith(".xlsx") ||
                                    file.getName().toLowerCase().endsWith(".xls"))
            );

            if (excelFiles != null && excelFiles.length > 0) {
                File excelFile = excelFiles[0]; // take the first found
                String extension = excelFile.getName().substring(excelFile.getName().lastIndexOf('.'));
                File newFile = new File(folder, folder.getName() + extension);

                if (excelFile.renameTo(newFile)) {
                    System.out.println("Renamed: " + excelFile.getName() + " -> " + newFile.getName());
                } else {
                    System.out.println("Failed to rename: " + excelFile.getAbsolutePath());
                }
            }
        }
    }
}

