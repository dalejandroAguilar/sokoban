package com.sokoban.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sokoban.MainGame;
import com.sokoban.entities.StageEntity;

public class StageScreen extends BaseScreen {
    final Table table;
    boolean isOnAdjusting;
    boolean isOnDragging;
    private Stage stage;
    private Stage stageSecondary;
    private ShapeRenderer shapeRenderer;
    private float bannerHeight = 200;
    Texture textureHome = new Texture(Gdx.files.internal("buttons2/home.png"));
    TextureAtlas atlas = mainGame.getManager().get("skin3/pack.atlas", TextureAtlas.class);

    public StageScreen(final MainGame mainGame) {
        super(mainGame);
        stage = new Stage(new FitViewport(1920, 1080));
        stageSecondary = new Stage(new FitViewport(1920, 1080), stage.getBatch());
        isOnDragging = false;



        Skin skin = new Skin(Gdx.files.internal("skin3/skin.json"), atlas);
        Skin skinDefault = new Skin(Gdx.files.internal("skin/uiskin.json"));
        shapeRenderer = new ShapeRenderer();

        table = new Table(skinDefault);
        table.setFillParent(true);
        //table.setDebug(true);
        table.top();
        Actor actor = new Actor();
        table.add(new Label("SELECT A WORLD", skin)).colspan(5).height(400).row();
        //table.setTouchable(Touchable.enabled);

        int large = mainGame.getLevelManager().getLevelWorlds().length;
        final int nCol = large / 5;
        for (int i = 0; i < large; i++) {
            final int indexLevel = i;
            if (i % 5 == 0)
                table.row();
            StageEntity stageEntity;

            stageEntity = new StageEntity(StageEntity.COMPLETED, mainGame.getLevelManager().getLevelWorlds()[i].getName());

            stageEntity.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //Gdx.app.log("touch-.........", "touch-.........");
                    if (!isOnDragging) {
                        mainGame.getLevelManager().setIndexWorld(indexLevel);
                        mainGame.setScreen(new LevelScreen(mainGame));
                    }
                }

            });

            Table justTable = new Table(skin);
            justTable.add(stageEntity).row();
            justTable.add(new Label(stageEntity.getName(), skin));
            table.add(justTable).pad(10);
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
                    isOnDragging = true;
                }

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
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
                        else if (table.getY() > (nCol - 1+1) * rowHeight)
                            moveToAction.setPosition(0, (nCol - 1+1) * rowHeight);
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
        Skin skinDefault2 = new Skin(Gdx.files.internal("skin2/uiskin.json"));

        ImageButton.ImageButtonStyle imageButtonStyleHome = new ImageButton.ImageButtonStyle(
                skinDefault2.get(Button.ButtonStyle.class));
        imageButtonStyleHome.imageUp = new TextureRegionDrawable(new TextureRegion(textureHome));
        ImageButton imageButtonHome = new ImageButton(imageButtonStyleHome);
        imageButtonHome.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainGame.setScreen(new TitleScreen(mainGame));
            }
        });

        Table secondaryTable = new Table(skin);
        secondaryTable.setFillParent(true);
        secondaryTable.bottom();
        secondaryTable.add(imageButtonHome).pad(10);



        stage.addActor(table);
        stageSecondary.addActor(secondaryTable);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stageSecondary);
        inputMultiplexer.addProcessor(stage);


        Gdx.input.setInputProcessor(inputMultiplexer);
    }


    @Override
    public void show() {
        super.show();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height);
        stageSecondary.getViewport().update(width, height);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.f, 1.f, 0.5f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        stageSecondary.act(delta);
        stageSecondary.draw();
    }

    @Override
    public void dispose() {
        textureHome.dispose();
        atlas.dispose();
    }
}
