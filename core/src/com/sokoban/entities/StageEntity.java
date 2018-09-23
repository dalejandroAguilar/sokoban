package com.sokoban.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Disposable;

public class StageEntity extends Actor implements Disposable {
    public final static int LOCKED = 1, UNLOCKED = 2, COMPLETED = 3;
    private int status;

    private Texture textureLocked, textureUnloked, textureCompleted;
    private String name;
    public StageEntity(int status, String name) {
        this.name = name;
        this.status = status;
        textureLocked = new Texture(Gdx.files.internal("boxes/close_box.png"));
        textureUnloked = new Texture(Gdx.files.internal("boxes/open_box.png"));
        textureCompleted = new Texture(Gdx.files.internal("boxes/complete_box.png"));
        setBounds(0,0,textureLocked.getWidth(),textureLocked.getHeight());
        setStatus(status);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status=status;
        switch (status){
            case LOCKED:
                setTouchable(Touchable.enabled);
                break;
            case UNLOCKED:
                setTouchable(Touchable.enabled);
                break;
            case COMPLETED:
                setTouchable(Touchable.enabled);
                break;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        switch (status){
            case LOCKED:
                batch.draw(textureLocked,getX(),getY());
                    break;
            case UNLOCKED:
                batch.draw(textureUnloked,getX(),getY());
            break;
            case COMPLETED:
                batch.draw(textureCompleted,getX(),getY());
            break;
        }
        BitmapFont font;
        font = new BitmapFont();
        font.getData().setScale(3);
        font.draw(batch, name, getX()+getWidth()/2, getY()+getHeight()/2);
    }

    @Override
    public void dispose() {
        textureLocked.dispose();
        textureUnloked.dispose();
        textureCompleted.dispose();
    }
}
