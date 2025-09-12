package com.creativelabs.scriptscreator.buildmodpack;

import java.io.IOException;

public class GothicModBuilder {
    public static void buildModPack() {
        String gothicVdfsPath = "E:\\Gothic II Old\\_work\\tools\\VDFS\\GothicVDFS.exe";
        String scriptPath = "E:\\Gothic II Old\\_work\\tools\\VDFS\\BuildGG2.vm";

        try {
            String[] command = {gothicVdfsPath, "/B", scriptPath};
            Process process = Runtime.getRuntime().exec(command);

            int exitCode = process.waitFor();
            System.out.println("GothicVDFS exited with code: " + exitCode);

            if (exitCode == 0) {
                System.out.println("Successfully built VDF from script.");
            } else {
                System.err.println("Failed to build VDF. Check VDFS output for errors.");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}