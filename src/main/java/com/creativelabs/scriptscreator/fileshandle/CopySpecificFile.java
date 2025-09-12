package com.creativelabs.scriptscreator.fileshandle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;

public class CopySpecificFile {
    public static void copyFileIfNewer(String sourceDir, String destDir, String fileName) {
        Path sourceFolder = Paths.get(sourceDir);
        Path destinationFolder = Paths.get(destDir);
        Path sourceFile = sourceFolder.resolve(fileName);
        Path destFile = destinationFolder.resolve(fileName);

        if (!Files.exists(sourceFile)) {
            System.out.println("Source file does not exist: " + sourceFile);
            return;
        }

        try {
            boolean shouldCopy = true;

            if (Files.exists(destFile)) {
                FileTime sourceTime = Files.getLastModifiedTime(sourceFile);
                FileTime destTime   = Files.getLastModifiedTime(destFile);

                shouldCopy = sourceTime.compareTo(destTime) > 0;
            }

            if (shouldCopy) {
                Files.copy(sourceFile, destFile, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Copied: " + sourceFile + " to " + destFile);
            } else {
                System.out.println("Destination file is up-to-date, skipping: " + fileName);
            }

        } catch (IOException e) {
            System.err.println("Failed to copy file: " + e.getMessage());
        }
    }
}

