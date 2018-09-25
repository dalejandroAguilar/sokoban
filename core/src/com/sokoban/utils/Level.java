package com.sokoban.utils;

public class Level {
    private String name;
    private int score;
    private String[] map;

    public Level(String name, int score, String[] map) {
        this.name = name;
        this.score = score;
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String[] getMap() {
        return map;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setMap(String[] map) {
        this.map = map;
    }
}
