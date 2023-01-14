package com.creativelabs.scriptscreator.scriptshandle;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ExtractDialoguesFromScript {
    int inconsistenciesCounter = 0;

    public String convertChoiceEntry(String line) {
        int startIndex = 0;
        int endIndex = 0;
        String findStr1 = "\"";
        String findStr2 = "\",";

        startIndex = line.indexOf(findStr1, startIndex);
        endIndex = line.indexOf(findStr2, endIndex);
        return line.substring(startIndex + findStr1.length(), endIndex);
    }
    public String convertMissionEntry(String line) {
        int startIndex = 0;
        int endIndex = 0;
        String findStr1 = ", \"";
        String findStr2 = "\");";

        startIndex = line.indexOf(findStr1, startIndex);
        endIndex = line.indexOf(findStr2, endIndex);
        return line.substring(startIndex + 3, endIndex);
    }

    public String convertDescriptionEntry(String line) {
        int startIndex = 0;
        int endIndex = 0;
        String findStr1 = "\"";
        String findStr2 = "\";";
        startIndex = line.indexOf(findStr1, startIndex);
        endIndex = line.indexOf(findStr2, endIndex);
        return line.substring(startIndex + 1, endIndex);
    }

    public String getNpcInstance(String line) {
        return (line.substring(line.indexOf("="))).replaceAll("\\P{L}+", "");
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
                startAI_OutputIndex += findStrAI_Output.length() + 1;
                startDialogueIndex += findStrDialogueStart.length();
                findStrDialogueNameStartIndex += findStrDialogueNameStart.length() + 1;
                findStrDialogueNameEndIndex += findStrDialogueNameEnd.length();
                speaker = line.substring(startAI_OutputIndex + 1, startAI_OutputIndex + 6);
                dialogueName = line.substring(findStrDialogueNameStartIndex - 1, findStrDialogueNameEndIndex - 2);
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

                if (line.toLowerCase().contains("description")) {
                    dialogues.add(Arrays.asList("", "Description", convertDescriptionEntry(line)));
                }

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

                if (line.toLowerCase().contains("Info_AddChoice".toLowerCase()) && line.contains("\"")) {
                    dialogues.add(Arrays.asList("", "Choice", convertChoiceEntry(line)));
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
    public static void main(String[] args) throws IOException {
        String path = "E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\Dialoge\\DIA_SideQuest_City_Aphrodisiac.d";
        ExtractDialoguesFromScript dialoguesFromScript = new ExtractDialoguesFromScript();
        List<List<String>> list = dialoguesFromScript.extractDialoguesFromScript(path);
        System.out.println(list);
    }
}
