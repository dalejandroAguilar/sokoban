package com.sokoban.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
//import com.sokoban.Constants;
import com.sokoban.sokobanWorld.Guy;

import static com.sokoban.Constants.*;

public class GuyEntity extends Actor {
    private Texture texture[][];
    public Guy guy;
    public boolean isOnPush;
    public GuyEntity(Guy guy, Texture[][] texture){
        this.guy=guy;
        this.texture =texture;
        isOnPush=false;
        //setDebug(true);
        //setSize(40,40);
        setBounds(guy.x*40,guy.y*40,40,40);
        //addAction(Actions.moveTo(1,2,3));

        System.out.println("asno");
        //GuyEntity.this.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));

       // addListener(new InputListener(){
       //     @Override
       //     public boolean keyDown(InputEvent event, int keycode) {
//
       //            // MoveByAction mba = new MoveByAction();
       //            // mba.setAmount(100f,100f);
       //            // mba.setDuration(100f);
       //            // GuyEntity.this.addAction(mba);
       //             System.out.println("asno");
       //            // GuyEntity.this.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
//
       //         return true;
       //     }
       // });

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //setPosition(40*guy.x,40*guy.y);
        //Color color = getColor();
        //System.out.println(color.a);
        //texture.
                //setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texture[isOnPush ? 1 : 0][guy.orientation], getX(),getY());

    }

}
