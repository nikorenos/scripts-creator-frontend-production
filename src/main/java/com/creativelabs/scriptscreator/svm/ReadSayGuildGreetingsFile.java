package com.creativelabs.scriptscreator.svm;

import com.creativelabs.scriptscreator.excel.CreateExcelDoc;
import com.creativelabs.scriptscreator.scriptshandle.FileAndFolderOperations;
import com.creativelabs.scriptscreator.scriptshandle.ReadAndModifyNpc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadSayGuildGreetingsFile {

    public static void main(String[] args) throws IOException {

        String bSaySmallTalkPath = "E:\\Gothic II Old\\_work\\Data\\Scripts\\Content\\AI\\Human\\B_Human\\B_Say_GuildGreetings.d";
        String svmPath = "E:\\Gothic II Old\\_work\\Data\\Scripts\\Content\\Story\\SVM.d";

        HashMap<Integer, List<String>> extractedIdsWithSVM = readBSaySmalltalk(bSaySmallTalkPath); //read npc ids with small talks
        Map<String, List<String>> extractedSVMTexts = readSVM(svmPath); //read all small talks
        ReadAndModifyNpc readNpc = new ReadAndModifyNpc();
        List<String> npcList = readNpc.filesNamesList("E:\\Gothic II Old\\_work\\Data\\Scripts\\Content\\Story\\NPC"); //read all npc
        HashMap<String, List<List<String>>> filteredNpcWithSVM = filterNpcById(npcList, extractedIdsWithSVM, extractedSVMTexts); //filter npc with small talks

        CreateExcelDoc createExcelDoc = new CreateExcelDoc();
        createExcelDoc.createExcelDocWithTabForEachNpc(filteredNpcWithSVM);
        FileAndFolderOperations.openFile("E:\\dev\\scripts-creator-frontend-production\\temp.xlsx");

        System.out.println("Filtered npc: " + filteredNpcWithSVM.keySet().size());


    }

    public static HashMap<String, List<List<String>>> filterNpcById(List<String> files, HashMap<Integer, List<String>> filteredNpcWithSVM, Map<String, List<String>> extractedSVMTexts) {
        HashMap<String, List<List<String>>> filteredNpcNameWithSVM = new HashMap<>();
        Pattern pattern = Pattern.compile("\\d+"); // Matches sequences of digits
        ReadAndModifyNpc readAndModifyNpc = new ReadAndModifyNpc();

            for (String file : files) {
                for (Map.Entry<Integer, List<String>> entry : filteredNpcWithSVM.entrySet()) {
                Matcher matcher = pattern.matcher(file);
                if (matcher.find()) {
                    int id = Integer.parseInt(matcher.group());
                    if (id == entry.getKey() && !file.toLowerCase().contains("ambient") && containsSmalltalkRoutine(file)) {
                        filteredNpcNameWithSVM.put(readAndModifyNpc.extractNpcFileName(file), convertToNpcWithSVM(entry.getValue(), extractedSVMTexts));
                    }
                }
            }
        }


        return filteredNpcNameWithSVM;
    }

    public static List<List<String>> convertToNpcWithSVM(List<String> svmNames, Map<String, List<String>> extractedSVMTexts) {
        List<List<String>> svms = new ArrayList<>();

        for (String svmName : svmNames) {
            for (Map.Entry<String, List<String>> entry : extractedSVMTexts.entrySet()) {
                if (svmName.equals(entry.getKey()))
                    svms.add(entry.getValue());
            }
        }

        return svms;
    }

    public static boolean containsSmalltalkRoutine(String filePath) {
        boolean hasSmalltalk = false;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();

            try {
                while (line != null) {
                    if (line.toLowerCase().contains("TA_Smalltalk".toLowerCase())) hasSmalltalk = true;
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return hasSmalltalk;
    }

    public static HashMap<Integer, List<String>> readBSaySmalltalk(String filePath) {
        HashMap<Integer, List<String>> mainList = new HashMap<>();
        HashMap<Integer, List<String>> tempList = new HashMap<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();

            try {
                while (line != null) {
                    if (line.contains("//")) {
                        addTempListToMain(mainList, tempList);
                        tempList = new HashMap<>();
                    }

                    if (line.contains("slf.id")) {
                        Pattern pattern = Pattern.compile("slf\\.id == (\\d+)");
                        Matcher matcher = pattern.matcher(line);

                        while (matcher.find()) {
                            int id = Integer.parseInt(matcher.group(1));
                            tempList.put(id, new ArrayList<>());
                        }
                    }
                    if (line.contains("$GG_")) {
                        for (Map.Entry<Integer, List<String>> entry : tempList.entrySet()) {
                            List<String> updatedSmallTalks = entry.getValue();
                            updatedSmallTalks.add(extractGGSmalltalk(line));
                            entry.setValue(updatedSmallTalks);
                        }
                    }

                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return mainList;
    }

    public static Map<String, List<String>> readSVM(String filePath) {
        HashMap<String, List<String>> svmList = new HashMap<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();

            try {
                while (line != null) {
                    if (line.contains("GG_Smalltalk")) {
                        Pattern pattern = Pattern.compile("(GG_Smalltalk\\d+)\\s*=\\s*\"(SVM_7_Smalltalk\\d+)\"\\s*;//(.*)");

                        Matcher matcher = pattern.matcher(line);
                        while (matcher.find()) {
                            String key = matcher.group(1);                  // GG_Smalltalk01, GG_Smalltalk02, etc.
                            String value = matcher.group(2);                // SVM_7_Smalltalk01, SVM_7_Smalltalk02, etc.
                            String comment = matcher.group(3).trim();       // Text after //

                            // Add the value and comment to a list
                            List<String> valueList = Arrays.asList(value, "", comment);
                            svmList.put(key, valueList);
                        }
                    }

                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return svmList;
    }

    private static void addTempListToMain(HashMap<Integer, List<String>> mainList, HashMap<Integer, List<String>> tempList) {
        for (Map.Entry<Integer, List<String>> entry : tempList.entrySet()) {
            Integer key = entry.getKey();
            List<String> tempListValues = entry.getValue();

            // If mainList already has the key, append values from tempList
            mainList.computeIfAbsent(key, k -> new ArrayList<>()).addAll(tempListValues);
        }
    }

    public static String extractGGSmalltalk(String text) {
        String name = "";
        Pattern pattern = Pattern.compile("\\$GG_Smalltalk(\\d+)"); // Pattern to match "GG_Smalltalk" with a number
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            name = "GG_Smalltalk" + matcher.group(1);
        }
        return name;
    }

}