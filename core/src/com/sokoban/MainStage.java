package com.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sokoban.entities.FloorEntity;
import com.sokoban.sokobanWorld.Brick;
import com.sokoban.sokobanWorld.World;

import java.util.ArrayList;

public class MainStage extends Stage implements GestureDetector.GestureListener  {
    static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3, NULL=-1, THREESHOLD_VELOCITY=50;
    private GestureDetector gestureDetector;
    public MainStage(){
        super();
    }
    private ArrayList<FloorEntity> floorEntityList;
    private World world;
    public MainStage(FitViewport fv, World world){
        super(fv);
        ArrayList<FloorEntity> floorEntityList= new ArrayList<FloorEntity>();
        this.world=world;



        //gestureDetector=new GestureDetector(this);
        //Gdx.input.setInputProcessor(gestureDetector);

    }



    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        System.out.println("as");
        if (Math.abs(velocityX)>Math.abs(velocityY))
            if(velocityX>0)
                world.move(RIGHT);
            else
                world.move(LEFT);
        if (Math.abs(velocityX)<Math.abs(velocityY))
            if(velocityY>0)
                world.move(UP);
            else
                world.move(DOWN);
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
}
