package com.creativelabs.scriptscreator.scriptshandle;


import java.io.*;

public class ScriptToDialogue {

    public String[] convertInstance(String line) {
        String[] dialogueparts = null;
        String dialogueName;
        int startDialogueNameIndex = 0;
        int endDialogueNameIndex = 0;
        String findStrInstance = "instance ";
        String findStrC_INFO = " (C_INFO)";

        while (startDialogueNameIndex != -1) {
            startDialogueNameIndex = line.toLowerCase().indexOf(findStrInstance, startDialogueNameIndex);
            endDialogueNameIndex = line.toLowerCase().indexOf(findStrC_INFO.toLowerCase(), endDialogueNameIndex);

            if (startDialogueNameIndex != -1) {
                startDialogueNameIndex += findStrInstance.length();
                endDialogueNameIndex += findStrC_INFO.length();
                dialogueName = line.substring(startDialogueNameIndex + 4, endDialogueNameIndex - findStrC_INFO.length());
                dialogueparts = dialogueName.split("_");
            }
        }
        return dialogueparts;
    }

    public String convertChoices(String line) {
        int startString1 = 0;
        int startString2 = 0;
        int startString3 = 0;
        String findString = ",\"";
        String findString2 = "\",";
        String findString3 = ");";
        String option = "";
        String optionNumber = "";

        while (startString1 != -1) {
            startString1 = line.indexOf(findString, startString1);
            startString2 = line.indexOf(findString2, startString2);
            startString3 = line.indexOf(findString3, startString3);

            if (startString1 != -1) {
                startString1 += findString.length();
                startString2 += findString2.length();
                option = line.substring(startString1, startString2 - 2);
                optionNumber = line.substring(startString3 - 1, startString3);
            }
        }
        return "C" + optionNumber + ": " + option;
    }

    public String convertMissionEntry(String line, String findString) {
        int startMissionIndex = 0;
        int startEntryIndex = 0;
        int findStr2Index = 0;
        String findStrEntry = ", \"";
        String findStr2 = "\");";
        String questName = "";
        String entry = "";
        String beginning = "[color=orange][b]BeginQuest:[/b] ";

        while (startMissionIndex != -1) {
            startMissionIndex = line.indexOf(findString, startMissionIndex);
            startEntryIndex = line.indexOf(findStrEntry, startEntryIndex);
            findStr2Index = line.indexOf(findStr2, findStr2Index);

            if (startMissionIndex != -1) {
                startMissionIndex += findString.length();
                startEntryIndex += findStrEntry.length();
                questName = line.substring(startMissionIndex + 7, startEntryIndex - 3);
                entry = line.substring(startEntryIndex, findStr2Index);
            }
        }
        if (findString.equals("ENTRY_MISSION")) {
            beginning = "[color=orange][b]Quest:[/b] ";
        }
        if (findString.equals("CLOSE_MISSION")) {
            beginning = "[color=orange][b]FinishQuest:[/b] ";
        }
        return beginning + questName + " [b]Entry:[/b] " + entry + "[/color]";
    }

    public String convertAIOutput(String line) {
        int startAI_OutputIndex = 0;
        int startDialogueIndex = 0;
        String findStrAI_Output = "AI_Output";
        String findStrDialogueStart = "); //";
        String speaker = "";
        String text = "";

        while (startAI_OutputIndex != -1) {
            startAI_OutputIndex = line.indexOf(findStrAI_Output, startAI_OutputIndex);
            startDialogueIndex = line.indexOf(findStrDialogueStart, startDialogueIndex);
            System.out.println("startAI_OutputIndex: " + startAI_OutputIndex);
            System.out.println("startDialogueIndex: " + startDialogueIndex);

            if (startAI_OutputIndex != -1) {
                startAI_OutputIndex += findStrAI_Output.length()+1;
                startDialogueIndex += findStrDialogueStart.length();
                speaker = line.substring(startAI_OutputIndex + 1, startAI_OutputIndex + 6);
                System.out.println(speaker);
                if (speaker.equals("other")) {
                    speaker = "[color=green]H: ";
                    text = line.substring(startDialogueIndex) + "[/color]";
                } else {
                    speaker = "N: ";
                    text = line.substring(startDialogueIndex);
                }
            }
        }
        return speaker + text;
    }

    public String convertExp(String line) {
        int startString1 = 0;
        int startString2 = 0;
        String findString = "(";
        String findString2 = ")";
        String number = "";

        while (startString1 != -1) {
            startString1 = line.indexOf(findString, startString1);
            startString2 = line.indexOf(findString2, startString2);

            if (startString1 != -1) {
                startString1 += findString.length();
                number = line.substring(startString1, startString2);
            }
        }
        return "EXP+" + number;
    }

    public String convertGiveItem(String line) {
        int startString1 = 0;
        int startString2 = 0;
        String findString1 = "self,";
        String findString2 = ")";
        String item = "";

        while (startString1 != -1) {
            startString1 = line.indexOf(findString1, startString1);
            startString2 = line.indexOf(findString2, startString2);

            if (startString1 != -1) {
                startString1 += findString1.length();
                item = line.substring(startString1, startString2);
            }
        }
        return "[color=purple]GivenItem: " + item + "[/color]";
    }

    public String convertReceivedItem(String line) {
        int startString1 = 0;
        int startString2 = 0;
        String findString1 = "(";
        String findString2 = ")";
        String item = "";

        while (startString1 != -1) {
            startString1 = line.indexOf(findString1, startString1);
            startString2 = line.indexOf(findString2, startString2);

            if (startString1 != -1) {
                startString1 += findString1.length();
                item = line.substring(startString1, startString2);
            }
        }
        return "[color=purple]ReceivedItem: " + item + "[/color]";
    }


    public static void main(String[] args) {

        String path = "E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\Dialoge\\DIA_MainQuest_Friends.d";
        String startMission = "START_MISSION";
        String entry = "ENTRY_MISSION";
        String closeMission = "CLOSE_MISSION";
        File file = new File(path);
        ScriptToDialogue scriptToDialogue = new ScriptToDialogue();
        String dialoguePath = "E:/dialogue.d";

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            try {

                FileWriter writeDialogue = new FileWriter(dialoguePath);

            while (line != null) {
                System.out.println(line);
                if (line.contains("INSTANCE")) {
                    String[] dialogueParts = scriptToDialogue.convertInstance(line);
                    writeDialogue.write("\n");
                    writeDialogue.write("[b]Dialogue: " + dialogueParts[0] + "-" + dialogueParts[1] + "[/b]");
                    writeDialogue.write("\n");
                    writeDialogue.write("\n");
                    System.out.println(line);
                }
                if (line.toLowerCase().contains("AI_Output".toLowerCase())) {
                    String dialogueLine = scriptToDialogue.convertAIOutput(line);
                    System.out.println("dialogueLine: " + dialogueLine);
                    writeDialogue.write(dialogueLine + "\n");
                }

                if (line.contains(startMission)) {
                    String startMissionLine = scriptToDialogue.convertMissionEntry(line, startMission);
                    writeDialogue.write("\n" + startMissionLine + "\n");
                }


                if (line.contains(entry)) {
                    System.out.println("entry");
                    String entryLine = scriptToDialogue.convertMissionEntry(line, entry);
                    writeDialogue.write("\n" + entryLine + "\n");
                }

                if (line.contains(closeMission)) {
                    System.out.println("closeMission");
                    String entryLine = scriptToDialogue.convertMissionEntry(line, closeMission);
                    writeDialogue.write("\n" + entryLine + "\n");
                }

                if (line.contains("Info_AddChoice")) {
                    String entryChoice = scriptToDialogue.convertChoices(line);
                    writeDialogue.write("\n" + entryChoice);
                }

                for (int i = 1; i < 11; i++) {
                    if (line.contains("_" + i +" ")) {
                        String entrySection = "S" + i + ":";
                        writeDialogue.write("\n" + entrySection + "\n");
                    }
                }

                if (line.contains("B_GivePlayerXP")) {
                    String entryChoice = scriptToDialogue.convertExp(line);
                    writeDialogue.write("\n" + entryChoice);
                }
                if (line.contains("B_GiveInvItems")) {
                    String item = scriptToDialogue.convertGiveItem(line);
                    writeDialogue.write(item + "\n");
                }
                if (line.contains("Create_And_Give")) {
                    String item = scriptToDialogue.convertReceivedItem(line);
                    writeDialogue.write(item + "\n");
                }
                if ((line.startsWith("//") || line.startsWith("\t//")) && !line.contains("////")) {
                    if (line.startsWith("\t//")) {
                        writeDialogue.write(line.substring(1) + "\n");
                    } else {
                        writeDialogue.write(line + "\n");
                    }
                }

                line = reader.readLine();
            }
            reader.close();
            writeDialogue.close();
            System.out.println("Dialogue successfully wrote to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        } catch (IOException e) {
            System.out.println("An error with dialogue occurred.");
            e.printStackTrace();
        }
    }
}

