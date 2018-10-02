package com.sokoban.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.sokoban.sokobanWorld.Guy;

public class CoolGuyEntity extends Actor implements Disposable {
    public Guy guy;
    Texture[] IDLEFrames;
    Animation animationIDLE;
    Texture[] idleBackFrames;
    Animation animationIdleBack;
    Texture[] idleSideFrames;
    Animation animationIdleSide;

    Texture[] pushLeftFrames;
    Animation animationPushLeft;

    Texture[] walkFrontFrames;
    Animation animationWalkFront;

    Texture[] walkBackFrames;
    Animation animationWalkBack;

    Texture[] walkSideFrames;
    Animation animationWalkSide;

    private float elapsedTime;
    private boolean isWalking;
    private boolean isPushing;
    private int direction;

    public CoolGuyEntity(Guy guy) {
        this.guy = guy;
//        tmpFrames = TextureRegion.split(texture, 64, 128);
        elapsedTime = 0;

        IDLEFrames = new Texture[8];
        IDLEFrames[0] = new Texture(Gdx.files.internal("coolGuy/front/guyIDLE0.png"));
        IDLEFrames[1] = new Texture(Gdx.files.internal("coolGuy/front/guyIDLE1.png"));
        IDLEFrames[2] = new Texture(Gdx.files.internal("coolGuy/front/guyIDLE2.png"));
        IDLEFrames[3] = new Texture(Gdx.files.internal("coolGuy/front/guyIDLE3.png"));
        IDLEFrames[4] = new Texture(Gdx.files.internal("coolGuy/front/guyIDLE4.png"));
        IDLEFrames[5] = new Texture(Gdx.files.internal("coolGuy/front/guyIDLE5.png"));
        IDLEFrames[6] = new Texture(Gdx.files.internal("coolGuy/front/guyIDLE6.png"));
        IDLEFrames[7] = new Texture(Gdx.files.internal("coolGuy/front/guyIDLE7.png"));

        pushLeftFrames = new Texture[8];
        pushLeftFrames[0] = new Texture(Gdx.files.internal("coolGuy/pushLeft/push0.png"));
        pushLeftFrames[1] = new Texture(Gdx.files.internal("coolGuy/pushLeft/push1.png"));
        pushLeftFrames[2] = new Texture(Gdx.files.internal("coolGuy/pushLeft/push2.png"));
        pushLeftFrames[3] = new Texture(Gdx.files.internal("coolGuy/pushLeft/push3.png"));
        pushLeftFrames[4] = new Texture(Gdx.files.internal("coolGuy/pushLeft/push4.png"));
        pushLeftFrames[5] = new Texture(Gdx.files.internal("coolGuy/pushLeft/push5.png"));
        pushLeftFrames[6] = new Texture(Gdx.files.internal("coolGuy/pushLeft/push6.png"));
        pushLeftFrames[7] = new Texture(Gdx.files.internal("coolGuy/pushLeft/push7.png"));


        idleBackFrames = new Texture[8];
        idleSideFrames = new Texture[8];
        walkFrontFrames = new Texture[8];
        walkBackFrames = new Texture[8];
        walkSideFrames = new Texture[8];

        for (int i = 0; i < 8; i++) {
            idleBackFrames[i] = new Texture(Gdx.files.internal("coolGuy/idle/back/" + i + ".png"));
            idleSideFrames[i] = new Texture(Gdx.files.internal("coolGuy/idle/side/" + i + ".png"));
            walkFrontFrames[i] = new Texture(Gdx.files.internal("coolGuy/walk/front/" + i + ".png"));
            walkBackFrames[i] = new Texture(Gdx.files.internal("coolGuy/walk/back/" + i + ".png"));
            walkSideFrames[i] = new Texture(Gdx.files.internal("coolGuy/walk/side/" + i + ".png"));
        }
        //setDebug(true);
        //setSize(40,40);


        animationIDLE = new Animation(.13f, IDLEFrames);

        animationPushLeft = new Animation(.1f, pushLeftFrames);

        animationIdleBack = new Animation(.13f, idleBackFrames);

        animationIdleSide = new Animation(.13f, idleSideFrames);
        animationWalkFront = new Animation(.1f, walkFrontFrames);
        animationWalkBack = new Animation(.1f, walkBackFrames);
        animationWalkSide = new Animation(.10f, walkSideFrames);

        setBounds(guy.x * com.sokoban.Constants.PLANK_CONSTANT, guy.y * com.sokoban.Constants.PLANK_CONSTANT, com.sokoban.Constants.PLANK_CONSTANT, com.sokoban.Constants.PLANK_CONSTANT);
        //addAction(Actions.moveTo(1,2,3));


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        Animation animation = animationIDLE;

        int amountX = (int) getX() + 60;
        boolean flipX = false;
        if (isWalking) {
            if (isPushing)
                switch (direction) {
                    case com.sokoban.Constants.FRONT:
                        animation = animationWalkFront;
                        break;
                    case com.sokoban.Constants.BACK:
                        animation = animationWalkBack;
                        break;
                    case com.sokoban.Constants.LEFT:
                        animation = animationPushLeft;
                        amountX = amountX - 100;
                        //  animation.getKeyFrame(elapsedTime).
                        break;
                    case com.sokoban.Constants.RIGHT:
                        animation = animationPushLeft;
                        amountX = amountX + 20;
                        flipX = true;
                        //amountX=amountX+100;
                        break;
                }
            else
                switch (direction) {
                    case com.sokoban.Constants.FRONT:
                        animation = animationWalkFront;
                        break;
                    case com.sokoban.Constants.BACK:
                        animation = animationWalkBack;
                        break;
                    case com.sokoban.Constants.LEFT:
                        animation = animationWalkSide;
                        break;
                    case com.sokoban.Constants.RIGHT:
                        animation = animationWalkSide;
                        flipX = true;
                        break;
                }
        } else
            switch (direction) {
                case com.sokoban.Constants.FRONT:
                    animation = animationIDLE;
                    break;
                case com.sokoban.Constants.BACK:
                    animation = animationIdleBack;
                    break;
                case com.sokoban.Constants.LEFT:
                    animation = animationIdleSide;
                    break;
                case com.sokoban.Constants.RIGHT:
                    animation = animationIdleSide;
                    flipX = true;
                    break;
            }
        Texture texture = (Texture) (animation.getKeyFrame(elapsedTime, true));

        int amountY;

        batch.draw(texture, amountX, getY(), texture.getWidth(), texture.getHeight(), 0, 0, texture.getWidth(), texture.getHeight(), flipX, false);

    }

    //public void changeDirection(int direction) {
    //    this.direction = direction;
    //}

    public void startWalk(int direction) {
        setDirection(direction);
        elapsedTime = 0;
        isWalking = true;
    }

    public void stopWalk() {
        elapsedTime = 0;
        isWalking = false;

    }

    public void startPush(int dir) {
        startWalk(dir);
        isPushing = true;
    }

    public void stopPush() {
        stopWalk();
        isPushing = false;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isOnWalking() {
        return isWalking;
    }

    @Override
    public void dispose() {
        for (Texture texture : IDLEFrames)
            texture.dispose();
        for (Texture texture : pushLeftFrames)
            texture.dispose();
        for (Texture texture : idleBackFrames)
            texture.dispose();
        for (Texture texture : idleSideFrames)
            texture.dispose();
        for (Texture texture : walkFrontFrames)
            texture.dispose();
        for (Texture texture : walkBackFrames)
            texture.dispose();
        for (Texture texture : walkSideFrames)
            texture.dispose();
    }
}
