package com.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen extends BaseScreen {
    private Stage stage;
    SpriteBatch batch;
    public MainMenuScreen(final MainGame mainGame) {
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
    }

    @Override
    public void render(float delta) {
        //super.render(delta);
        Gdx.gl.glClearColor(0.f, 1.f, 1.f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();
        stage.act();
        stage.draw();
        //batch.end();
    }

    @Override
    public void dispose() {
        //super.dispose();
        stage.dispose();
        //Gdx.input.setInputProcessor(null);
    }
}
