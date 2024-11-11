package com.creativelabs.scriptscreator.svm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadSaySmallTalkFile {

    public static void main(String[] args) throws IOException {

        HashMap<Integer, List<String>> extractedIdsWithSmallTalks = readBSaySmalltalk("E:\\Gothic II Old\\_work\\Data\\Scripts\\Content\\AI\\Human\\B_Human\\B_Say_Smalltalk.d");
        System.out.println(extractedIdsWithSmallTalks);
        System.out.println(extractedIdsWithSmallTalks.size());
//        ReadAndModifyNpc readNpc = new ReadAndModifyNpc();
//        List<String> npcList = readNpc.filesNamesList("E:\\Gothic II Old\\_work\\Data\\Scripts\\Content\\Story\\NPC");
//
//
//        System.out.println("Extracted IDs: " + extractedIds);
//        List<String> filteredFiles = filterFilesById(npcList, extractedIds);
//
//        System.out.println("Filtered Files size: " + filteredFiles.size() + ", " + filteredFiles);


    }

    public static List<String> filterFilesById(List<String> files, Set<Integer> extractedIds) throws FileNotFoundException {
        List<String> filteredFiles = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+"); // Matches sequences of digits

        for (String file : files) {
            Matcher matcher = pattern.matcher(file);
            if (matcher.find()) {
                int id = Integer.parseInt(matcher.group());
                if (extractedIds.contains(id) && containsSmalltalkRoutine(file)) {
                    filteredFiles.add(file);
                }
            }
        }
        return filteredFiles;
    }

    public static Set<Integer> extractIds(String text) {
        Set<Integer> ids = new HashSet<>();
        if (text.contains("self.id")) {
            Pattern pattern = Pattern.compile("self\\.id == (\\d+)");
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                int id = Integer.parseInt(matcher.group(1));
                ids.add(id);
            }
        }
        return ids;
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
                    if (line.contains("Kapitel")) {
                        addTempListToMain(mainList, tempList);
                        tempList = new HashMap<>();
                    }

                    if (line.contains("self.id")) {
                        Pattern pattern = Pattern.compile("self\\.id == (\\d+)");
                        Matcher matcher = pattern.matcher(line);

                        while (matcher.find()) {
                            int id = Integer.parseInt(matcher.group(1));
                            tempList.put(id, new ArrayList<>());
                        }
                    }
                    if (line.contains("$GG_Smalltalk")) {
                        for (Map.Entry<Integer, List<String>> entry : tempList.entrySet()) {
                            List<String> updatedSmallTalks = entry.getValue();
                            updatedSmallTalks.add(extractGGSmalltalk(line));
                            entry.setValue(updatedSmallTalks);
                            //entry.getValue().addAll(extractGGSmalltalk(line));
                        }
                    }

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } catch(
    IOException e)

    {
        throw new RuntimeException(e);
    }
        return mainList;
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