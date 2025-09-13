package com.creativelabs.scriptscreator.buildmodpack;

import java.io.File;
import java.io.IOException;

public class GothicLauncher {


    public static void reparseScripts(String gothicStarterExe, String iniFile)
            throws IOException, InterruptedException {

        ProcessBuilder pb = new ProcessBuilder(
                gothicStarterExe,
                "-game:" + iniFile,
                "-reparse",   // important flag: forces script recompilation
                "-start"
        );

        pb.directory(new File(new File(gothicStarterExe).getParent())); // working dir = Gothic2/System
        pb.redirectErrorStream(true);

        Process process = pb.start();

        // Print output
        new Thread(() -> {
            try (java.io.BufferedReader r =
                         new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = r.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("GothicStarter failed with exit code " + exitCode);
        }
    }

    public static void launchGothic2() {
        try {
            reparseScripts(
                    "E:\\Gothic II Old\\System\\GothicStarter_mod.exe",
                    "GoldenGate2.ini"
            );
            System.out.println("Scripts reparsed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launchGothic2();
    }
}