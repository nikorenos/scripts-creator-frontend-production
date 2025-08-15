package com.creativelabs.scriptscreator.fileshandle;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFilesFromSubfolders {
    public static void moveFilesToDestinationFolder(String sourceFolder, String destinationFolder) {
        Path sourceDir = Paths.get(sourceFolder);
        Path destinationDir = Paths.get(destinationFolder);

        try {
            // Tworzymy folder docelowy, jeśli nie istnieje
            if (!Files.exists(destinationDir)) {
                Files.createDirectories(destinationDir);
            }

            Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (Files.isRegularFile(file)) {
                        Path targetFile = destinationDir.resolve(file.getFileName());
                        Files.copy(file, targetFile, StandardCopyOption.COPY_ATTRIBUTES);
                        System.out.println("Skopiowano: " + file + " -> " + targetFile);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

            System.out.println("Kopiowanie zakończone.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

