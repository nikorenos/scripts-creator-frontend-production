package com.creativelabs.scriptscreator.buildmodpack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;


public class CopyNewerFiles {

    public static void copyNewerFiles(String sourceFolderPath, String destinationFolderPath) throws IOException {
        Path sourceFolder = Paths.get(sourceFolderPath);
        Path destinationFolder = Paths.get(destinationFolderPath);
        if (!Files.isDirectory(sourceFolder) || !Files.isDirectory(destinationFolder)) {
            throw new IllegalArgumentException("Both paths must be directories");
        }
        System.out.println("Copy to: " + destinationFolderPath);

        // 1. Find the latest modified time in destination folder
        FileTime latestInDest;
        try (Stream<Path> destFiles = Files.list(destinationFolder)) {
            Optional<FileTime> maxTime = destFiles
                    .filter(Files::isRegularFile)
                    .map(path -> {
                        try {
                            return Files.getLastModifiedTime(path);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .max(Comparator.naturalOrder());

            latestInDest = maxTime.orElse(FileTime.fromMillis(0)); // fallback: epoch
        }

        // 2. Copy newer files from source to destination
        try (Stream<Path> sourceFiles = Files.list(sourceFolder)) {
            sourceFiles.filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            FileTime fileTime = Files.getLastModifiedTime(file);
                            if (fileTime.compareTo(latestInDest) > 0) {
                                Path target = destinationFolder.resolve(file.getFileName());
                                Files.copy(file, target, StandardCopyOption.REPLACE_EXISTING);
                                System.out.println("Copied: " + file.getFileName());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }



    public static void main(String[] args) throws IOException {
        String sourceFolder = "E:\\RepoZW2\\_Work\\data\\Anims\\_compiled";
        String destinationFolder = "C:\\Users\\akcja\\Desktop\\Nowy folder\\_COMPILED";

        copyNewerFiles(sourceFolder, destinationFolder);
    }
}

