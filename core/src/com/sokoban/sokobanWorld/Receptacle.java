package com.sokoban.sokobanWorld;

public class Receptacle extends Brick {
    boolean status;
    public Receptacle(int x, int y) {
        super(x, y);
        status = false;
    }

	public Receptacle(Receptacle receptacle) {
		x=receptacle.x;
		y=receptacle.y;
		status=receptacle.status;
	}

}