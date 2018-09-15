package com.sokoban.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sokoban.sokobanWorld.Guy;

public class GirlEntity extends Actor {
    public Guy guy;
    TextureRegion[] animationFrames;
    Animation animation;
    private Texture texture;
    public float elapsedTime;
    public boolean isWalking;
    TextureRegion[][] tmpFrames;
    public GirlEntity(Guy guy, Texture texture) {
        this.guy = guy;
        this.texture = texture;
        tmpFrames = TextureRegion.split(texture, 64, 128);

        animationFrames = new TextureRegion[9];
        //setDebug(true);
        //setSize(40,40);

        int index = 0;
        for (int i = 1; i<10; i++ )
            animationFrames[index++] = tmpFrames[0][i];

        animation = new Animation(0.3f/9f,animationFrames);


            setBounds(guy.x * 40, guy.y * 40, 40, 40);
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
        if (isWalking) {
            elapsedTime += Gdx.graphics.getDeltaTime();
            TextureRegion texture = (TextureRegion) (animation.getKeyFrame(elapsedTime, true));
            batch.draw(texture,getX()-200,-10);
        }
        else
            batch.draw(tmpFrames[0][0],getX()-200,-10);
        //setColor(color.r, color.g, color.b, color.a * parentAlpha);




    }


}
