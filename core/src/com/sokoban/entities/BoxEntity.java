package com.sokoban.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sokoban.sokobanWorld.Box;

public class BoxEntity extends Actor {
    private Texture texture[];
    public Box box;
    public boolean isEmbonated;

    public BoxEntity(Box box, Texture[] texture) {
        this.box = box;
        this.texture = texture;
        setBounds(box.x * 40, box.y * 40, 40, 40);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //setPosition(40*box.x,40*box.y);
        batch.draw(texture[isEmbonated ? 1 : 0], getX(), getY());
    }
}