package com.sokoban.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.sokoban.sokobanWorld.Brick;

public class BrickEntity extends Actor implements Disposable {

    private Texture texture;

    public BrickEntity(Brick brick) {
        texture = new Texture(Gdx.files.internal("world/Brick.png"));
        setBounds(brick.x * com.sokoban.Constants.PLANK_CONSTANT, brick.y * com.sokoban.Constants.PLANK_CONSTANT, com.sokoban.Constants.PLANK_CONSTANT, com.sokoban.Constants.PLANK_CONSTANT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());

    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
