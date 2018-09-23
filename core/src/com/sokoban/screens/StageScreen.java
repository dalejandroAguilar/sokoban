package com.sokoban.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sokoban.MainGame;
import com.sokoban.entities.StageEntity;

public class StageScreen extends BaseScreen {
    final Table table;
    boolean isOnAdjusting;
    boolean isOnDragging;
    private Stage stage;
    private ShapeRenderer shapeRenderer;

    public StageScreen(final MainGame mainGame) {
        super(mainGame);
        stage = new Stage(new FitViewport(1920, 1080));
        isOnDragging = false;
        TextureAtlas atlas = mainGame.getManager().get("skin3/pack.atlas", TextureAtlas.class);
        Skin skin = new Skin(Gdx.files.internal("skin3/skin.json"), atlas);
        shapeRenderer = new ShapeRenderer();

        table = new Table(skin);
        table.setFillParent(true);
        //table.setDebug(true);
        table.top();
        //table.setTouchable(Touchable.enabled);

        int large = mainGame.getLevelManager().getSize();
        final int nCol = large / 4;
        for (int i = 0; i < large; i++) {
            final int indexLevel = i;
            if (i % 4 == 0)
                table.row();
            StageEntity stageEntity;
            if (i > mainGame.getLevelManager().getProgressLevel())
                stageEntity = new StageEntity(StageEntity.LOCKED,mainGame.getLevelManager().nameLevels[i] );
            else {
                stageEntity = new StageEntity(StageEntity.COMPLETED,mainGame.getLevelManager().nameLevels[i]);

                stageEntity.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //Gdx.app.log("touch-.........", "touch-.........");
                        if (!isOnDragging) {
                            mainGame.getLevelManager().setIndexLevel(indexLevel);
                            mainGame.setScreen(new GameScreen(mainGame));
                        }
                    }

                });
            }
            table.add(stageEntity);
        }
        table.setPosition(0, 0);
        table.addListener(new InputListener() {
            float xClick;
            float yClick;
            float yDragg;
            float yTableFix;


            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                yDragg = y - yClick + yDragg;
                if (Math.abs(yDragg) > 200) {
                    table.setPosition(table.getX(), -yClick + y + table.getY());
                    //Gdx.app.log("dragg", "" + y);

                    isOnDragging = true;
                }

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //table.setPosition(x,y);
                //Gdx.app.log("TouchDown", "touch " + y);
                yTableFix = table.getY();
                yDragg = 0;
                xClick = x;
                yClick = y;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.app.log("TouchUp", "" + table.getY());
                //Gdx.app.log("TouchUp",""+stage.getViewport().getBottomGutterHeight());
                //table.setPosition(table.getX(),yTableFix+(int)(yDragg/table.getRowHeight(0))*table.getRowHeight(0));
                //Gdx.app.log("yTableFix", "" + yTableFix + "drag" + yDragg);
                if (Math.abs(yDragg) > 200)
                    if (!isOnAdjusting) {
                        float rowHeight = table.getRowHeight(0);

                        MoveToAction moveToAction = new MoveToAction();
                        moveToAction.setDuration(0.25f);
                        moveToAction.setInterpolation(Interpolation.fade);
                        if (table.getY() < 0)
                            moveToAction.setPosition(0, 0);
                        else if (table.getY() > (nCol - 1) * rowHeight)
                            moveToAction.setPosition(0, (nCol - 1) * rowHeight);
                        else
                            moveToAction.setPosition(0, yTableFix + (int) (yDragg / rowHeight) * rowHeight);
                        table.addAction(Actions.sequence(
                                Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        isOnAdjusting = true;
                                    }
                                }),
                                moveToAction,
                                Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        isOnAdjusting = false;
                                    }
                                })
                        ));
                    }
                isOnDragging = false;
            }
        });
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {
        super.show();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.f, 1.f, 0.f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        //shapeRenderer.setColor(Color.BLACK);
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        //shapeRenderer.line(0,1,1000,1);
        //shapeRenderer.line(0,10,1000,10);
        //shapeRenderer.line(0,20,1000,20);shapeRenderer.line(0,200,1000,200);
        //shapeRenderer.line(0,table.getRowHeight(1),1000,table.getRowHeight(1));
        //shapeRenderer.line(0,table.getY(),1000,table.getY());
        ////Gdx.app.log("row:",""+ table.getRows()*table.getRowHeight(0) );
        //shapeRenderer.line(0,0,0,1000);
        //shapeRenderer.end();
        stage.act(delta);
        stage.draw();
    }

}
