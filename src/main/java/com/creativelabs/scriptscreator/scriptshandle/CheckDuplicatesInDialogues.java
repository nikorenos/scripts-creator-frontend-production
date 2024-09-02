package com.creativelabs.scriptscreator.scriptshandle;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CheckDuplicatesInDialogues {

    public String[] extractAIOutput(String line) {
        int startString1 = 0;
        int startString2 = 0;
        String findString = "\"";
        String findString2 = "); //";
        String dialogueLine;
        String[] dialogueParts = null;

        while (startString1 != -1) {
            startString1 = line.indexOf(findString, startString1);
            startString2 = line.indexOf(findString2, startString2);

            dialogueLine = line.substring(startString1 + 1, startString2 - 1) + "#" + line.substring(startString2 + 5);
            dialogueParts = dialogueLine.split("#");
            startString1 = -1;
        }
        return dialogueParts;
    }


    public static void main(String[] args) {

        CheckDuplicatesInDialogues check = new CheckDuplicatesInDialogues();

        String dialoguePath = "E:\\Gothic II Old\\_work\\Data\\Scripts\\Content\\Story\\Dialoge\\DIA_MainQuest_Temple.d";
        File file = new File(dialoguePath);
        Map<String, String> dialoguesMap = new HashMap<>();
        int duplicates = 0;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while (line != null) {
                if (line.contains("AI_Output")) {
                    String[] dialogueLine = check.extractAIOutput(line);
                    for (Map.Entry<String, String> entry : dialoguesMap.entrySet()) {
                        if (entry.getKey().equals(dialogueLine[0]) && !entry.getValue().equals(dialogueLine[1])) {
                            duplicates++;
                            System.out.println(entry.getKey() + ": " + entry.getValue());
                            System.out.println(entry.getKey() + ": " + dialogueLine[1]);
                            System.out.println();
                        }
                    }
                    dialoguesMap.put(dialogueLine[0], dialogueLine[1]);
                }

                line = reader.readLine();
            }
            System.out.println("Total duplicates: " + duplicates);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

