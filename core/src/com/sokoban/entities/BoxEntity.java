package com.sokoban.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sokoban.sokobanWorld.Box;
import static com.sokoban.Constants.*;


public class BoxEntity extends Actor {
    private Texture texture[];
    public Box box;

    public BoxEntity(Box box, Texture[] texture) {
        this.box = box;
        this.texture = texture;
        setBounds(box.x * PLANK_CONSTANT, box.y * PLANK_CONSTANT, PLANK_CONSTANT, PLANK_CONSTANT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(texture[box.isEmbonated ? 1 : 0], getX(), getY(),getWidth(),getHeight());
    }
}