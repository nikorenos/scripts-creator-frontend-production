package com.creativelabs.scriptscreator.fileshandle;


import java.io.File;

public class CheckIfDirectoryHasFiles {
    public static void main(String[] args) {
        // Ścieżka do folderu głównego, który chcesz przeszukać
        String folderPath = "E:\\Dubbbing ZW2\\Nagrywki";
        // Nazwa poszukiwanych podfolderów
        String targetFolderName = "Gotowe nagrania";

        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            int count = countAndDisplayFoldersWithFiles(folder, targetFolderName);
            System.out.println("Liczba podfolderów o nazwie \"" + targetFolderName + "\" zawierających pliki: " + count);
        } else {
            System.out.println("Podana ścieżka nie istnieje lub nie jest folderem.");
        }
    }

    private static int countAndDisplayFoldersWithFiles(File folder, String targetFolderName) {
        int count = 0;
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (file.getName().equals(targetFolderName) && containsFiles(file)) {
                        count++;
                        System.out.println("Folder wyżej: " + file.getParentFile().getAbsolutePath());
                    }
                    // Rekurencyjne przeszukiwanie podfolderów
                    count += countAndDisplayFoldersWithFiles(file, targetFolderName);
                }
            }
        }
        return count;
    }

    private static boolean containsFiles(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    return true;
                }
            }
        }
        return false;
    }
}