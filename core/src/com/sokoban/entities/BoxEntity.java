package com.sokoban.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.sokoban.sokobanWorld.Box;

import static com.sokoban.Constants.PLANK_CONSTANT;


public class BoxEntity extends Actor implements Disposable {
    public Box box;
    private Texture texture[];

    public BoxEntity(Box box) {
        this.box = box;
        texture = new Texture[3];
        texture[0] = new Texture(Gdx.files.internal("world/Box.png"));
        texture[1] = new Texture(Gdx.files.internal("world/Embonated.png"));
        texture[2] = new Texture(Gdx.files.internal("world/Box_Dead.png"));
        setBounds(box.x * PLANK_CONSTANT, box.y * PLANK_CONSTANT, PLANK_CONSTANT, PLANK_CONSTANT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!box.isAlive)
            batch.draw(texture[2], getX(), getY(), getWidth(), getHeight());
        else if (box.isEmbonated)
            batch.draw(texture[1], getX(), getY(), getWidth(), getHeight());
        else
            batch.draw(texture[0], getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void dispose() {
        for (int i = 0; i < 3; i++)
            texture[i].dispose();
    }
}