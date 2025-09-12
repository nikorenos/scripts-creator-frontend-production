package com.creativelabs.scriptscreator.buildmodpack;

import java.io.IOException;

import static com.creativelabs.scriptscreator.buildmodpack.CopyNewerFiles.copyNewerFiles;
import static com.creativelabs.scriptscreator.buildmodpack.GitUpdater.updateRepo;
import static com.creativelabs.scriptscreator.buildmodpack.GothicModBuilder.buildModPack;
import static com.creativelabs.scriptscreator.fileshandle.CopySpecificFile.copyFileIfNewer;

public class ModPackManager {
    public static void main(String[] args) throws IOException {
        updateRepo("E:\\RepoZW2\\.git");
        copyAnimsToModPack();
        copyMeshesToModPack();
        copyPresetsToModPack();
        copyScriptsToModPack();
        copyOUToModPack();
        copyTexturesToModPack();
        copyMainWorldToModPack();
        copyAutorun();
        copyTempleZen();
        copyGerezaZen();
        copyzBassMusic();
        buildModPack();
    }

    private static void copyAnimsToModPack() throws IOException {
        copyNewerFiles("E:\\RepoZW2\\_Work\\data\\Anims\\_compiled", "E:\\Paczka ZW2\\_WORK\\DATA\\ANIMS\\_COMPILED");
    }
    private static void copyMeshesToModPack() throws IOException {
        copyNewerFiles("E:\\RepoZW2\\_Work\\data\\Meshes\\_compiled", "E:\\Paczka ZW2\\_WORK\\DATA\\MESHES\\_COMPILED");
    }
    private static void copyPresetsToModPack() throws IOException {
        copyNewerFiles("E:\\RepoZW2\\_Work\\data\\Presets", "E:\\Paczka ZW2\\_WORK\\DATA\\Presets");
    }
    private static void copyScriptsToModPack() throws IOException {
        copyNewerFiles("E:\\Gothic II Old\\_work\\Data\\Scripts\\_compiled", "E:\\Paczka ZW2\\_WORK\\DATA\\SCRIPTS\\_COMPILED");
    }
    private static void copyOUToModPack() throws IOException {
        copyNewerFiles("E:\\Gothic II Old\\_work\\Data\\Scripts\\Content\\Cutscene", "E:\\Paczka ZW2\\_WORK\\DATA\\SCRIPTS\\CONTENT\\CUTSCENE");
    }
    private static void copyTexturesToModPack() throws IOException {
        copyNewerFiles("E:\\RepoZW2\\_Work\\data\\Textures\\_compiled", "E:\\Paczka ZW2\\_WORK\\DATA\\TEXTURES\\_COMPILED");
    }
    private static void copyMainWorldToModPack() throws IOException {
        copyNewerFiles("E:\\Gothic II Old\\_work\\Data\\Worlds\\MAIN", "E:\\Paczka ZW2\\_WORK\\DATA\\WORLDS\\MAIN");
    }
    private static void copyAutorun() throws IOException {
        copyNewerFiles("E:\\RepoZW2\\System\\Autorun", "E:\\SteamLibrary\\steamapps\\common\\Gothic II\\launcher\\goldengate2\\Content\\system\\Autorun");
    }
    private static void copyTempleZen() {
        copyFileIfNewer("E:\\RepoZW2\\_Work\\data\\Worlds", "E:\\Paczka ZW2\\_WORK\\DATA\\WORLDS", "SAMOA_07_TEMPLE.ZEN");
    }
    private static void copyGerezaZen() {
        copyFileIfNewer("E:\\RepoZW2\\_Work\\data\\Worlds", "E:\\Paczka ZW2\\_WORK\\DATA\\WORLDS", "SAMOA_08_GEREZA.ZEN");
    }
    private static void copyzBassMusic() {
        copyFileIfNewer("E:\\RepoZW2\\Data", "E:\\SteamLibrary\\steamapps\\common\\Gothic II\\launcher\\goldengate2\\Content\\Data", "zBassMusic.vdf");
    }
}
