package com.sokoban.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.sokoban.sokobanWorld.Receptacle;

public class ReceptacleEntity extends Actor {
    private Texture texture;
    private Receptacle recpetacle;
    public ReceptacleEntity(Receptacle recpetacle, Texture texture){
        this.recpetacle=recpetacle;
        this.texture=texture;

        setSize(40,40);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(40*recpetacle.x,40*recpetacle.y);


        //System.out.printf("%f, %f, %f, %f\n",getColor().r,getColor().g,getColor().b,getColor().a);
        //SpriteBatch sp= new SpriteBatch();
        //batch.setColor(getColor());
        //toFront();
        batch.draw(texture, getX(),getY());
        //addAction(Actions.fadeOut(10));

    }

    @Override
    public void act(float delta) {


    }
}
