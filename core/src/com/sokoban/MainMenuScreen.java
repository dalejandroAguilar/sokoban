package com.sokoban;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen extends BaseScreen implements ApplicationListener {
    private Stage stage;
    Texture adTextrue;
    final AdManager ads;
    //Rectangle showrectang
    SpriteBatch batch;
    OrthographicCamera cam;

    public MainMenuScreen(final MainGame mainGame, AdManager adMob) {
        super(mainGame);
        Skin skin=new Skin(Gdx.files.internal("skin/uiskin.json"));
        stage= new Stage( new FitViewport(640,360));
        TextButton playButton = new TextButton("Play",skin);
        playButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainGame.setScreen(new GameScreen(mainGame));
            }
        });
        TextButton levelButton = new TextButton("Level",skin);

        Table table = new Table(skin);
        //table.setDebug(true);
        table.setFillParent(true);
        table.add(playButton);
        table.row();
        table.add(levelButton);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        ads=adMob;

        batch=new SpriteBatch();
        cam=new OrthographicCamera(800,1280);
        cam.position.set(800/2,1280/2,0);
    }


    @Override
    public void render(float delta) {
        //super.render(delta);
        cam.update();
        Gdx.gl.glClearColor(0.f, 1.f, 1.f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();
        stage.act();
        batch.begin();

        batch.end();
        stage.draw();
        ads.show();
        //batch.end();
    }

    @Override
    public void create() {

    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {
        //super.dispose();
        stage.dispose();
        //Gdx.input.setInputProcessor(null);
    }
}
