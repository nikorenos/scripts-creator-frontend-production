package com.creativelabs.scriptscreator.scriptshandle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReplaceText {

    private void replaceDialoguesAndLogEntriesInScript(String scriptPath, List<List<String>> dialogues) {
        StringBuilder script = new StringBuilder();
        int counter = 0;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(scriptPath));
            String line = reader.readLine();

            try {

                while (line != null) {
                    if ((dialogues.size() > counter)) {
                        if (line.toLowerCase().contains("AI_Output".toLowerCase())) {
                            System.out.println("size: " + dialogues.size());
                            System.out.println("licznik:" + counter);
                            System.out.println(dialogues.get(counter));
                            if (line.contains(dialogues.get(counter).get(0))) {
                                String oldDialogue = line.substring(line.indexOf("\"); //") + 6);
                                line = line.replace(oldDialogue, dialogues.get(counter).get(1));
                            } else {
                                System.out.println("Could not find: " + dialogues.get(counter).get(1));
                            }

                            counter++;
                        } else if (line.toLowerCase().contains("mission")) {
//                        if (dialogues.size() >= counter) {
//
//                        }
//                        counter++;
                        }
                    }
                    script.append(line).append("\n");
                    line = reader.readLine();
                }
                reader.close();
                FileWriter writeScript = new FileWriter(scriptPath);
                writeScript.write(script.toString());
                writeScript.close();
                System.out.println("Dialogue successfully wrote to the file.");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (
                IOException e) {
            System.out.println("An error with dialogue occurred.");
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws IOException {
        ReplaceText replaceText = new ReplaceText();
        String path = "E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\Dialoge\\DIA_MainQuest_HelpGorn.d";

        List<List<String>> dialogues = new ArrayList<>();

        List<String> dialogue = new ArrayList<>();
        dialogue.add("DIA_Gorn_ThanksForHelp_12_01");
        dialogue.add("Uff, miałem ciężką przeprawę z tymi bandytami. Kim jesteś nieznajomy...");

        dialogues.add(dialogue);

        List<String> dialogue2 = new ArrayList<>();
        dialogue2.add("DIA_Gorn_ThanksForHelp_15_02");
        dialogue2.add("Myśliwy Morris. A ty jesteś Gorn???");
        dialogues.add(dialogue2);

        replaceText.replaceDialoguesAndLogEntriesInScript(path, dialogues);
    }
}
