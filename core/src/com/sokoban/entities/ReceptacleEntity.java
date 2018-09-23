package com.sokoban.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Disposable;
import com.sokoban.sokobanWorld.Receptacle;
import static com.sokoban.Constants.*;

public class ReceptacleEntity extends Actor implements Disposable{

    private Texture texture;
    public Receptacle receptacle;
    public ReceptacleEntity(Receptacle receptacle){
       this. receptacle=receptacle;
        texture=new Texture(Gdx.files.internal("world/Receptacle.png"));

        setBounds(PLANK_CONSTANT*receptacle.x,PLANK_CONSTANT*receptacle.y,PLANK_CONSTANT,PLANK_CONSTANT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //setPosition(40*recpetacle.x,40*recpetacle.y);


        //System.out.printf("%f, %f, %f, %f\n",getColor().r,getColor().g,getColor().b,getColor().a);
        //SpriteBatch sp= new SpriteBatch();
        //batch.setColor(getColor());

        Color color = getColor();

        batch.setColor(color.r, color.g, color.b, color.a*parentAlpha);
        //toFront();
        batch.draw(texture, getX(), getY(),getWidth(),getHeight());
        //addAction(Actions.fadeOut(10));

    }


    @Override
    public void dispose() {
        texture.dispose();
    }
}
