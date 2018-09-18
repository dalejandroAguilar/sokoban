package com.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.util.ArrayList;

public  class LevelManager{

    public static String[][] getAllFiles() {
        FileHandle file=Gdx.files.internal("levels/");
        FileHandle[] listFiles= file.list();
        String string[][]= new String[listFiles.length][];
        for (int i=0; i<listFiles.length;i++){
            FileHandle fileTemp = listFiles[i];
            String[] levelString = fileTemp.readString().split("\n");
            System.out.println("Archivo no encontrado " + levelString.length);
            string[i] = levelString;
        }
       return  string;
    }

//
   // public static String[] fromFileToStringArray(FileHandle file) {
   //     file.readString()
   //     String[] level = new String[countLinesFile(file)];
   //     try {
   //         Scanner fileScanned = new Scanner(file);
//
   //         for (int i = 0; i < level.length; i++)
   //             level[i] = fileScanned.nextLine();
//
   //     } catch (Exception ex) {
   //         System.out.println("Archivo no encontrado " + ex);
   //     }
//
   //     return level;
   // }
}