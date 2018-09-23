package com.sokoban.sokobanWorld;

import java.util.ArrayList;

public class Reel {
    public ArrayList<Brick> groupBrick = new ArrayList<Brick>();
    public ArrayList<Box> groupBox = new ArrayList<Box>();
    public ArrayList<Receptacle> groupReceptacle = new ArrayList<Receptacle>();
    public Guy guy;
    public Reel (Guy guy, ArrayList<Box> groupBox, ArrayList<Receptacle> groupReceptacle, ArrayList<Brick> groupBrick ){
        for(int i=0; i < groupBrick.size(); i++ )
            this.groupBrick.add(new Brick(groupBrick.get(i).x,groupBrick.get(i).y));
        for(int i=0; i < groupBox.size(); i++ )
            this.groupBox.add(new Box(groupBox.get(i).x,groupBox.get(i).y,groupBox.get(i).isAlive, groupBox.get(i).isSlippy ));
        for(int i=0; i < groupReceptacle.size(); i++ )
            this.groupReceptacle.add(new Receptacle(groupReceptacle.get(i).x,groupReceptacle.get(i).y));
        this.guy = new Guy(guy.x,guy.y,guy.orientation);
    }
}
