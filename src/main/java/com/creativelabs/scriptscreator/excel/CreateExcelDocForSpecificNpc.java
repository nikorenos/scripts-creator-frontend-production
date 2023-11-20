package com.creativelabs.scriptscreator.excel;

import com.creativelabs.scriptscreator.scriptshandle.ExitDialogue;
import com.creativelabs.scriptscreator.scriptshandle.ExtractDialoguesFromScript;
import com.creativelabs.scriptscreator.scriptshandle.FileOperations;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class CreateExcelDocForSpecificNpc {


    public static void main(String[] args) throws IOException {
        String scriptPath = "E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\Dialoge\\DIA_SideQuest_Romance_OlsaRevenge.d";
        //String scriptPath = "E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\Dialoge\\DIA_City.d";
        CreateExcelDoc createExcelDoc = new CreateExcelDoc();
        ExtractDialoguesFromScript extractDialoguesFromScript = new ExtractDialoguesFromScript();


        ExitDialogue exitDialogue = new ExitDialogue();
        String npcFolderPath = "E:/Gothic II/_Work/data/Scripts/Content/Story/NPC";
        List<String> filteredNpcFiles = exitDialogue.filterNpcFiles(npcFolderPath);
        List<String> npcNames = exitDialogue.filterNpcNames(filteredNpcFiles);

        for (String npcName : npcNames) {
            extractDialoguesForSpecfificNpc(extractDialoguesFromScript, npcName);
        }

        createExcelDoc.createExcelDoc(extractDialoguesFromScript.getDialogues());
        FileOperations.openFile("E:\\dev\\scripts-creator-frontend-production\\temp.xlsx");
    }

    private static void extractDialoguesForSpecfificNpc(ExtractDialoguesFromScript extractDialoguesFromScript, String npcName) throws IOException {
        FileOperations fileOperations = new FileOperations();
        Set<String> dialogueFilesPath = fileOperations.getAbsolutePathForFiles("E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\Dialoge\\", "DIA");
        for (String dialoguePath : dialogueFilesPath) {
            //System.out.println(dialoguePath);
            extractDialoguesFromScript.extractDialoguesFromScriptForSpecificNpc(dialoguePath, npcName);

        }
    }

}
