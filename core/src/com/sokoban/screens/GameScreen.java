package com.sokoban.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sokoban.MainGame;
import com.sokoban.entities.BoxEntity;
import com.sokoban.entities.BrickEntity;
import com.sokoban.entities.CoolGuyEntity;
import com.sokoban.entities.FloorEntity;
import com.sokoban.entities.ReceptacleEntity;
import com.sokoban.sokobanWorld.Box;
import com.sokoban.sokobanWorld.Brick;
import com.sokoban.sokobanWorld.Floor;
import com.sokoban.sokobanWorld.Node;
import com.sokoban.sokobanWorld.PathFinding;
import com.sokoban.sokobanWorld.Receptacle;
import com.sokoban.sokobanWorld.Reel;
import com.sokoban.sokobanWorld.World;

import java.util.ArrayList;

import static com.sokoban.Constants.CHANGE_VIEWPORT;
import static com.sokoban.Constants.PLANK_CONSTANT;
import static com.sokoban.Constants.REDO;
import static com.sokoban.Constants.RESTART;
import static com.sokoban.Constants.UNDO;

public class GameScreen extends BaseScreen implements GestureDetector.GestureListener {
    static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3, NULL = -1, THREESHOLD_VELOCITY = 50;
    GestureDetector gestureDetector;
    TextureAtlas atlas = mainGame.getManager().get("skin3/pack.atlas", TextureAtlas.class);
    Texture textureUndo = new Texture(Gdx.files.internal("buttons2/undo.png"));
    Texture textureRedo = new Texture(Gdx.files.internal("buttons2/redo.png"));
    Texture textureRestart = new Texture(Gdx.files.internal("buttons2/restart.png"));
    Texture textureHome = new Texture(Gdx.files.internal("buttons2/home.png"));
    Texture texturePath = new Texture(Gdx.files.internal("buttons2/path.png"));
    private Skin skin;
    private Stage stage;
    private Stage stage2;
    private Stage stage3;
    private World world;
    private CoolGuyEntity coolGuyEntity;
    private ArrayList<BrickEntity> brickEntityList;
    private ArrayList<FloorEntity> floorEntityList;
    private ArrayList<BoxEntity> boxEntityList;
    private ArrayList<ReceptacleEntity> receptacleEntityList;
    private float minX, minY, maxX, maxY;
    private boolean viewPortMap;
    private Label labelMoves;
    private Label labelPushes;

    public GameScreen(final MainGame mainGame) {
        super(mainGame);
        viewPortMap = true;

        skin = new Skin(Gdx.files.internal("skin3/skin.json"), atlas);
        stage = new Stage(new FitViewport(1920, 1080));
        stage2 = new Stage(new FitViewport(1920, 1080), stage.getBatch());
        stage3 = new Stage(new FitViewport(1920, 1080), stage.getBatch());

        InputMultiplexer multiplexer = new InputMultiplexer();

        world = new World(mainGame.getLevelManager().getCurrentLevel().getMap());
        coolGuyEntity = new CoolGuyEntity(world.guy);
        brickEntityList = new ArrayList<BrickEntity>();
        boxEntityList = new ArrayList<BoxEntity>();
        receptacleEntityList = new ArrayList<ReceptacleEntity>();
        floorEntityList = new ArrayList<FloorEntity>();

        for (Floor floor : this.world.groupFloor) {

            floorEntityList.add(new FloorEntity(new Floor(floor.x, floor.y, floor.isAlive), world));
        }


        for (final FloorEntity floorEntity : floorEntityList) {
            final Node target = new Node(floorEntity.floor.x, floorEntity.floor.y);
            floorEntity.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!coolGuyEntity.isOnWalking()) {
                        PathFinding pathFinding = new PathFinding(world, target);
                        int path[] = pathFinding.getPath();
                        if (path[0] != NULL) {
                            SequenceAction animation = new SequenceAction();
                            coolGuyEntity.startWalk(NULL);
                            for (int i = 0; i < path.length; i++) {
                                final int action = path[i];
                                int amountX = 0;
                                int amountY = 0;
                                switch (action) {
                                    case UP:
                                        amountY = -PLANK_CONSTANT;
                                        break;
                                    case DOWN:
                                        amountY = PLANK_CONSTANT;
                                        break;

                                    case LEFT:
                                        amountX = -PLANK_CONSTANT;
                                        break;
                                    case RIGHT:
                                        amountX = PLANK_CONSTANT;
                                        break;
                                }


                                animation.addAction(Actions.run(new Runnable() {
                                    public void run() {
                                        coolGuyEntity.setDirection(action);
                                    }
                                }));

                                animation.addAction(Actions.moveBy(amountX, amountY, 0.2f));

                                animation.addAction(Actions.run(new Runnable() {
                                    public void run() {
                                        world.move(action);
                                        labelMoves.setText("moves: " + world.getMoves());
                                    }
                                }));

                            }
                            animation.addAction(Actions.run(new Runnable() {
                                public void run() {
                                    coolGuyEntity.stopWalk();
                                    //floorEntity.setIsAlive(true);
                                }
                            }));

                            coolGuyEntity.addAction(animation);
                        }
                    }
                }

                ;
            });
            stage.addActor(floorEntity);
        }


        for (Receptacle receptacle : world.groupReceptacle)
            receptacleEntityList.add(new ReceptacleEntity(receptacle));

        for (final ReceptacleEntity receptacleEntity : receptacleEntityList) {
            final Node target = new Node(receptacleEntity.receptacle.x, receptacleEntity.receptacle.y);
            receptacleEntity.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!coolGuyEntity.isOnWalking()) {
                        PathFinding pathFinding = new PathFinding(world, target);
                        int path[] = pathFinding.getPath();
                        if (path[0] != NULL) {
                            SequenceAction animation = new SequenceAction();
                            coolGuyEntity.startWalk(NULL);
                            for (int i = 0; i < path.length; i++) {
                                final int action = path[i];
                                int amountX = 0;
                                int amountY = 0;
                                switch (action) {
                                    case UP:
                                        amountY = -PLANK_CONSTANT;
                                        break;
                                    case DOWN:
                                        amountY = PLANK_CONSTANT;
                                        break;

                                    case LEFT:
                                        amountX = -PLANK_CONSTANT;
                                        break;
                                    case RIGHT:
                                        amountX = PLANK_CONSTANT;
                                        break;
                                }
                                world.move(action);
                                animation.addAction(Actions.run(new Runnable() {
                                    public void run() {
                                        coolGuyEntity.setDirection(action);
                                    }
                                }));

                                animation.addAction(Actions.moveBy(amountX, amountY, 0.2f));

                                animation.addAction(Actions.run(new Runnable() {
                                    public void run() {
                                        //world.move(action);
                                    }
                                }));

                            }
                            animation.addAction(Actions.run(new Runnable() {
                                public void run() {
                                    coolGuyEntity.stopWalk();
                                }
                            }));

                            coolGuyEntity.addAction(animation);
                        }
                    }
                }

                ;
            });
            stage.addActor(receptacleEntity);
        }
        for (Brick brick : world.groupBrick)
            brickEntityList.add(new BrickEntity(brick));

        for (BrickEntity brickEntity : brickEntityList)
            stage.addActor(brickEntity);

        for (Box box : world.groupBox)
            boxEntityList.add(new BoxEntity(box));

        for (BoxEntity boxEntity : boxEntityList)
            stage.addActor(boxEntity);

        stage.addActor(coolGuyEntity);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.left().top();

        TextButton viewport = new TextButton("viewport", skin);

        Skin skinDefault = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Skin skinDefault2 = new Skin(Gdx.files.internal("skin2/uiskin.json"));


        ImageButton.ImageButtonStyle imageButtonStyleUndo = new ImageButton.ImageButtonStyle(
                skinDefault.get(Button.ButtonStyle.class));
        imageButtonStyleUndo.imageUp = new TextureRegionDrawable(new TextureRegion(textureUndo));
        ImageButton imageButtonUndo = new ImageButton(imageButtonStyleUndo);
        imageButtonUndo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onAction(UNDO);
            }
        });

        ImageButton.ImageButtonStyle imageButtonStyleRedo = new ImageButton.ImageButtonStyle(
                skinDefault.get(Button.ButtonStyle.class));
        imageButtonStyleRedo.imageUp = new TextureRegionDrawable(new TextureRegion(textureRedo));
        ImageButton imageButtonRedo = new ImageButton(imageButtonStyleRedo);
        imageButtonRedo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onAction(REDO);
            }
        });

        ImageButton.ImageButtonStyle imageButtonStyleRestart = new ImageButton.ImageButtonStyle(
                skinDefault.get(Button.ButtonStyle.class));
        imageButtonStyleRestart.imageUp = new TextureRegionDrawable(new TextureRegion(textureRestart));
        ImageButton imageButtonRestart = new ImageButton(imageButtonStyleRestart);
        imageButtonRestart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onAction(RESTART);
            }
        });

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

        ImageButton.ImageButtonStyle imageButtonStylePath = new ImageButton.ImageButtonStyle(
                skinDefault2.get(Button.ButtonStyle.class));
        imageButtonStylePath.imageUp = new TextureRegionDrawable(new TextureRegion(texturePath));
        ImageButton imageButtonPath = new ImageButton(imageButtonStylePath);
        imageButtonPath.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainGame.setScreen(new StageScreen(mainGame));
            }
        });

        labelMoves = new Label("moves: 0", skin);
        labelPushes = new Label("pushes: 0", skin);

        viewport.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onAction(CHANGE_VIEWPORT);
            }
        });
        //table.setDebug(true);
        table.add(new Actor()).height(mainGame.handler.getHeightBanner()).row();
        table.add(imageButtonUndo);
        table.add(imageButtonRedo).row();
        table.add(imageButtonRestart).colspan(2).row();
        table.add(labelMoves).colspan(2).row();
        table.add(labelPushes).colspan(2).row();
        table.add(viewport).colspan(2);
        Table table2 = new Table(skin);
        table2.left().bottom();
        table2.add(imageButtonHome);
        table2.add(imageButtonPath);

        stage2.addActor(table2);

        stage2.addActor(table);

        gestureDetector = new GestureDetector(this);

        multiplexer.addProcessor(stage2);
        multiplexer.addProcessor(gestureDetector);
        multiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(multiplexer);

        setBounds();

        Table table1 = new Table(skin);
        table1.setFillParent(true);
        table1.add(new Label(mainGame.getLevelManager().getCurrentLevelWorld().getName(), skin)).row();
        table1.add(new Label(mainGame.getLevelManager().getCurrentLevel().getName(), skin));
        table1.addAction(
                Actions.sequence(
                        Actions.delay(3)
                        ,
                        Actions.parallel(
                                Actions.moveBy(-stage3.getWidth(), 0, 1f)
                                ,
                                Actions.fadeOut(1f)
                        )

                )
        );
        stage3.addActor(table1);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.f, 1.f, 1.f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
        stage2.act(delta);
        stage2.draw();
        stage3.act(delta);
        stage3.draw();

        if (!viewPortMap) {
            stage.getCamera().position.set(coolGuyEntity.getX(), coolGuyEntity.getY(), 0);
        }


    }

    @Override
    public void dispose() {
        coolGuyEntity.dispose();

        for (BrickEntity brickEntity : brickEntityList)
            brickEntity.dispose();

        for (ReceptacleEntity receptacleEntity : receptacleEntityList)
            receptacleEntity.dispose();
        for (BoxEntity boxEntity : boxEntityList)
            boxEntity.dispose();
        for (FloorEntity floorEntity : floorEntityList)
            floorEntity.dispose();
        stage.dispose();
        atlas.dispose();
        textureUndo.dispose();
        textureRedo.dispose();
        textureRestart.dispose();
        textureHome.dispose();
        texturePath.dispose();

        Gdx.input.setInputProcessor(null);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height);
        stage2.getViewport().update(width, height);
        stage3.getViewport().update(width, height);
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (Math.abs(velocityX) > Math.abs(velocityY))
            if (velocityX > 0)
                onAction(RIGHT);
            else
                onAction(LEFT);
        else if (Math.abs(velocityX) < Math.abs(velocityY))
            if (velocityY > 0)
                onAction(UP);
            else
                onAction(DOWN);
        else
            return false;
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;

    }


    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;

    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    private void onAction(final int action) {
        if (!coolGuyEntity.isOnWalking()) {
            if (action >= 0 && action < 10) {
                int index = world.enhancedMove(action);
                int amountX = 0;
                int amountY = 0;
                switch (action) {
                    case UP:
                        amountY = -PLANK_CONSTANT;
                        break;
                    case DOWN:
                        amountY = PLANK_CONSTANT;
                        break;
                    case LEFT:
                        amountX = -PLANK_CONSTANT;
                        break;
                    case RIGHT:
                        amountX = PLANK_CONSTANT;
                        break;
                }

                coolGuyEntity.setDirection(action);


                if (index != -2)
                    coolGuyEntity.addAction(
                            Actions.sequence(
                                    Actions.run(new Runnable() {
                                        public void run() {
                                            coolGuyEntity.startWalk(action);
                                        }
                                    }),
                                    Actions.moveBy(amountX, amountY, 0.3f),
                                    Actions.run(new Runnable() {
                                        public void run() {
                                            coolGuyEntity.stopWalk();
                                            labelMoves.setText("moves: " + world.getMoves());
                                        }
                                    })
                            ));
                if (index != -2 && index != -1)
                    boxEntityList.get(index).addAction(
                            Actions.sequence(
                                    Actions.run(new Runnable() {
                                        public void run() {
                                            coolGuyEntity.startPush(action);
                                        }
                                    }),
                                    Actions.moveBy(amountX, amountY, 0.3f),//, Interpolation.swing),
                                    Actions.run(new Runnable() {
                                        public void run() {
                                            coolGuyEntity.stopPush();
                                            labelPushes.setText("pushes: " + world.getPushes());
                                            labelMoves.setText("moves: " + world.getMoves());
                                        }
                                    })
                            ));

            }
            if (action >= 10 && action < 20) {
                Reel reel = new Reel(world.guy, world.groupBox, world.groupReceptacle, world.groupBrick);
                switch (action) {
                    case UNDO:
                        world.undo();
                        labelMoves.setText("moves: " + world.getMoves());
                        labelPushes.setText("pushes: " + world.getPushes());
                        break;
                    case REDO:
                        world.redo();
                        labelMoves.setText("moves: " + world.getMoves());
                        labelPushes.setText("pushes: " + world.getPushes());
                        break;
                    case RESTART:
                        world.restart();
                        labelMoves.setText("moves: " + world.getMoves());
                        labelPushes.setText("pushes: " + world.getPushes());
                        break;
                }
                coolGuyEntity.addAction(
                        Actions.sequence(
                                Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        //TODO: set te correct position on undo, restart and redo
                                        //coolGuyEntity.setDirection(coolGuyEntity.guy.orientation);
                                    }
                                })
                                ,
                                Actions.moveBy(world.guy.x * PLANK_CONSTANT - reel.guy.x * PLANK_CONSTANT, world.guy.y * PLANK_CONSTANT - reel.guy.y * PLANK_CONSTANT, 0.5f, Interpolation.swing)
                        )
                );

                for (int i = 0; i < boxEntityList.size(); i++) {
                    boxEntityList.get(i).addAction(Actions.moveBy(world.groupBox.get(i).x * PLANK_CONSTANT - reel.groupBox.get(i).x * PLANK_CONSTANT, world.groupBox.get(i).y * PLANK_CONSTANT - reel.groupBox.get(i).y * PLANK_CONSTANT, 0.5f, Interpolation.swing));
                }
            }

        }
        if (action == CHANGE_VIEWPORT) {
            viewPortMap = !viewPortMap;
            if (viewPortMap)
                setBounds();
            if (!viewPortMap)
                ((OrthographicCamera) stage.getCamera()).zoom = 1;
        }
        if (world.win()) {
            Label label = new Label("You Win!!", skin);
            Table table = new Table(skin);
            table.setFillParent(true);
            table.center();

            TextButton nextLevelButton = new TextButton("Next Level", skin);
            TextButton goTitleButton = new TextButton("Go Home", skin);
            TextButton goLevelButton = new TextButton("Select level", skin);

            mainGame.getLevelManager().nextLevel();

            nextLevelButton.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    mainGame.setScreen(new GameScreen(mainGame));
                }
            });

            goTitleButton.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    mainGame.setScreen(new TitleScreen(mainGame));
                }
            });

            goLevelButton.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    mainGame.setScreen(new StageScreen(mainGame));
                }
            });

            table.add(label).colspan(3).center().row();
            table.add(nextLevelButton);
            table.add(goLevelButton);
            table.add(goTitleButton);

            stage3.addActor(table);
            Gdx.input.setInputProcessor(stage3);
            stage2.addAction(Actions.moveBy(-500, 0, 0.5f));
        }

    }

    private void setBounds() {
        minX = maxX = brickEntityList.get(0).getX();
        minY = maxY = brickEntityList.get(0).getY();
        for (BrickEntity brickEntity : brickEntityList) {
            if (minX > brickEntity.getX())
                minX = brickEntity.getX();
            if (maxX < brickEntity.getX())
                maxX = brickEntity.getX();
            if (minY > brickEntity.getY())
                minY = brickEntity.getY();
            if (maxY < brickEntity.getY())
                maxY = brickEntity.getY();
        }
        maxX += PLANK_CONSTANT;
        maxY += PLANK_CONSTANT;
        float deltaX = maxX - minY;
        float deltaY = maxY - minY;
        float screenX = stage.getWidth();
        float screenY = stage.getHeight();
        float bannerSpace = mainGame.handler.getHeightBanner();
        float zoom = Math.max(deltaX / (screenX - 160 * 2), deltaY /
                (screenY - bannerSpace));
        ((OrthographicCamera) stage.getCamera()).zoom = zoom;
        ((OrthographicCamera) stage.getCamera()).position.set(-160 * zoom + deltaX / 2.f, +bannerSpace / 2 * zoom + deltaY / 2.f, 0);
    }
}