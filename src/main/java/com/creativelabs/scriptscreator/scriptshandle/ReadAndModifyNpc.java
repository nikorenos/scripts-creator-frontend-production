package com.creativelabs.scriptscreator.scriptshandle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ReadAndModifyNpc {

    public List<String> filesNamesList(String folderPath) throws IOException {
        return Files.list(Paths.get(folderPath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());
    }

    public String extractNpcName(String filePath) {
        String reverseString = new StringBuilder(filePath).reverse().toString();
        int foundCharacter = reverseString.indexOf("_");
        reverseString = reverseString.substring(2,foundCharacter);
        reverseString = new StringBuilder(reverseString).reverse().toString();
        return reverseString;
    }
    public String extractNpcFileName(String filePath) {
            int lastSeparatorIndex = filePath.lastIndexOf("\\");
            int extensionIndex = filePath.lastIndexOf(".d");

            if (lastSeparatorIndex != -1 && extensionIndex != -1) {
                return filePath.substring(lastSeparatorIndex + 1, extensionIndex);
            }
            return null;
        }

    public void modifyNpc(List<String> filesNamesList, String destinationFolder, int npcId) {
        ReadAndModifyNpc readAllFiles = new ReadAndModifyNpc();
        String npcName;
        String startupEntry;
        boolean isNPCAmbient = false;
        for(String filePath : filesNamesList) {
            if (filePath.contains("_L") || filePath.contains("_M")) {
                npcName = "Ambient";
                isNPCAmbient = true;
            } else {
                npcName = readAllFiles.extractNpcName(filePath);
            }
            String ambientNpcName = "BANDIT_" + npcId + "_" + npcName + "_MainCamp";
            String writePath = destinationFolder + "/" + "BANDIT_" + npcId + "_" + npcName + "_MainCamp.d";
            //System.out.println(writePath);
            if (isNPCAmbient) {
                startupEntry = "Wld_InsertNpc \t\t(" + ambientNpcName + ", \"" + npcName.toUpperCase() + "\");";
            } else {
                startupEntry = "Wld_InsertNpc \t\t(" + npcName + ", \"" + npcName.toUpperCase() + "\");";
            }
            System.out.println(startupEntry);
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(filePath));
                String line = reader.readLine();

                try {

                    FileWriter writeDialogue = new FileWriter(writePath);

                    while (line != null) {
                        if (line.startsWith("instance") && isNPCAmbient)  {
                            writeDialogue.write("instance " + ambientNpcName + " (Npc_Default)\n");
                        } else if (line.startsWith("\tid"))  {
                            writeDialogue.write("\tid \t\t\t= " + npcId + ";\n");
                        } else if (line.startsWith("\tdaily_routine"))  {
                            writeDialogue.write("\tdaily_routine \t= Rtn_Start_" + npcId + ";\n");
                        } else if (line.startsWith("FUNC VOID Rtn_Start_"))  {
                            writeDialogue.write("FUNC VOID Rtn_Start_" + npcId + " ()\n");
                        } else {
                            writeDialogue.write(line + "\n");
                        }
                        line = reader.readLine();
                    }

                    reader.close();
                    writeDialogue.close();
                    //System.out.println("Dialogue successfully wrote to the file.");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                System.out.println("An error with dialogue occurred.");
                e.printStackTrace();
            }
            npcId +=1;
            isNPCAmbient = false;
        }
    }



    public static void main(String[] args) throws IOException {
        //Creating a File object for directory
        String directoryPath = "E:/Temp";
        String writePath = "E:/Temp/Temp2";
        int npcId = 1;

        ReadAndModifyNpc readAllFiles = new ReadAndModifyNpc();
        List<String> filesNames = readAllFiles.filesNamesList(directoryPath);
        readAllFiles.modifyNpc(filesNames, writePath, npcId);


    }
}