package com.creativelabs.scriptscreator.scriptshandle;

import java.io.FileWriter;
import java.io.IOException;

public class Settings {

    public void saveSettings (String gothicDirectoryPath) {
        try {
            //create file with users data
            FileWriter writeProjectData = new FileWriter(gothicDirectoryPath + "/ScriptCreatorSettings.txt");
            //create file with users data
            writeProjectData.write(gothicDirectoryPath + "\n");
            writeProjectData.close();
            System.out.println("Project data successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("An error with users list occurred.");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String gothicDirectoryPath = "E:/Gothic 2";
        Settings settings = new Settings();
        settings.saveSettings(gothicDirectoryPath);
    }
}
