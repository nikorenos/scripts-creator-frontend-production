package com.creativelabs.scriptscreator.scriptshandle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CreateNpcAmbient {

    public int findMaxNpcId(String folderPath) throws IOException {
        return Files.list(Paths.get(folderPath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .map(File::getName)
                .filter(name -> !name.equals("PC_Hero.d"))
                .map(name -> name.replaceAll("[^\\d.]", ""))
                .map(name -> name.replaceAll("[.]", ""))
                .map(Integer::parseInt)
                .mapToInt(Integer::intValue).max().getAsInt();
    }

    public int findFirstMissingNpcId(String folderPath) throws IOException {
        List<Integer> ids = Files.list(Paths.get(folderPath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .map(File::getName)
                .filter(name -> !name.equals("PC_Hero.d"))
                .map(name -> name.replaceAll("[^\\d.]", ""))
                .map(name -> name.replaceAll("[.]", ""))
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i) != i + 1) {
                return i + 1;
            }
        }
        return ids.size() + 1;
    }

    public List<String> listNpc(String folderPath) throws IOException {
        return Files.list(Paths.get(folderPath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .map(File::getName)
                .filter(name -> !name.equals("PC_Hero.d"))
                .map(name -> name.replaceAll("[^\\d.]", ""))
                .map(name -> name.replaceAll("[.]", ""))
                .collect(Collectors.toList());
    }

    public static ArrayList<String> startupEntriesList(File obj) {

        Scanner myReader = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            myReader = new Scanner(obj);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            list.add(data);
        }
        myReader.close();

        return list;
    }

    public void createNPC(int amount, int npcId) {
        //int npcId = findFirstMissingNpcId(path);

        for (int n = 1; n <= amount; n++) {

            String name = "Name_Novize";
            String npcGuild = "ZOMBIE";
            String npcName = "NOV_" + npcId + "_Ambient";
            String waypoint = "LEVEL_03_NOVICE_000";
            int SetAttributesToChapter = 5;
            int voice = 1 + n;
            String fight_tactic = "STRONG"; // MASTER / STRONG / COWARD
            String weapon = "ItMW_Hasis_Stab01"; //ItMw_2h_Sld_Axe iron_mastersword ItMw_1h_Vlk_Axe itmw_1h_bau_axe
            String armor = "Hasis_Nov_01"; //ITAR_BDT_H ITAR_BDT_M ItAr_Leather_L itar_prisoner
            String Mdl_ApplyOverlayMds = "Mage"; // Tired / Militia / Mage / Arrogance / Relaxed
            int FightSkills = 60;
            String routine = "TA_Stand_WP"; //TA_Smalltalk TA_Practice_Sword TA_Sit_Bench TA_Stand_Eating

            String npcScript = "\n" +
                    "instance " + npcName + " (Npc_Default)\n" +
                    "{\n" +
                    "\t// ------ NSC ------\n" +
                    "\tname \t\t= " + name + ";\n" +
                    "\tguild \t\t= GIL_" + npcGuild + ";\n" +
                    "\tid \t\t\t= " + npcId + ";\n" +
                    "\tvoice \t\t= " + voice + ";\n" +
                    "\tflags      \t= 0;\n" + //NPC_FLAG_IMMORTAL
                    "\tnpctype\t\t= NPCTYPE_MAIN;\n" +
                    "\t\n" +
                    "\t// ------ Aivars ------\n" +
                    "\t//aivar[AIV_NewsOverride] = TRUE;\n" +
                    "\t\n" +
                    "\t// ------ Attribute ------\n" +
                    "\tB_SetAttributesToChapter (self, " + SetAttributesToChapter + ");\n" +
                    "\t\n" +
                    "\t// ------ Kampf-Taktik ------\n" +
                    "\tfight_tactic = FAI_HUMAN_" + fight_tactic + ";\t\n" +
                    "\t\n" +
                    "\t// ------ Equippte Waffen ------\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                    "\tEquipItem (self, " + weapon + ");\n" +
                    "\t\n" +
                    "\t// ------ Inventory ------\n" +
                    "\tB_CreateAmbientInv (self); \n" +
                    "\t\n" +
                    "\t// ------ visuals ------\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                    "\tB_SetNpcVisual \t\t(self, MALE, \"Hum_Head_Bald\", Face_N_NormalBart_Senyan, BodyTex_N, " + armor + ");\t\n" +
                    "\tMdl_SetModelFatness\t(self, 1.0);\n" +
                    "\tMdl_ApplyOverlayMds\t(self, \"Humans_" + Mdl_ApplyOverlayMds + ".mds\"); \n" +
                    "\n" +
                    "\t// ------ NSC-relevante Talente vergeben ------\n" +
                    "\tB_GiveNpcTalents (self);\n" +
                    "\t\n" +
                    "\t// ------ Kampf-Talente ------\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                    "\tB_SetFightSkills (self, " + FightSkills + "); \n" +
                    "\n" +
                    "\t// ------ TA anmelden ------\n" +
                    "\tdaily_routine \t= Rtn_Start_" + npcId + ";\n" +
                    "};\n" +
                    "\n" +
                    "FUNC VOID Rtn_Start_" + npcId + " ()\n" +
                    "{\n" +
                    "  \t" + routine + "     (08,00,12,00,\"" + waypoint.toUpperCase() + "\");\n" +
                    "    " + routine + "     (12,00,08,00,\"" + waypoint.toUpperCase() + "\");\t\t\n" +
                    "};";

            String startupEntry = "Wld_InsertNpc \t\t(" + npcName + ", \"" + waypoint.toUpperCase() + "\");";
            System.out.println(startupEntry);

            try {
                //create npc
                String npcPath = "E:\\Gothic II\\_work\\data\\Scripts\\Content\\Story\\NPC\\" + npcName + ".d";
                FileWriter myWriter = new FileWriter(npcPath);
                myWriter.write(npcScript);
                myWriter.close();


                //create npc entry in startup
                /*String startupEntryPath = "src/main/resources/files/startupEntries.txt";
                File startupEntryFile = new File(startupEntryPath);

                ArrayList<String> startupEntriesList = startupEntriesList(startupEntryFile);
                startupEntriesList.add(startupEntryPath);
                FileWriter myWriterStartup = new FileWriter(startupEntryPath);

                for (String entry : startupEntriesList) {
                    //myWriterStartup.write(entry + "\n");
                    System.out.println(entry);
                }
                myWriterStartup.close();*/

                //System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            npcId = npcId + 1;
            //System.out.println(npcId);
        }

    }


    public static void main(String[] args) throws IOException {

        CreateNpcAmbient createNpcAmbient = new CreateNpcAmbient();
        String gothicFolder = "E:/Gothic II";
        String npcFolderPath = gothicFolder + "/_Work/data/Scripts/Content/Story/NPC";
        int firstFree = createNpcAmbient.findFirstMissingNpcId(npcFolderPath);
        int maxNpcId = createNpcAmbient.findMaxNpcId(npcFolderPath);
        System.out.println("Pierwszy wolny numer dla npc: " + (firstFree + 1));
        System.out.println("Pierwszy wolny numer dla npc: " + (maxNpcId + 1));

        //createNpcAmbient.createNPC(9, npcFolderPath);
        //createNpcAmbient.createNPC(7,maxNpcId+1);

    }
}

