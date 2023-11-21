package com.creativelabs.scriptscreator.scriptshandle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExitDialogue {

    public String readNpcFile(String line) {
        return line.substring(line.indexOf(" ") + 1, line.indexOf(" ("));
    }

    public List<String> filterNpcFiles(String folderPath) throws IOException {
        return Files.list(Paths.get(folderPath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .map(File::getName)
                .filter(name -> !name.equals("PC_Hero.d"))
                .map(name -> folderPath + "/" + name)
                .collect(Collectors.toList());
    }

    public List<String> filterNpcNames(List<String> filteredFilesPaths) {
        File file;
        String npcName;
        List<String> npcNames = new ArrayList<>();
        int counter = 0;

        BufferedReader reader;
        for (String fileName : filteredFilesPaths) {
            try {
                file = new File(fileName);
                reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();

                while (line != null) {
                    if (line.toLowerCase().contains("instance")) {
                        npcName = readNpcFile(line);
                        npcNames.add(npcName);
                        counter++;
                    }
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        System.out.println("Amount of npc: " + counter);
        System.out.println();
        return npcNames.stream().sorted().collect(Collectors.toList());
    }

    public String prepareExitFile(List<String> npcNames) {
        String exitDialogueText = "";

        for (String npcName : npcNames) {
            exitDialogueText = exitDialogueText +
                    "// ************************************************************\n" +
                    "INSTANCE DIA_" + npcName + "_EXIT(C_INFO)\n" +
                    "{\n" +
                    "\tnpc\t\t\t= " + npcName + ";\n" +
                    "\tnr\t\t\t= 999;\n" +
                    "\tcondition\t= DIA_" + npcName + "_EXIT_Condition;\n" +
                    "\tinformation\t= DIA_" + npcName + "_EXIT_Info;\n" +
                    "\tpermanent\t= TRUE;\n" +
                    "\tdescription = DIALOG_ENDE;\n" +
                    "};                       \n" +
                    "FUNC INT DIA_" + npcName + "_EXIT_Condition()\n" +
                    "{\n" +
                    "\treturn TRUE;\n" +
                    "};\n" +
                    "FUNC VOID DIA_" + npcName + "_EXIT_Info()\n" +
                    "{\t\n" +
                    "\tAI_StopProcessInfos\t(self);\n" +
                    "};\n\n";

        }
        return exitDialogueText;
    }

    public void saveExitDialogues(String gothicFolder) throws IOException {
        ExitDialogue exitDialogue = new ExitDialogue();
        String npcFolderPath = gothicFolder + "/_Work/data/Scripts/Content/Story/NPC";
        String exitScriptPath = "/_Work/data/Scripts/Content/Story/Dialoge/DIA_Exit.d";

        List<String> filteredNpcFiles = exitDialogue.filterNpcFiles(npcFolderPath);
        List<String> npcNames = exitDialogue.filterNpcNames(filteredNpcFiles);
        String exitDialogueText = exitDialogue.prepareExitFile(npcNames);
        try {
            FileWriter writeDialogue = new FileWriter(gothicFolder + exitScriptPath);
            writeDialogue.write(exitDialogueText);
            writeDialogue.close();
            System.out.println("Dialogue successfully wrote to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        ExitDialogue exitDialogue = new ExitDialogue();
        String gothicFolder = "E:/Gothic II";
        exitDialogue.saveExitDialogues(gothicFolder);
    }
}



