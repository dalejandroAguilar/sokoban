package com.sokoban.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sokoban.sokobanWorld.Brick;

public class BrickEntity extends Actor {
    private Texture texture;
    private Brick brick;
    public BrickEntity(Brick brick, Texture texture){
        this.brick=brick;
        this.texture=texture;
        setSize(40,40);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(40*brick.x,40*brick.y);
        batch.draw(texture, getX(),getY());
    }
}
