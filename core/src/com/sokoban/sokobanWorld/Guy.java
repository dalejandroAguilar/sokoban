package com.sokoban.sokobanWorld;

public class Guy extends Box{
    public int orientation;
    public boolean push;
    public Guy(Guy ref){
        super(ref.x,ref.y);
        this.orientation = ref.orientation;
    }

    public Guy (int x, int y, int orientation, boolean push){
        super(x,y);
        this.orientation = orientation;
        this.push = push;
    }
    public Guy (int x, int y, int orientation){
    this(x,y,orientation,false);
    }
    Guy(int x, int y){
    this(x,y, Sokoban.DOWN);
    }
    public Guy(){
    super();
    }
    @Override
    void move(int direction){
        super.move(direction);
        orientation = direction;
    }
}
