package com.sokoban.utils;

import java.util.ArrayList;

public class LevelWorld {
    private String name;
    private ArrayList<Level> levels;
    private int progress;

    public LevelWorld(String name, int progress) {
        this.name = name;
        this.progress = progress;
        levels=new ArrayList<Level>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        this.levels = levels;
    }

    public int getProgress() {

        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
