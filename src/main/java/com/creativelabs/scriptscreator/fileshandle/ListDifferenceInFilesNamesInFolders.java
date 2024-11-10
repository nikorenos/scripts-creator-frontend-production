package com.creativelabs.scriptscreator.fileshandle;

import com.creativelabs.scriptscreator.excel.ReadExcelFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.creativelabs.scriptscreator.scriptshandle.ReplaceDialoguesWithDataFromExcel.getFirstElements;

public class ListDifferenceInFilesNamesInFolders {

    public static List<String> listFilesNamesInFolder(String folderPath) throws IOException {
        return Files.list(Paths.get(folderPath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .map(File::getName)
                .map(it -> it.substring(0, it.length()-4))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException {
        List<String> audioFilesNames = listFilesNamesInFolder("E:\\Dubbbing ZW2\\Dla Anonimowego\\Horn - Kristo\\Gotowe nagrania");
        String excelFilePath = "E:\\dev\\scripts-creator-frontend-production\\temp.xlsx";
        ReadExcelFile readExcelFile = new ReadExcelFile();
        List<List<String>> dialogues = readExcelFile.readExcelFile(excelFilePath, null);
        List<String> dialogueNames = getFirstElements(dialogues);
        for (String dialogueName: dialogueNames) {
            if (!audioFilesNames.contains(dialogueName)) System.out.println(dialogueName);
        }
    }
}
