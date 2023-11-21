package com.creativelabs.scriptscreator.scriptshandle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HideUnnecessaryDialogues {

    private void commentDialoguesInScript(String filePath) {
        int dialogueCounter = 0;
        StringBuilder script = new StringBuilder();


        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
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
                FileWriter writeScript = new FileWriter(filePath);
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

    private void unCommentDialoguesInScript(String filePath) {
        int dialogueCounter = 0;
        StringBuilder script = new StringBuilder();


        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();

            try {

                while (line != null) {
                    if (line.contains("//") && line.contains("AI_Output")) {
                        script = new StringBuilder(script + line.replace("//", "").replace("\"); ", "\"); //") + "\n");
                    } else {
                        script.append(line).append("\n");
                    }
                    line = reader.readLine();
                }
                System.out.println(script);
                reader.close();
                FileWriter writeScript = new FileWriter(filePath);
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

    public static void main(String[] args) throws IOException {
        HideUnnecessaryDialogues dialogue = new HideUnnecessaryDialogues();
        ReadAndModifyNpc readAndModifyNpc = new ReadAndModifyNpc();
        String gothicPath = "E:/Gothic II";
        String questCodeName = "DIA_MainQuest_Antidotum_Pirates .d";
        //dialogue.commentDialoguesInScript(gothicPath, questCodeName);
        //dialogue.unCommentDialoguesInScript(gothicPath, questCodeName);

        List<String> files = readAndModifyNpc.filesNamesList("E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\Dialoge");
        System.out.println(files);
        System.out.println(files.size());
        for (String filePath: files) {
            dialogue.commentDialoguesInScript(filePath);
            //dialogue.unCommentDialoguesInScript(filePath);
        }
    }
}


