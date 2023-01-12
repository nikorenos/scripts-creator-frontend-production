package com.creativelabs.scriptscreator.scriptshandle;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ExtractDialoguesFromScript {
    int inconsistenciesCounter = 0;

    public String convertMissionEntry(String line) {
        String entry = "";
        int startDialogueNameIndex = 0;
        int endDialogueNameIndex = 0;
        String findStr1Instance = ", \"";
        String findStr2Instance = "\");";

        while (startDialogueNameIndex != -1) {
            startDialogueNameIndex = line.indexOf(findStr1Instance, startDialogueNameIndex);
            endDialogueNameIndex = line.indexOf(findStr2Instance, endDialogueNameIndex);

            if (startDialogueNameIndex != -1) {
                startDialogueNameIndex += findStr1Instance.length();
                endDialogueNameIndex += findStr2Instance.length();
                entry = line.substring(startDialogueNameIndex, endDialogueNameIndex - 3);
            }
        }
        return entry;
    }

    public List<List<String>> extractDialoguesFromScript(String path) throws IOException {
        List<List<String>> dialogues = new ArrayList<>();
        String npcName = "";
        String npcInstance;
        String currentDialogueInstance = "";
        ScriptToDialogue scriptToDialogue = new ScriptToDialogue();
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();

        try {

            while (line != null) {

                if (line.toLowerCase().contains("INSTANCE".toLowerCase())) {
                    npcName = scriptToDialogue.convertInstance(line)[0];
                    currentDialogueInstance = scriptToDialogue.convertInstance(line)[0] + "_" + scriptToDialogue.convertInstance(line)[1];
                    dialogues.add(Arrays.asList("", "", ""));
                }

                if (line.contains("npc") && line.contains("=") && line.contains(";")) {
                    npcInstance = getNpcInstance(line);
                    if (!npcName.equals(npcInstance)) {
                        inconsistenciesCounter++;
                        System.out.println("----------------------------------");
                        System.out.println("Niezgodnosc npc name z npc instance w dialogu: DIA_" + currentDialogueInstance);
                        System.out.println("npcName:" + npcName);
                        System.out.println("npcInstance:" + npcInstance);
                    }
                }

                if (line.toLowerCase().contains("AI_Output".toLowerCase())) {
                    dialogues.add(convertAIOutput(line, npcName));
                }

                if (line.toLowerCase().contains("mission")) {
                    dialogues.add(Arrays.asList("", "Wpis do dziennika", convertMissionEntry(line)));
                }


                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println("Wszystkich niezgodnosci: " + inconsistenciesCounter);
        System.out.println();
        return dialogues;
    }

    public String getNpcInstance(String line) {
        String npcInstance = "";
        int startDialogueNameIndex = 0;
        int endDialogueNameIndex = 0;
        String findStr1Instance = "= \t";
        String findStr2Instance = ";";

        while (startDialogueNameIndex != -1) {
            startDialogueNameIndex = line.indexOf(findStr1Instance, startDialogueNameIndex);
            endDialogueNameIndex = line.indexOf(findStr2Instance, endDialogueNameIndex);

            if (startDialogueNameIndex != -1) {
                startDialogueNameIndex += findStr1Instance.length();
                endDialogueNameIndex += findStr2Instance.length();
                npcInstance = line.substring(startDialogueNameIndex, endDialogueNameIndex - findStr2Instance.length());
            }
        }
        return npcInstance;
    }
    public List<String> convertAIOutput(String line, String npcName) {
        List<String> dialogue = new ArrayList<>();
        int startAI_OutputIndex = 0;
        int startDialogueIndex = 0;
        int findStrDialogueNameStartIndex = 0;
        int findStrDialogueNameEndIndex;
        String findStrAI_Output = "AI_Output";
        String findStrDialogueStart = "); //";
        String findStrDialogueNameStart = "\"";
        String findStrDialogueNameEnd = "\")";
        String speaker;
        String text;
        String dialogueName;
        String npcDialogueName;

        while (startAI_OutputIndex != -1) {
            startAI_OutputIndex = line.indexOf(findStrAI_Output, startAI_OutputIndex);
            startDialogueIndex = line.indexOf(findStrDialogueStart, startDialogueIndex);
            findStrDialogueNameStartIndex = line.indexOf(findStrDialogueNameStart, findStrDialogueNameStartIndex);
            findStrDialogueNameEndIndex = line.indexOf(findStrDialogueNameEnd, findStrDialogueNameStartIndex);

            if (startAI_OutputIndex != -1) {
                startAI_OutputIndex += findStrAI_Output.length()+1;
                startDialogueIndex += findStrDialogueStart.length();
                findStrDialogueNameStartIndex += findStrDialogueNameStart.length()+1;
                findStrDialogueNameEndIndex += findStrDialogueNameEnd.length();
                speaker = line.substring(startAI_OutputIndex + 1, startAI_OutputIndex + 6);
                dialogueName = line.substring(findStrDialogueNameStartIndex-1, findStrDialogueNameEndIndex-2);
                npcDialogueName = dialogueName.substring(4, dialogueName.substring(4).indexOf("_") + 4);
                if (!npcName.equals(npcDialogueName)) {
                    inconsistenciesCounter++;
                    System.out.println("----------------------------------");
                    System.out.println("Niezgodnosc nazwy npc z dialogue instance z nazwa npc w AI_Output:");
                    System.out.println(dialogueName);
                }

                if (speaker.equals("other")) {
                    speaker = "Morris".toUpperCase();
                } else {
                    speaker = npcName.toUpperCase();
                }
                text = line.substring(startDialogueIndex);
                dialogue.add(dialogueName);
                dialogue.add(speaker);
                dialogue.add(text);
            }
        }
        return dialogue;
    }

    public static void main(String[] args) throws IOException {
        String path = "E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\Dialoge\\DIA_MainQuest_HelpGorn.d";
        ExtractDialoguesFromScript dialoguesFromScript = new ExtractDialoguesFromScript();
        dialoguesFromScript.extractDialoguesFromScript(path);
    }
}
