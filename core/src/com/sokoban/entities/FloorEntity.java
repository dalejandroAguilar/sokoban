package com.sokoban.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Disposable;
import com.sokoban.sokobanWorld.Floor;
import com.sokoban.sokobanWorld.World;

import static com.sokoban.Constants.PLANK_CONSTANT;

public class FloorEntity extends Actor implements Disposable {
    public Floor floor;
    private Texture textureAlive;
    private Texture textureDead;

    //private World world;
    public FloorEntity(final Floor floor, World world) {
        this.floor = floor;
        //debug();
        textureAlive = new Texture(Gdx.files.internal("world/Floor.png"));
        textureDead = new Texture(Gdx.files.internal("world/Dead_Floor.png"));
        setBounds(PLANK_CONSTANT * floor.x, PLANK_CONSTANT * floor.y, PLANK_CONSTANT, PLANK_CONSTANT);
        setTouchable(Touchable.enabled);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (floor.isAlive)
            batch.draw(textureAlive, getX(), getY(), getWidth(), getHeight());
        else
            batch.draw(textureDead, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void dispose() {
        textureAlive.dispose();
        textureDead.dispose();
    }
}