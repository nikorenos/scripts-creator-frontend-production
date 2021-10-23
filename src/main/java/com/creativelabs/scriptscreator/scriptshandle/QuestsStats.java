package com.creativelabs.scriptscreator.scriptshandle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class QuestsStats {

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

    public List<String> filterQuestsFiles(String folderPath) throws IOException {
        return Files.list(Paths.get(folderPath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .map(File::getName)
                .filter(name -> name.contains("DIA_MainQuest_LostLumberjack"))//Quest
                .map(name -> folderPath + "/" + name)
                .collect(Collectors.toList());
    }

    public String readQuestsFiles(List<String> filteredFilesPaths) {
        File file;
        String npcName;
        String info = "";
        List<String> npcNames = new ArrayList<>();
        //ilosc lini dialogowych, ilosc xp, ilosc denarów, otrzymane itemy, czy jest walka z npc, ilosc wpisow do dziennika, ilość wyborów

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
                        if (line.contains(("npc\t\t\t = \t"))) {
                            npcName = line.substring(11,line.length()-1);
                            npcNames.add(npcName);
                            /*info = info +
                                    "Npc: " + npcName + "\n";*/

                        }
                    }
                }

                Map<String, Long> unsortedNpcOccurrenceMap =
                        npcNames.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
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
                info = info + "Ilość dialogów z Npc (łącznie: " + npcNames.size() + "): \n" +
                        npcOccurenceInfo;
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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



