package com.sokoban.sokobanWorld;

public class Floor extends Brick{
    public boolean isAlive;
    public Floor (int x, int y, boolean isAlive){
        super(x,y);
        isAlive=true;
    }

    public Floor (int x, int y){
        this(x, y, true);
    }

    public Floor(){
        super();
    }

    public Floor(Floor unref) {
        super(unref);
    }

    void setPosition(int x,int y){
        this.x = x;
        this.y = y;
    }
}
