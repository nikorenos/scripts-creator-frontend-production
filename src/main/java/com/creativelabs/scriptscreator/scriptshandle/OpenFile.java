package com.creativelabs.scriptscreator.scriptshandle;

import java.awt.*;
import java.io.File;

public class OpenFile {

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
}
