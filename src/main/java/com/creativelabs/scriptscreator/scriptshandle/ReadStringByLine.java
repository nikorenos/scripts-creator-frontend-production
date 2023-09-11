package com.creativelabs.scriptscreator.scriptshandle;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Scanner;

public class ReadStringByLine {

    private static String calculateChoiceNumber(String line) {
        String secondChoiceDigit = String.valueOf(line.charAt(2));
        if (!NumberUtils.isParsable(secondChoiceDigit)) secondChoiceDigit = "";
        return line.charAt(1) + secondChoiceDigit;
    }

    public String convertDialogueIntoScript(String dialogue, String questCodeName) {
        DialogueToScriptByLine dialogueToScriptByLine = new DialogueToScriptByLine();
        StringBuilder script = new StringBuilder();
        questCodeName = "TOPIC_" + questCodeName;

        Scanner scanner = new Scanner(dialogue);

        int instanceCounter = 0;
        int dialogueCounter = 0;
        int choiceCounter = 0;
        String npcName = "";
        String dialogueName = "";
        String previousNpcName = "";
        String previousDialogueName = "";
        String questEntry;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.startsWith("Dialogue: ")) {
                instanceCounter+=1;
                dialogueCounter = 0;
                choiceCounter = 0;
                String[] dialogueParts = dialogueToScriptByLine.convertDialogueStart(line);
                npcName = dialogueParts[0];
                dialogueName = dialogueParts[1];

                if (instanceCounter > 1) {
                    script.append("};");
                    script.append("\n\n");
                } else {
                    previousNpcName = npcName;
                    previousDialogueName = dialogueName;
                }
                script.append("///////////////////////////////////////////////////////////////////////" + "\n");
                script.append("////////////////  " + npcName + " " + dialogueName  + "\n");
                script.append("///////////////////////////////////////////////////////////////////////" + "\n");
                script.append("INSTANCE DIA_" + npcName + "_" + dialogueName + " (C_INFO)" + "\n");
                script.append("{" + "\n");
                script.append("\tnpc\t\t\t = \t" + npcName+ ";" + "\n");
                script.append("\tnr\t\t\t = \t2;" + "\n");
                script.append("\tcondition\t = \tDIA_" + npcName + "_" + dialogueName + "_Condition;" + "\n");
                script.append("\tinformation\t = \tDIA_" + npcName + "_" + dialogueName + "_Info;" + "\n");
                script.append("\timportant\t = \tFALSE;" + "\n");
                script.append("\tpermanent\t = \tFALSE;" + "\n");
                script.append("\tdescription\t = \t\"(to do)\";" + "\n");
                script.append("};" + "\n");
                script.append("func int DIA_" + npcName + "_" + dialogueName + "_Condition ()" + "\n");
                script.append("{" + "\n");
                if (instanceCounter > 1) {
                    script.append("\tif (Npc_KnowsInfo (other, Dia_" + previousNpcName + "_" + previousDialogueName + "))" + "\n");
                    script.append("\t\t{ return TRUE; };\t" + "\n");
                } else {
                    script.append("\treturn TRUE; \t" + "\n");
                }
                script.append("};" + "\n");
                script.append("func void DIA_" + npcName + "_" + dialogueName + "_Info ()" + "\n");
                script.append("{" + "\n");
                previousNpcName = npcName;
                previousDialogueName = dialogueName;
            }

            if (line.startsWith("H: ")) {
                dialogueCounter+=1;
                String dialogueLine = line.substring(3);
                if (dialogueCounter < 10) {
                    script.append("\tAI_Output (other, self, \"DIA_").append(npcName).append("_").append(dialogueName).append("_15_0").append(dialogueCounter).append("\"); //").append(dialogueLine).append("\n");
                } else{
                    script.append("\tAI_Output (other, self, \"DIA_").append(npcName).append("_").append(dialogueName).append("_15_").append(dialogueCounter).append("\"); //").append(dialogueLine).append("\n");
                }
            }
            if (line.startsWith("N: ")) {
                dialogueCounter+=1;
                String dialogueLine = line.substring(3);
                if (dialogueCounter < 10) {
                    script.append("\tAI_Output (self, other, \"DIA_").append(npcName).append("_").append(dialogueName).append("_12_0").append(dialogueCounter).append("\"); //").append(dialogueLine).append("\n");
                } else {
                    script.append("\tAI_Output (self, other, \"DIA_").append(npcName).append("_").append(dialogueName).append("_12_").append(dialogueCounter).append("\"); //").append(dialogueLine).append("\n");
                }
            }

            if (line.startsWith("//")) {
                script.append("\t" + line);
                script.append("\n");
            }

            if (line.startsWith("GivenItem")) {
                String[] itemParts = dialogueToScriptByLine.convertItem(line);
                script.append("\tGive_And_Remove(" + itemParts[0] + "," + itemParts[1] + ");");
                script.append("\n");
            }

            if (line.startsWith("ReceivedItem")) {
                String[] itemParts = dialogueToScriptByLine.convertItem(line);
                script.append("\tCreate_And_Give(" + itemParts[0] + "," + itemParts[1] + ");");
                script.append("\n");
            }

            if (line.startsWith("BeginQuest")) {
                String[] entryParts = dialogueToScriptByLine.convertEntry(line);
                questEntry = entryParts[1];
                script.append("\n");
                script.append("\tSTART_MISSION(" + questCodeName + ", \"" + questEntry + "\");");
                script.append("\n");
            }

            if (line.startsWith("Quest")) {
                String[] entryParts = dialogueToScriptByLine.convertEntry(line);
                questEntry = entryParts[1];
                script.append("\n");
                script.append("\tENTRY_MISSION(" + questCodeName + ", \"" + questEntry + "\");");
                script.append("\n");
            }
            if (line.startsWith("FinishQuest")) {
                String[] entryParts = dialogueToScriptByLine.convertEntry(line);
                questEntry = entryParts[1];
                script.append("\n");
                script.append("\tCLOSE_MISSION(" + questCodeName + ", \"" + questEntry + "\");");
                script.append("\n");
            }
            if (line.startsWith("EXP")) {
                String exp = line.substring(4);
                script.append("\tB_GivePlayerXP (" + exp + ");");
                script.append("\n");
            }

            if (line.startsWith("C")) {
                choiceCounter+=1;
                String currentChoiceNumber = calculateChoiceNumber(line);
                System.out.println("choice: " + choiceCounter);
                String choiceLine = line.substring(4);
                if (choiceCounter <2) {
                    script.append("\n\tInfo_ClearChoices(Dia_" + npcName + "_" + dialogueName + ");" + "\n");
                }
                script.append("\tInfo_AddChoice(Dia_" + npcName + "_" + dialogueName + ",\"" + choiceLine + "\",Dia_" + npcName + "_" + dialogueName + "_" + currentChoiceNumber + ");"+ "\n");
            }

            if (line.startsWith("S")) {
                String currentChoiceNumber = calculateChoiceNumber(line);
                script.append("\n");
                script.append("};");
                script.append("\n");
                script.append("FUNC VOID DIA_" + npcName + "_" + dialogueName + "_" + currentChoiceNumber + " ()" + "\n");
                script.append("{");
                script.append("\tInfo_ClearChoices(Dia_" + npcName + "_" + dialogueName + ");" + "\n");
            }

        }
        script.append("};");
        return script.toString().replace("…", "...");
    }

    public static void main(String[] args) {
        String myString = "H: Oto zioła, o które prosiłeś.\n" +
                "N: Daj mi spojrzeć… rzeczywiście, masz wszystko doskonale. Ależ się mój mały szkrab ucieszy.\n" +
                "H: Kto taki?\n";
        String codename = "Test";
        ReadStringByLine readStringByLine = new ReadStringByLine();
        System.out.println(readStringByLine.convertDialogueIntoScript(myString, codename));
    }
}
