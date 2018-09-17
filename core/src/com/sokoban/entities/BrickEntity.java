package com.sokoban.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sokoban.sokobanWorld.Brick;
import static com.sokoban.Constants.*;

public class BrickEntity extends Actor {

    private Texture texture;
    private Brick brick;
    public BrickEntity(Brick brick, Texture texture){
        this.brick=brick;
        this.texture=texture;
        setBounds(brick.x * PLANK_CONSTANT, brick.y * PLANK_CONSTANT, PLANK_CONSTANT, PLANK_CONSTANT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(),getWidth(),getHeight());

    }
}
