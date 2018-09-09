package com.sokoban;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class Input implements GestureDetector.GestureListener {


    static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3, NULL=-1;
    public Vector2 delta;
    private Vector2 delta0;
    public boolean isReady;
    public Input(){
        delta0=new Vector2(0,0);
        delta=new Vector2(0,0);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        System.out.println(velocityX);
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    //  @Override
  //  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
  //      delta0.set(screenX,screenY);
  //      delta.set(0,0);
  //      return true;
//
  //  }

  //  @Override
  //  protected void finalize() throws Throwable {
  //      super.finalize();
  //  }
//
  //  @Override
  //  public boolean touchDragged(int screenX, int screenY, int pointer) {
  //      System.out.println(screenX+", "+screenY);
  //      delta.set(screenX-delta0.x,screenY-delta0.y);
  //      System.out.println(direction());
  //      return false;
  //  }
//
  //  @Override
  //  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
  //      System.out.println("down");
  //      delta.set(0,0);
  //      return true;
  //  }

    public int direction(){
        if(!delta.epsilonEquals(0,0)){
            if(Math.abs(delta.x)>Math.abs(delta.y)){
                if(delta.x>0)
                    return RIGHT;
                else
                    return LEFT;
            }
            else{
                if(delta.y>0)
                    return UP;
                else
                    return DOWN;
            }
        }
        return NULL;
    }
}