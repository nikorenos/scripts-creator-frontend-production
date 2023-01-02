package com.creativelabs.scriptscreator.scriptshandle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExtractDialoguesFromScript {

    public static List<String> convertAIOutput(String line) {
        List<String> dialogue = new ArrayList<>();
        int startAI_OutputIndex = 0;
        int startDialogueIndex = 0;
        int findStrDialogueNameStartIndex = 0;
        int findStrDialogueNameEndIndex = 0;
        String findStrAI_Output = "AI_Output";
        String findStrDialogueStart = "); //";
        String findStrDialogueNameStart = "\"";
        String findStrDialogueNameEnd = "\")";
        String speaker = "";
        String text = "";
        String dialogueName = "";

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

                if (speaker.equals("other")) {
                    speaker = "MORRIS";
                } else {
                    speaker = "";
                }
                text = line.substring(startDialogueIndex);
                System.out.println(dialogueName);
                System.out.println(speaker);
                System.out.println(text);
            }
        }
        return dialogue;
    }

    public static void main(String[] args) {
        String path = "E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\Dialoge\\DIA_MainQuest_HelpGorn.d";
        File file = new File(path);
        ExtractDialoguesFromScript dialoguesFromScript = new ExtractDialoguesFromScript();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            try {

                while (line != null) {
                    //System.out.println(line);

                    if (line.toLowerCase().contains("AI_Output".toLowerCase())) {
                        ExtractDialoguesFromScript.convertAIOutput(line);
                        //System.out.println("dialogueLine: " + dialogueLine);
                    }

//                    if (line.contains(startMission)) {
//                        String startMissionLine = scriptToDialogue.convertMissionEntry(line, startMission);
//                        writeDialogue.write("\n" + startMissionLine + "\n");
//                    }
//
//
//                    if (line.contains(entry)) {
//                        System.out.println("entry");
//                        String entryLine = scriptToDialogue.convertMissionEntry(line, entry);
//                        writeDialogue.write("\n" + entryLine + "\n");
//                    }
//
//                    if (line.contains(closeMission)) {
//                        System.out.println("closeMission");
//                        String entryLine = scriptToDialogue.convertMissionEntry(line, closeMission);
//                        writeDialogue.write("\n" + entryLine + "\n");
//                    }

                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("An error with dialogue occurred.");
            e.printStackTrace();
        }
    }
}
