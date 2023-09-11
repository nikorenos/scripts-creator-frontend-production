package com.creativelabs.scriptscreator.scriptshandle;

import org.apache.commons.lang3.math.NumberUtils;

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

    private static String calculateChoiceNumber(String line) {
        String secondChoiceDigit = String.valueOf(line.charAt(2));
        if (!NumberUtils.isParsable(secondChoiceDigit)) secondChoiceDigit = "";
        return line.charAt(1) + secondChoiceDigit;
    }

    public void writeScript(String dialoguePath, String gothicFolderPathPath, String questCodeName) {
        File file = new File(dialoguePath);
        int instanceCounter = 0;
        int dialogueCounter = 0;
        int choiceCounter = 0;
        String npcName = "";
        String dialogueName = "";
        String previousNpcName = "";
        String previousDialogueName = "";
        String questEntry;
        //gothicFolderPathPath = gothicFolderPathPath + "/_Work/data/Scripts/Content/Story/Dialoge/DIA_MainQuest_" + questCodeName + ".d";
        gothicFolderPathPath = "E:/DIA_MainQuest_" + questCodeName + ".d";
        questCodeName = "TOPIC_" + questCodeName;


        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            try {

                FileWriter writeScript = new FileWriter(gothicFolderPathPath);

                while (line != null) {
                    if (line.startsWith("Dialogue: ")) {
                        instanceCounter+=1;
                        dialogueCounter = 0;
                        choiceCounter = 0;
                        String[] dialogueParts = convertDialogueStart(line);
                        npcName = dialogueParts[0];
                        dialogueName = dialogueParts[1];

                        if (instanceCounter > 1) {
                            writeScript.write("};");
                            writeScript.write("\n\n");
                        } else {
                            previousNpcName = npcName;
                            previousDialogueName = dialogueName;
                        }
                        writeScript.write("///////////////////////////////////////////////////////////////////////" + "\n");
                        writeScript.write("////////////////  " + npcName + " " + dialogueName  + "\n");
                        writeScript.write("///////////////////////////////////////////////////////////////////////" + "\n");
                        writeScript.write("INSTANCE DIA_" + npcName + "_" + dialogueName + " (C_INFO)" + "\n");
                        writeScript.write("{" + "\n");
                        writeScript.write("\tnpc\t\t\t = \t" + npcName+ ";" + "\n");
                        writeScript.write("\tnr\t\t\t = \t2;" + "\n");
                        writeScript.write("\tcondition\t = \tDIA_" + npcName + "_" + dialogueName + "_Condition;" + "\n");
                        writeScript.write("\tinformation\t = \tDIA_" + npcName + "_" + dialogueName + "_Info;" + "\n");
                        writeScript.write("\timportant\t = \tFALSE;" + "\n");
                        writeScript.write("\tpermanent\t = \tFALSE;" + "\n");
                        writeScript.write("\tdescription\t = \t\"(to do)\";" + "\n");
                        writeScript.write("};" + "\n");
                        writeScript.write("func int DIA_" + npcName + "_" + dialogueName + "_Condition ()" + "\n");
                        writeScript.write("{" + "\n");
                        if (instanceCounter > 1) {
                            writeScript.write("\tif (Npc_KnowsInfo (other, Dia_" + previousNpcName + "_" + previousDialogueName + "))" + "\n");
                            writeScript.write("\t\t{ return TRUE; };\t" + "\n");
                        } else {
                            writeScript.write("\treturn TRUE; \t" + "\n");
                        }
                        writeScript.write("};" + "\n");
                        writeScript.write("func void DIA_" + npcName + "_" + dialogueName + "_Info ()" + "\n");
                        writeScript.write("{" + "\n");
                        previousNpcName = npcName;
                        previousDialogueName = dialogueName;
                    }
                    if (line.startsWith("H: ")) {
                        dialogueCounter+=1;
                        String dialogueLine = line.substring(3);
                        if (dialogueCounter < 10) {
                            writeScript.write("\tAI_Output (other, self, \"DIA_" + npcName + "_" + dialogueName + "_15_0" + dialogueCounter + "\"); //" + dialogueLine + "\n");
                        } else{
                            writeScript.write("\tAI_Output (other, self, \"DIA_" + npcName + "_" + dialogueName + "_15_" + dialogueCounter + "\"); //" + dialogueLine + "\n");
                        }
                    }

                    if (line.startsWith("N: ")) {
                        dialogueCounter+=1;
                        String dialogueLine = line.substring(3);
                        if (dialogueCounter < 10) {
                            writeScript.write("\tAI_Output (self, other, \"DIA_" + npcName + "_" + dialogueName + "_12_0" + dialogueCounter + "\"); //" + dialogueLine + "\n");
                        } else {
                            writeScript.write("\tAI_Output (self, other, \"DIA_" + npcName + "_" + dialogueName + "_12_" + dialogueCounter + "\"); //" + dialogueLine + "\n");
                        }
                    }

                    if (line.startsWith("//")) {
                        writeScript.write("\t" + line);
                        writeScript.write("\n");
                    }

                    if (line.startsWith("GivenItem")) {
                        String[] itemParts = convertItem(line);
                        writeScript.write("\tGive_And_Remove(" + itemParts[0] + "," + itemParts[1] + ");");
                        writeScript.write("\n");
                    }

                    if (line.startsWith("ReceivedItem")) {
                        String[] itemParts = convertItem(line);
                        writeScript.write("\tCreate_And_Give(" + itemParts[0] + "," + itemParts[1] + ");");
                        writeScript.write("\n");
                    }

                    if (line.startsWith("BeginQuest")) {
                        String[] entryParts = convertEntry(line);
                        questEntry = entryParts[1];
                        writeScript.write("\n");
                        writeScript.write("\tSTART_MISSION(" + questCodeName + ", \"" + questEntry + "\");");
                        writeScript.write("\n");
                    }

                    if (line.startsWith("Quest")) {
                        String[] entryParts = convertEntry(line);
                        questEntry = entryParts[1];
                        writeScript.write("\n");
                        writeScript.write("\tENTRY_MISSION(" + questCodeName + ", \"" + questEntry + "\");");
                        writeScript.write("\n");
                    }
                    if (line.startsWith("FinishQuest")) {
                        String[] entryParts = convertEntry(line);
                        questEntry = entryParts[1];
                        writeScript.write("\n");
                        writeScript.write("\tCLOSE_MISSION(" + questCodeName + ", \"" + questEntry + "\");");
                        writeScript.write("\n");
                    }
                    if (line.startsWith("EXP")) {
                        String exp = line.substring(4);
                        writeScript.write("\tB_GivePlayerXP (" + exp + ");");
                        writeScript.write("\n");
                    }

                    if (line.startsWith("C")) {
                        choiceCounter+=1;
                        String currentChoiceNumber = calculateChoiceNumber(line);
                        System.out.println("choice: " + currentChoiceNumber);
                        System.out.println(line);
                        String choiceLine = line.substring(4);
                        if (choiceCounter <2) {
                            writeScript.write("\n\tInfo_ClearChoices(Dia_" + npcName + "_" + dialogueName + ");" + "\n");
                        }
                        writeScript.write("\tInfo_AddChoice(Dia_" + npcName + "_" + dialogueName + ",\"" + choiceLine + "\",Dia_" + npcName + "_" + dialogueName + "_" + currentChoiceNumber + ");"+ "\n");
                    }

                    if (line.startsWith("S")) {
                        String currentChoiceNumber = calculateChoiceNumber(line);
                        writeScript.write("\n");
                        writeScript.write("};");
                        writeScript.write("\n");
                        writeScript.write("FUNC VOID DIA_" + npcName + "_" + dialogueName + "_" + currentChoiceNumber + " ()" + "\n");
                        writeScript.write("{");
                        writeScript.write("\tInfo_ClearChoices(Dia_" + npcName + "_" + dialogueName + ");" + "\n");
                    }

                    line = reader.readLine();
                }
                writeScript.write("};");
                reader.close();
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
        DialogueToScriptByLine dialogue = new DialogueToScriptByLine();
        String dialoguePath = "E:/dialogue.d";
        String gothicPath = "E:/Gothic II";
        String questCodeName = "HatiretWrath";
        dialogue.writeScript(dialoguePath, gothicPath, questCodeName);
        FileOperations.openFile("E:/DIA_MainQuest_" + questCodeName + ".d");
    }
}

