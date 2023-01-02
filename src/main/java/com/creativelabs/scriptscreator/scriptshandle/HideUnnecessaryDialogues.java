package com.creativelabs.scriptscreator.scriptshandle;

import java.io.*;

public class HideUnnecessaryDialogues {

    private void commentDialoguesInScript(String gothicFolderPath, String questCodeName) {
        int dialogueCounter = 0;
        String fileFolderPath = gothicFolderPath + "/_Work/data/Scripts/Content/Story/Dialoge/DIA_MainQuest_" + questCodeName + ".d";
        StringBuilder script = new StringBuilder();


        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileFolderPath));
            String line = reader.readLine();

            try {

                while (line != null) {
                    if (line.startsWith("INSTANCE")) {
                        System.out.println("INSTANCE");
                        dialogueCounter = 0;
                        script.append(line).append("\n");
                    } else if (line.contains("AI_Output")) {
                        if (dialogueCounter == 0) {
                            script.append(line).append("\n");
                            dialogueCounter += 1;
                        } else {
                            System.out.println("//");
                            script.append("//").append(line).append("\n");
                        }
                    } else {
                        script.append(line).append("\n");
                    }
                    line = reader.readLine();
                }
                System.out.println(script);
                reader.close();
                FileWriter writeScript = new FileWriter(fileFolderPath);
                writeScript.write(script.toString());
                writeScript.close();
                System.out.println("Dialogue successfully wrote to the file.");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("An error with dialogue occurred.");
            e.printStackTrace();
        }
    }

    private void unCommentDialoguesInScript(String gothicFolderPath, String questCodeName) {
        int dialogueCounter = 0;
        String fileFolderPath = gothicFolderPath + "/_Work/data/Scripts/Content/Story/Dialoge/DIA_MainQuest_" + questCodeName + ".d";
        StringBuilder script = new StringBuilder();


        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileFolderPath));
            String line = reader.readLine();

            try {

                while (line != null) {
                    if (line.startsWith("//\tAI_Output")) {
                        script = new StringBuilder(script + line.substring(2) + "\n");
                    } else {
                        script.append(line).append("\n");
                    }
                    line = reader.readLine();
                }
                System.out.println(script);
                reader.close();
                FileWriter writeScript = new FileWriter(fileFolderPath);
                writeScript.write(script.toString());
                writeScript.close();
                System.out.println("Dialogue successfully wrote to the file.");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("An error with dialogue occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HideUnnecessaryDialogues dialogue = new HideUnnecessaryDialogues();
        String gothicPath = "E:/Gothic II";
        String questCodeName = "Friends";
        dialogue.commentDialoguesInScript(gothicPath, questCodeName);
        //dialogue.unCommentDialoguesInScript(gothicPath, questCodeName);
    }
}


