package com.sokoban.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class ImageButtonEntity extends Actor implements Disposable {
    private Texture textureIcon;
    private Texture textureEnable;
    private Texture textureDisable;
    private boolean isEnable;

    public ImageButtonEntity(Texture textureIcon, Texture textureEnable, Texture textureDisable) {
        this.textureIcon = textureIcon;
        this.textureEnable = textureEnable;
        this.textureDisable = textureDisable;
        isEnable = true;
        setBounds(0, 0, textureEnable.getWidth(), textureEnable.getHeight());
    }

    public void setToEnable() {
        isEnable = true;
    }

    public void setToDisable() {
        isEnable = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        if (isEnable)
            batch.draw(textureEnable, getX(), getY());
        else
            batch.draw(textureDisable, getX(), getY());
        batch.draw(textureIcon, getX(), getY());
    }

    @Override
    public void dispose() {
        textureIcon.dispose();
        textureEnable.dispose();
        textureDisable.dispose();
    }

}
