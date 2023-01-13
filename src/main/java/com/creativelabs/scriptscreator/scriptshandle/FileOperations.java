package com.creativelabs.scriptscreator.scriptshandle;

import java.awt.*;
import java.io.File;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileOperations {

    public static void openFile(String path) {
        try {
            File savedExcelDoc = new File(path);
            Desktop desktop = Desktop.getDesktop();
            if (savedExcelDoc.exists()) {
                desktop.open(savedExcelDoc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<String> getAbsolutePathForFiles(String dir, String filter) {
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getAbsolutePath)
                .filter(name -> name.contains(filter))
                .collect(Collectors.toSet());
    }


    public void printFilesNames(String dir, String filter) {
        Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(name -> name.contains(filter))
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        FileOperations fileOperations = new FileOperations();
        String path = "E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\Dialoge";
        fileOperations.printFilesNames(path, "Quest");
        Set<String> list = fileOperations.getAbsolutePathForFiles(path, "Quest");
        System.out.println(list);
    }
}
