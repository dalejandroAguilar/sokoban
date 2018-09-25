package com.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.sokoban.sokobanWorld.World;
import com.sokoban.utils.Level;
import com.sokoban.utils.LevelWorld;

import java.util.ArrayList;

public class LevelManager {
    private int indexLevel;
    private int progressLevel;
    private LevelWorld[] levelWorlds;
    private int indexWorld;


    public LevelManager() {
        // levels = getAllFiles();
        progressLevel = 0;
        indexLevel = 0;
        indexWorld = 0;
        loadLevelsAssets();
        loadProgress();
    }


    public LevelWorld[] getLevelWorlds() {
        return levelWorlds;
    }

    public void loadLevelsAssets() {
        FileHandle directory = Gdx.files.internal("levels/");
        FileHandle[] listWorldFiles = directory.list();
        levelWorlds = new LevelWorld[listWorldFiles.length];
        for (int i = 0; i < listWorldFiles.length; i++) {
            levelWorlds[i] = new LevelWorld(listWorldFiles[i].nameWithoutExtension(), 0);
            System.out.println(listWorldFiles[i].readString());
            String[] strlevelsDescription = listWorldFiles[i].readString().split("\n\n");
            LevelWorld levelWorldI = levelWorlds[i];
            for (int j = 0; j < strlevelsDescription.length; j++) {
                String[] strLevel_description = strlevelsDescription[j].split("\n; ");
                String strLevel = strLevel_description[1];
                String strDescription = strLevel_description[0];
                levelWorlds[i].getLevels().add(new Level(strLevel, 0, strDescription.split("\n")));
            }
        }
    }

    public void loadProgress() {
        FileHandle file = Gdx.files.local("data.txt");
        if (file.exists()) {
            String[] strWorlds = file.readString().split("\n");
            for (int i = 0; i < strWorlds.length; i++) {
                String[] strWorldName_progressI = strWorlds[i].split("<world/progress>");
                String strWorldNameI = strWorldName_progressI[0];
                String strWorldProgressI = strWorldName_progressI[1];
                for (LevelWorld levelWorld : levelWorlds) {
                    if (levelWorld.getName().equals(strWorldNameI))
                        levelWorld.setProgress(Integer.parseInt(strWorldProgressI));
                }
            }
        } else {
            for (LevelWorld levelWorld : levelWorlds)
                levelWorld.setProgress(0);
        }
    }


    //public int getSize() {
    //    return levels.length;
    //}

    //public String[][] getAllFiles() {
    //    FileHandle file = Gdx.files.internal("levels/");
    //    FileHandle[] listFiles = file.list();
    //    String string[][] = new String[listFiles.length][];
    //    nameLevels = new String[listFiles.length];
    //    for (int i = 0; i < listFiles.length; i++) {
    //        FileHandle fileTemp = listFiles[i];
    //        String[] levelString = fileTemp.readString().split("\n");
    //        string[i] = levelString;
    //        nameLevels[i] = listFiles[i].nameWithoutExtension();
    //    }
    //    return string;
    //}

    private void saveProgress() {
        FileHandle file = Gdx.files.local("data.txt");
        for (LevelWorld levelWorld: levelWorlds)
            file.writeString(levelWorld.getName()+"<world/progress>"+levelWorld.getProgress()+"\n", true);
    }

    public void resetProgress() {
        FileHandle file = Gdx.files.local("data.txt");
        file.writeString(0 + "\n" + 0, false);
        indexLevel = 0;
        progressLevel = 0;
    }

    public Level getCurrentLevel() {
        return levelWorlds[indexWorld].getLevels().get(indexLevel);
    }

    //public boolean nextLevel() {
    //    indexLevel++;
    //    return true;
    //}

    public int getIndex() {
        return indexLevel;
    }


    public boolean nextLevel() {
        if (++indexLevel > levelWorlds[indexWorld].getLevels().size() - 1) {
            indexLevel = 0;
            saveProgress();
            return false;
        }

        if (indexLevel > levelWorlds[indexWorld].getProgress())
            levelWorlds[indexWorld].setProgress(levelWorlds[indexWorld].getProgress()+1);
        saveProgress();
        return true;
    }

    public int getProgressLevel() {
        return progressLevel;
    }

    public int getIndexLevel() {
        return indexLevel;
    }

    public void setIndexLevel(int indexLevel) {
        this.indexLevel = indexLevel;
    }

    public int getIndexWorld() {
        return indexWorld;
    }

    public void setIndexWorld(int indexWorld) {
        this.indexWorld = indexWorld;
    }

    public LevelWorld getCurrentLevelWorld(){
        return levelWorlds[indexWorld];
    }

    public int getCurrentWorldProgress(){
        return levelWorlds[indexWorld].getProgress();
    }

}