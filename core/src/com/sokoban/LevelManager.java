package com.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class LevelManager {
    private int indexLevel;
    private int progressLevel;
    private String[][] levels;
    public String[] nameLevels;
    public LevelManager() {
        levels = getAllFiles();
        progressLevel = 0;
        indexLevel = 0;
        loadProgress();
    }


    public void setIndexLevel(int indexLevel) {
        this.indexLevel = indexLevel;
    }

    public int getSize() {
        return levels.length;
    }

    public String[][] getAllFiles() {
        FileHandle file = Gdx.files.internal("levels/");
        FileHandle[] listFiles = file.list();
        String string[][] = new String[listFiles.length][];
        nameLevels = new String[listFiles.length];
        for (int i = 0; i < listFiles.length; i++) {
            FileHandle fileTemp = listFiles[i];
            String[] levelString = fileTemp.readString().split("\n");
            string[i] = levelString;
            nameLevels[i] = listFiles[i].nameWithoutExtension();
        }
        return string;
    }

    public void loadProgress() {
        FileHandle file = Gdx.files.local("data.txt");
        if (file.exists()) {
            String[] splitReadString = file.readString().split("\n");
            indexLevel = Integer.parseInt(splitReadString[0]);
            progressLevel = Integer.parseInt(splitReadString[1]);
        } else {
            file.writeString("0" + "\n" + "0", false);
            //file.writeString("0", true);
            indexLevel = 0;
            progressLevel = 0;
        }
    }

    private void saveProgress() {
        FileHandle file = Gdx.files.local("data.txt");
        if (file.exists()) {
            file.writeString("" + indexLevel + "\n" + progressLevel, false);
            //file.writeString("" + progressLevel, true);
        } else {
            file.writeString("" + indexLevel + "\n" + progressLevel, false);
            //file.writeString("" + progressLevel, true);
        }
    }

    public void resetProgress() {
        FileHandle file = Gdx.files.local("data.txt");
        file.writeString(0 + "\n" + 0, false);
        indexLevel = 0;
        progressLevel = 0;
    }

    public String[] getCurrentLevel() {
        return levels[indexLevel];
    }

    public boolean nextLevel() {
        if (++indexLevel > levels.length - 1) {
            indexLevel = 0;
            saveProgress();
            return false;
        }
        if (indexLevel > progressLevel)
            ++progressLevel;
        saveProgress();
        return true;
    }

    public int getIndex() {
        return indexLevel;
    }

    public int getProgressLevel() {
        return progressLevel;
    }
}