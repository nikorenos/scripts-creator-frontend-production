package com.creativelabs.scriptscreator.scriptshandle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ExitDialogue {

    public String readNpcFile(String line) {
        String npcName = "";
        int npcNameIndex = 0;
        String findStrInstance = "instance ";
        while (npcNameIndex != -1) {
            npcNameIndex = line.indexOf(findStrInstance, npcNameIndex);
            if (npcNameIndex != -1) {
                npcNameIndex += findStrInstance.length();
                npcName = line.substring(npcNameIndex, line.length()-14);
            }
        }
        return npcName;
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

    public String prepareExitFile(List<String> filteredFilesPaths) {
        File file;
        String npcName;
        String exitDialogueText = "";

        BufferedReader reader;
        for (String fileName : filteredFilesPaths) {
            try {
                file = new File(fileName);
                reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                while (line != null) {
                    line = reader.readLine();
                    if ((line != null) && (line.startsWith("instance") ||  line.startsWith("INSTANCE"))) {
                        npcName = readNpcFile(line);
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
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return exitDialogueText;
    }
    public void saveExitDialogues(String gothicFolder) throws IOException {
        ExitDialogue exitDialogue = new ExitDialogue();
        String npcFolderPath = gothicFolder + "/_Work/data/Scripts/Content/Story/NPC";
        String exitScriptPath = "/_Work/data/Scripts/Content/Story/Dialoge/DIA_Exit.d";

        List<String> filteredNpcFiles = exitDialogue.filterNpcFiles(npcFolderPath);
        String exitDialogueText = exitDialogue.prepareExitFile(filteredNpcFiles);
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



