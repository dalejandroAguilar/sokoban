package com.sokoban.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sokoban.GameScreen;
import com.sokoban.sokobanWorld.Brick;
import com.sokoban.sokobanWorld.World;

public class FloorEntity extends Actor {
    private Texture texture;
    private Brick floor;
    private World world;
    public FloorEntity(final Brick floor, Texture texture, World world){
        this.floor=floor;
        this.texture=texture;
        this.world=world;
        setBounds(40*floor.x,40*floor.y,40,40);
        setTouchable(Touchable.enabled);
        //addListener(new ClickListener() {
//
        //   @Override
        //   public void clicked(InputEvent event, float x, float y) {
        //       System.out.println(x+", "+y);
        //       FloorEntity.this.world.guy.x=floor.x;
        //       FloorEntity.this.world.guy.y=floor.y;
        //       event.handle();//the Stage will stop trying to handle this event
        //   }
        //});
        //setDebug(true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setBounds(40*floor.x,40*floor.y,40,40);
        batch.draw(texture, getX(),getY());
    }
}