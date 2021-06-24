package com.creativelabs.scriptscreator.scriptshandle;

import java.io.*;

public class DialogueToScriptByLine {

    public String[] convertDialogueStart(String line) {
        String[] dialogueParts = null;
        String dialogueName;
        int startDialogueIndex = 0;
        String findStrInstance = "Dialogue: ";

        while (startDialogueIndex != -1) {
            startDialogueIndex = line.indexOf(findStrInstance, startDialogueIndex);

            if (startDialogueIndex != -1) {
                startDialogueIndex += findStrInstance.length();
                dialogueName = line.substring(startDialogueIndex);
                dialogueParts = dialogueName.split("-");
            }
        }
        return dialogueParts;
    }

    public String[] convertEntry(String line) {
        String[] entryParts = null;
        int questNameIndex = 0;
        String findStr1 = "Quest: ";
        String entry;

        while (questNameIndex != -1) {
            questNameIndex = line.indexOf(findStr1, questNameIndex);

            if (questNameIndex != -1) {
                questNameIndex += findStr1.length();
                entry = line.substring(questNameIndex);
                entryParts = entry.split(" Entry: ");
            }
        }
        return entryParts;
    }

    public String[] convertItem(String line) {
        String[] itemParts = null;
        int itemNameIndex = 0;
        String findStr1 = "Item: ";
        String entry;

        while (itemNameIndex != -1) {
            itemNameIndex = line.indexOf(findStr1, itemNameIndex);

            if (itemNameIndex != -1) {
                itemNameIndex += findStr1.length();
                entry = line.substring(itemNameIndex);
                itemParts = entry.split(",");
            }
        }
        return itemParts;
    }

    public void writeScript(String dialoguePath, String scriptPath) {
        File file = new File(dialoguePath);
        int instanceCounter = 0;
        int dialogueCounter = 0;
        int choiceCounter = 0;
        int sectionCounter = 0;
        String npcName = "";
        String dialogueName = "";
        String previousNpcName = "";
        String previousDialogueName = "";
        String questName;
        String questEntry;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            try {

                FileWriter writeDialogue = new FileWriter(scriptPath);

                while (line != null) {
                    if (line.startsWith("Dialogue: ")) {
                        instanceCounter+=1;
                        dialogueCounter = 0;
                        choiceCounter = 0;
                        sectionCounter = 0;
                        String[] dialogueParts = convertDialogueStart(line);
                        npcName = dialogueParts[0];
                        dialogueName = dialogueParts[1];

                        if (instanceCounter > 1) {
                            writeDialogue.write("};");
                            writeDialogue.write("\n\n");
                        } else {
                            previousNpcName = npcName;
                            previousDialogueName = dialogueName;
                        }
                        writeDialogue.write("///////////////////////////////////////////////////////////////////////" + "\n");
                        writeDialogue.write("////////////////  " + npcName + " " + dialogueName  + "\n");
                        writeDialogue.write("///////////////////////////////////////////////////////////////////////" + "\n");
                        writeDialogue.write("INSTANCE DIA_" + npcName + "_" + dialogueName + " (C_INFO)" + "\n");
                        writeDialogue.write("{" + "\n");
                        writeDialogue.write("\tnpc\t\t\t = \t" + npcName+ ";" + "\n");
                        writeDialogue.write("\tnr\t\t\t = \t2;" + "\n");
                        writeDialogue.write("\tcondition\t = \tDIA_" + npcName + "_" + dialogueName + "_Condition;" + "\n");
                        writeDialogue.write("\tinformation\t = \tDIA_" + npcName + "_" + dialogueName + "_Info;" + "\n");
                        writeDialogue.write("\timportant\t = \tFALSE;" + "\n");
                        writeDialogue.write("\tpermanent\t = \tFALSE;" + "\n");
                        writeDialogue.write("\tdescription\t = \t\"(to do)\";" + "\n");
                        writeDialogue.write("};" + "\n");
                        writeDialogue.write("func int DIA_" + npcName + "_" + dialogueName + "_Condition ()" + "\n");
                        writeDialogue.write("{" + "\n");
                        if (instanceCounter > 1) {
                            writeDialogue.write("\tif (Npc_KnowsInfo (other, Dia_" + previousNpcName + "_" + previousDialogueName + "))" + "\n");
                            writeDialogue.write("\t\t{ return TRUE; };\t" + "\n");
                        } else {
                            writeDialogue.write("\treturn TRUE; \t" + "\n");
                        }
                        writeDialogue.write("};" + "\n");
                        writeDialogue.write("func void DIA_" + npcName + "_" + dialogueName + "_Info ()" + "\n");
                        writeDialogue.write("{" + "\n");
                        previousNpcName = npcName;
                        previousDialogueName = dialogueName;
                    }
                    if (line.startsWith("H: ")) {
                        dialogueCounter+=1;
                        String dialogueLine = line.substring(3);
                        if (dialogueCounter < 10) {
                            writeDialogue.write("\tAI_Output (other, self, \"DIA_" + npcName + "_" + dialogueName + "_15_0" + dialogueCounter + "\"); //" + dialogueLine + "\n");
                        } else{
                            writeDialogue.write("\tAI_Output (other, self, \"DIA_" + npcName + "_" + dialogueName + "_15_" + dialogueCounter + "\"); //" + dialogueLine + "\n");
                        }
                    }

                    if (line.startsWith("N: ")) {
                        dialogueCounter+=1;
                        String dialogueLine = line.substring(3);
                        if (dialogueCounter < 10) {
                            writeDialogue.write("\tAI_Output (self, other, \"DIA_" + npcName + "_" + dialogueName + "_12_0" + dialogueCounter + "\"); //" + dialogueLine + "\n");
                        } else {
                            writeDialogue.write("\tAI_Output (self, other, \"DIA_" + npcName + "_" + dialogueName + "_12_" + dialogueCounter + "\"); //" + dialogueLine + "\n");
                        }
                    }

                    if (line.startsWith("//")) {
                        writeDialogue.write("\t" + line);
                        writeDialogue.write("\n");
                    }

                    if (line.startsWith("GivenItem")) {
                        String[] itemParts = convertItem(line);
                        writeDialogue.write("\tGive_And_Remove(" + itemParts[0] + "," + itemParts[1] + ");");
                        writeDialogue.write("\n");
                    }

                    if (line.startsWith("ReceivedItem")) {
                        String[] itemParts = convertItem(line);
                        writeDialogue.write("\tCreate_And_Give(" + itemParts[0] + "," + itemParts[1] + ");");
                        writeDialogue.write("\n");
                    }

                    if (line.startsWith("BeginQuest")) {
                        String[] entryParts = convertEntry(line);
                        questName = entryParts[0];
                        questEntry = entryParts[1];
                        writeDialogue.write("\n");
                        writeDialogue.write("\tSTART_MISSION(" + questName + ", \"" + questEntry + "\");");
                        writeDialogue.write("\n");
                    }

                    if (line.startsWith("Quest")) {
                        String[] entryParts = convertEntry(line);
                        questName = entryParts[0];
                        questEntry = entryParts[1];
                        writeDialogue.write("\n");
                        writeDialogue.write("\tENTRY_MISSION(" + questName + ", \"" + questEntry + "\");");
                        writeDialogue.write("\n");
                    }
                    if (line.startsWith("FinishQuest")) {
                        String[] entryParts = convertEntry(line);
                        questName = entryParts[0];
                        questEntry = entryParts[1];
                        writeDialogue.write("\n");
                        writeDialogue.write("\tCLOSE_MISSION(" + questName + ", \"" + questEntry + "\");");
                        writeDialogue.write("\n");
                    }
                    if (line.startsWith("EXP")) {
                        String exp = line.substring(4);
                        writeDialogue.write("\tB_GivePlayerXP (" + exp + ");");
                        writeDialogue.write("\n");
                    }

                    if (line.startsWith("C")) {
                        choiceCounter+=1;
                        System.out.println("choice: " + choiceCounter);
                        String choiceLine = line.substring(4);
                        if (choiceCounter <2) {
                            writeDialogue.write("\n\tInfo_ClearChoices(Dia_" + npcName + "_" + dialogueName + ");" + "\n");
                        }
                        writeDialogue.write("\tInfo_AddChoice(Dia_" + npcName + "_" + dialogueName + ",\"" + choiceLine + "\",Dia_" + npcName + "_" + dialogueName + "_" + choiceCounter + ");"+ "\n");
                    }

                    if (line.startsWith("S")) {
                        sectionCounter+=1;
                        writeDialogue.write("\n");
                        writeDialogue.write("};");
                        writeDialogue.write("\n");
                        writeDialogue.write("FUNC VOID DIA_" + npcName + "_" + dialogueName + "_" + sectionCounter + " ()" + "\n");
                        writeDialogue.write("{");
                        writeDialogue.write("\tInfo_ClearChoices(Dia_" + npcName + "_" + dialogueName + ");" + "\n");
                    }

                    line = reader.readLine();
                }
                writeDialogue.write("};");
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

    public static void main(String[] args) {
        DialogueToScriptByLine dialogue = new DialogueToScriptByLine();
        String dialoguePath = "C:/input.d";
        String scriptPath = "C:/dialogue.d";
        dialogue.writeScript(dialoguePath, scriptPath);
    }
}

