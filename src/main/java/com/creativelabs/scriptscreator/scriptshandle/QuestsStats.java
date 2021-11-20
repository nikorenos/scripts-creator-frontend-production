package com.creativelabs.scriptscreator.scriptshandle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class QuestsStats {

    public String orderNpcOccurrence(List<String> npcNamesList) {
        Map<String, Long> unsortedNpcOccurrenceMap =
                npcNamesList.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        LinkedHashMap<String, Integer> sortedNpcOccurrenceMap = new LinkedHashMap<>();

        //Use Comparator.reverseOrder() for reverse ordering
        unsortedNpcOccurrenceMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedNpcOccurrenceMap.put(x.getKey(), Math.toIntExact(x.getValue())));
        String npcOccurenceInfo = "";
        for (Map.Entry<String, Integer> entry : sortedNpcOccurrenceMap.entrySet()) {
            npcOccurenceInfo = npcOccurenceInfo + entry.getKey() + "(" + entry.getValue() + "), ";
        }
        return npcOccurenceInfo + "\n\n";
    }


    public int convertExp(String line) {
        int startString1 = 0;
        int startString2 = 0;
        String findString = "(";
        String findString2 = ")";
        String number = "";

        while (startString1 != -1) {
            startString1 = line.indexOf(findString, startString1);
            startString2 = line.indexOf(findString2, startString2);

            if (startString1 != -1) {
                startString1 += findString.length();
                number = line.substring(startString1, startString2);
            }
        }
        return Integer.parseInt(number);
    }

    public List<String> filterQuestsFiles(String folderPath) throws IOException {
        return Files.list(Paths.get(folderPath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .map(File::getName)
                //.filter(name -> name.contains("DIA_MainQuest_LostLumberjack") || name.contains("DIA_MainQuest_Kick"))//Quest
                .filter(name -> name.contains("Quest"))
                .map(name -> folderPath + "/" + name)
                .collect(Collectors.toList());
    }

    public String readQuestsFiles(List<String> filteredFilesPaths) {
        File file;
        String npcName;
        String info = "";
        int dialogueLineCounter = 0;
        int heroDialogueLineCounter = 0;
        int npcDialogueLineCounter = 0;
        int allDialogueLineCounter = 0;
        int XPCounter = 0;
        List<String> npcNamesList = new ArrayList<>();
        List<String> allNpcNamesList = new ArrayList<>();
        //ilosc lini dialogowych, ilosc xp, ilosc denarow, otrzymane itemy, czy jest walka z npc, ilosc wpisow do dziennika, ilosc wyborow

        BufferedReader reader;
        for (String fileName : filteredFilesPaths) {
            try {
                file = new File(fileName);
                reader = new BufferedReader(new FileReader(file));
                info = info +
                        "Zadanie: " + fileName + "\n";
                String line = reader.readLine();
                while (line != null) {
                    line = reader.readLine();

                    if (line != null) {
                        if (line.startsWith("\tnpc")) {
                            npcName = line.substring(11, line.length()-1);
                            npcNamesList.add(npcName);
                            allNpcNamesList.add(npcName);
                            //System.out.println(line.substring(11, line.length()-1));

                        }
                        if (line.contains("AI_Output")) {
                            dialogueLineCounter++;
                        }
                        if (line.contains("AI_Output (other, self,")) {
                            heroDialogueLineCounter++;
                        }
                        if (line.contains("AI_Output (self, other,")) {
                            npcDialogueLineCounter++;
                        }
                        if (line.contains("B_GivePlayerXP")) {
                            XPCounter = convertExp(line);
                        }
                    }
                }

                info = info + "Ilosc lini dialogowych: " + dialogueLineCounter + "\n" +
                        "Ilosc dialogow z Npc (lacznie: " + npcNamesList.size() + "): \n" +
                        orderNpcOccurrence(npcNamesList);

                allDialogueLineCounter = allDialogueLineCounter + dialogueLineCounter;
                dialogueLineCounter = 0;
                npcNamesList.clear();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        info = info + "Ilosc wszystkich lini dialogowych: " + allDialogueLineCounter + " \n" +
                "Ilosc wszystkich lini dialogowych Morrisa: " + heroDialogueLineCounter + " \n" +
                "Ilosc wszystkich lini dialogowych npc: " + npcDialogueLineCounter + "\n" +
                "Ilosc wszystkich dialogow z npc: " + orderNpcOccurrence(allNpcNamesList);
        return info;
    }

    public static void main(String[] args) throws IOException {
        QuestsStats questsStats = new QuestsStats();
        String gothicFolder = "E:/Gothic 2";
        String questsFolderPath = gothicFolder + "/_Work/data/Scripts/Content/Story/Dialoge";
        List<String> filteredQuestsFiles = questsStats.filterQuestsFiles(questsFolderPath);
        System.out.println(questsStats.readQuestsFiles(filteredQuestsFiles));
    }
}



