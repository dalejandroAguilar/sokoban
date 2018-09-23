package com.sokoban.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sokoban.MainGame;

public class TitleScreen extends BaseScreen {
    private Stage stage;
    SpriteBatch batch;
    public TitleScreen(final MainGame mainGame) {
        super(mainGame);




        TextureAtlas atlas = mainGame.getManager().get("skin3/pack.atlas", TextureAtlas.class);
        Skin skin = new Skin(Gdx.files.internal("skin3/skin.json"), atlas);



        stage= new Stage( new FitViewport(1920,1080));
        final TextButton playButton = new TextButton("Play",skin);
        //playButton.addAction(Actions.forever( Actions.sequence(
        //        Actions.sizeBy(5,5,.5f),
        //        Actions.sizeBy(1.f/5,1.f/5,.5f)
        //)    ))    ;
        final TextButton levelButton = new TextButton("Level",skin);
        final TextButton resetButton = new TextButton("Reset",skin);
        final Image texture = new Image(new Texture(Gdx.files.internal("Title.png")));

        playButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                texture.addAction(Actions.sequence(
                        Actions.parallel(
                                Actions.moveBy(0,1500,.4f),
                                Actions.fadeOut(.5f)
                        )
                        ,
                        Actions.run(new Runnable() {
                            public void run() {

                                mainGame.setScreen(new StageScreen(mainGame));
                            }
                        })
                ));

                playButton.addAction(Actions.sequence(
                        Actions.parallel(
                        Actions.moveBy(1500,0,.4f),
                                Actions.fadeOut(.5f)
                                )
                        ,
                        Actions.run(new Runnable() {
                            public void run() {

                                mainGame.setScreen(new StageScreen(mainGame));
                            }
                        })
                ));

                resetButton.addAction(
                        Actions.parallel(
                                Actions.moveBy(-1500,0,.4f),
                                Actions.fadeOut(.5f)
                        )
                       );
                //playButton.addAction(Actions.moveBy(100,0,1));

            }
        });

        resetButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainGame.getLevelManager().resetProgress();
                //playButton.addAction(Actions.moveBy(100,0,1));

            }
        });
        //Label label=new Label("hola",skin);
        //TextButton levelButton = new TextButton("level",skin);


        //stage.addActor(label);
        //stage.addActor(levelButton);


        Table table = new Table(skin);
        //table.setDebug(true);

        table.setFillParent(true);
        table.add(texture).width(564).pad(20).row();
        table.add(playButton).width(400).pad(20);
        table.row();
       // table.add(levelButton).width(400).pad(20);
        //table.row();
        table.add(resetButton).width(400).pad(20);
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

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width,height);
    }
}
