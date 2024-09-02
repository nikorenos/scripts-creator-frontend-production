package com.creativelabs.scriptscreator.fileshandle;

import java.io.File;

public class RenameFiles {
    public static void main(String[] args) {
        String folderPath = "C:\\Users\\akcja\\Desktop\\Nowy folder\\Temp\\Nowy folder\\Nowe twarze";
        String baseName = "Hum_Head_V";
        int startingNumber = 230;

        renameFilesInFolder(folderPath, baseName, startingNumber);
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

    public static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndex = name.lastIndexOf('.');
        if (lastIndex == -1) {
            return "";
        }
        return name.substring(lastIndex + 1);
    }
}
