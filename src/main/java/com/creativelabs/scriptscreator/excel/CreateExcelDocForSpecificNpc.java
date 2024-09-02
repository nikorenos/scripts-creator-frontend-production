package com.creativelabs.scriptscreator.excel;

import com.creativelabs.scriptscreator.scriptshandle.ExitDialogue;
import com.creativelabs.scriptscreator.scriptshandle.ExtractDialoguesFromScript;
import com.creativelabs.scriptscreator.scriptshandle.FileOperations;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateExcelDocForSpecificNpc {


    public static void main(String[] args) throws IOException {
        String scriptPath = "E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\Dialoge\\DIA_Xardas.d";
        int counter = 0;
        //String scriptPath = "E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\Dialoge\\DIA_City.d";
        CreateExcelDoc createExcelDoc = new CreateExcelDoc();
        ExtractDialoguesFromScript extractDialoguesFromScript = new ExtractDialoguesFromScript();
        String specificNpcName = "Satir";

        ExitDialogue exitDialogue = new ExitDialogue();
        FileOperations fileOperations = new FileOperations();
        Set<String> npcNames = exitDialogue.filterNpcNamesFromDialogues(getDialogueFilesPathes(fileOperations));
        Map<String, List<List<String>>> npcWithDialogues = new HashMap<>(Collections.emptyMap());

        for (String npcName : npcNames) {
            if (Objects.equals(npcName, specificNpcName)) {
                //System.out.println(npcName);
                List<List<String>> dialogues = extractDialoguesFromAllScriptsForSpecificNpc(extractDialoguesFromScript, npcName);
                if (dialogues.size() > 1) {
                    counter++;
                    npcWithDialogues.put(npcName, dialogues);
                }
            }
        }

        createExcelDoc.createExcelDocWithTabForEachNpc(npcWithDialogues);
        FileOperations.openFile("E:\\dev\\scripts-creator-frontend-production\\temp.xlsx");
        System.out.println("Ilosc postaci z dialogami: " + counter + ", wszystkich linii dialogowych: " + extractDialoguesFromScript.getDialogueLineCounter());
        System.out.println("Ilosc linii dialogowych glowengo bohatera: " + extractDialoguesFromScript.getHeroDialogueLineCounter());
    }

    private static List<List<String>> extractDialoguesFromAllScriptsForSpecificNpc(ExtractDialoguesFromScript extractDialoguesFromScript, String npcName) throws IOException {
        List<List<String>> listArrayList = new ArrayList<>();
        FileOperations fileOperations = new FileOperations();
        Set<String> dialogueFilesPath = getDialogueFilesPathes(fileOperations);
        for (String dialoguePath : dialogueFilesPath) {
            //System.out.println(dialoguePath);
            listArrayList = Stream.concat(listArrayList.stream(), extractDialoguesFromScript.extractDialoguesFromScriptForSpecificNpc(dialoguePath, npcName).stream())
                    .collect(Collectors.toList());

        }
        return listArrayList;
    }

    private static Set<String> getDialogueFilesPathes(FileOperations fileOperations) {
        return fileOperations.getAbsolutePathForFiles("E:\\Gothic II Old\\_work\\Data\\Scripts\\Content\\Story\\Dialoge\\", "DIA");
    }

}
