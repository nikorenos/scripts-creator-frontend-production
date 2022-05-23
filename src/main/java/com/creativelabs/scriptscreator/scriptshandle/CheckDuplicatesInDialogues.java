package com.creativelabs.scriptscreator.scriptshandle;


import java.io.*;

import static jdk.nashorn.internal.objects.NativeString.indexOf;

public class CheckDuplicatesInDialogues {

    public String[] extractAIOutput(String line) {
        int startString1 = 0;
        int startString2 = 0;
        String findString = "\"";
        String findString2 = "); //";
        String dialogue = "";
        String dialogueLine = "";
        String[] dialogueParts = null;

        while (startString1 != -1) {
            startString1 = line.indexOf(findString, startString1);
            startString2 = line.indexOf(findString2, startString2);

            dialogue = line.substring(startString1 + 1, startString2 - 1) + "_" + line.substring(startString2 + 5);
            dialogueParts = dialogue.split("_");
            startString1 = -1;
        }
        return dialogueParts;
    }



    public static void main(String[] args) {

        CheckDuplicatesInDialogues check = new CheckDuplicatesInDialogues();

        String dialoguePath = "E:\\Gothic 2\\_Work\\data\\Scripts\\Content\\Story\\Dialoge\\DIA_SideQuest_Gambler.d";
        File file = new File(dialoguePath);

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while (line != null) {
                if (line.contains("AI_Output")) {
                    String[] dialogueLine = check.extractAIOutput(line);
                    //System.out.println("dialogueLine: " + dialogueLine);
                }

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

