package com.sokoban.sokobanWorld;

public class Brick{
    public int x,y;
    public Brick (int x, int y){
        this.x = x;
        this.y = y;
    }
	 Brick(){
	 }
	 Brick(Brick unref){
		 x = unref.x;
		 y = unref.y;
	 }
	 boolean compare(Brick brick){
		 if(x==brick.x && y == brick.y)
			 return true;
		 return false;
	 }
	 Brick projec(int x, int y){
		 return new Brick(this.x +x, this.y +y);
	 }
	  void print(){
		 System.out.println(x + ", "+ y);
	 }

	void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
