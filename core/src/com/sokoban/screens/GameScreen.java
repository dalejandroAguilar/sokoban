package com.sokoban.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.IntAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sokoban.MainGame;
import com.sokoban.entities.BoxEntity;
import com.sokoban.entities.BrickEntity;
import com.sokoban.entities.CoolGuyEntity;
import com.sokoban.entities.FloorEntity;
import com.sokoban.entities.GirlEntity;
import com.sokoban.entities.GuyEntity;
import com.sokoban.entities.ImageButtonEntity;
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

import javax.swing.text.LabelView;

import static com.sokoban.Constants.CHANGE_VIEWPORT;
import static com.sokoban.Constants.PLANK_CONSTANT;
import static com.sokoban.Constants.REDO;
import static com.sokoban.Constants.RESTART;
import static com.sokoban.Constants.UNDO;

public class GameScreen extends BaseScreen implements GestureDetector.GestureListener {
    static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3, NULL = -1, THREESHOLD_VELOCITY = 50;
    final int SIZE = 140;
    SpriteBatch batch;
    Animation animation;
    float elapsedTime;
    IntAction intAction;
    GestureDetector gestureDetector;
    private Skin skin;
    private TextButton textButton;
    private Stage stage;
    private Stage stage2;
    private Stage  stage3;
    private Table table;
    private World world;
    private CoolGuyEntity coolGuyEntity;
    private ArrayList<BrickEntity> brickEntityList;
    private ArrayList<FloorEntity> floorEntityList;
    private ArrayList<BoxEntity> boxEntityList;
    private ArrayList<ReceptacleEntity> receptacleEntityList;
    //private Music music;
    private float tablaWidth;
    //private ArrayList<String[]> levels;
    private int indexLevel;
    private float minX, minY, maxX, maxY;
    private boolean viewPortMap;
    private boolean isOnPan;

    private Label labelMoves;


    public GameScreen(final MainGame mainGame) {
        super(mainGame);
        //mainGame.getLevelManager().loadProgress();
        isOnPan = false;
        indexLevel = 0;

        viewPortMap = true;
        TextureAtlas atlas = mainGame.getManager().get("skin3/pack.atlas", TextureAtlas.class);
        skin = new Skin(Gdx.files.internal("skin3/skin.json"), atlas);
        textButton = new TextButton("asno", skin);
        textButton.setSize(200, 200);
        textButton.setPosition(220, 250);
        stage = new Stage(new FitViewport(1920, 1080));


        stage2 = new Stage(new FitViewport(1920, 1080), stage.getBatch());
        stage3 = new Stage(new FitViewport(1920, 1080), stage.getBatch());
//        music = mainGame.getManager().get("music/Slider.ogg");
        intAction = new IntAction();
        intAction.setDuration(12);
        intAction.setStart(0);
        intAction.setEnd(12);
        intAction.setReverse(true);
        InputMultiplexer multiplexer = new InputMultiplexer();

        //levels=LevelsManage.getAllFiles("levels");

        String[] data = {"  BBB    ",
                "  BRB    ",
                "  B BBBB ",
                "BBBXGXRB ",
                "BR X  BBB ",
                "BBBBXB   ",
                "   BRB   ",
                "   BBB   "};
        //System.out.println(levels.get(2)[0]);

        //String[][] levels = LevelManager.getAllFiles();
        //System.out.println("File[0]"+files[1].name());
        //System.out.println("File[0]"+file.readString());
        //mainGame.getManager().get("levels/level_1.txt", FileHandle.class);
        //String[] level = LevelsManage.fromFileToStringArray((File));
        //for(int i=0; i<levels[0].length; i++)
        //    System.out.println(levels[0][i]);
        //Gdx.app.log("Current level", "" + mainGame.getLevelManager().getIndex());
        world = new World(mainGame.getLevelManager().getCurrentLevel());

        batch = new SpriteBatch();
        Texture[][] guyTextures = new Texture[2][4];

        //Texture floorTexture = mainGame.getManager().get("world/Floor.png");
        //Texture guyTexture = mainGame.getManager().get("guy/DOWN.png");
        //Texture brickTexture = mainGame.getManager().get("world/Brick.png");
        //Texture boxTexture = mainGame.getManager().get("world/Box.png");
        //Texture embonatedTexture = mainGame.getManager().get("world/Embonated.png");

        //Texture receptacleTexture = mainGame.getManager().get("world/Receptacle.png");


        //Texture[] textures = new Texture[4];
        //guyTextures[0][DOWN] = new Texture("guy/DOWN.png");
        //guyTextures[1][DOWN] = mainGame.getManager().get("guy/TOP_PUSH.png");
        //guyTextures[0][RIGHT] = mainGame.getManager().get("guy/RIGHT.png");
        //guyTextures[1][RIGHT] = mainGame.getManager().get("guy/RIGHT_PUSH.png");
        //guyTextures[0][UP] = mainGame.getManager().get("guy/DOWN.png");
        //guyTextures[1][UP] = mainGame.getManager().get("guy/DOWN_PUSH.png");
        //guyTextures[0][LEFT] = mainGame.getManager().get("guy/LEFT.png");
        //guyTextures[1][LEFT] = mainGame.getManager().get("guy/LEFT_PUSH.png");
        //Texture[] boxTextures = new Texture[2];
        //boxTextures[0] = boxTexture;
        //boxTextures[1] = embonatedTexture;

        //textures[0] = mainGame.getManager().get("world/Floor.png");
        //textures[1] = mainGame.getManager().get("guy/DOWN.png");
        //textures[2] = mainGame.getManager().get("world/Box.png");
        //animation = new Animation(1f / 30f, textures);
        //System.out.println(world.guy.x + "," + world.guy.y);
//        Texture girlTexture = mainGame.getManager().get("walk.png");
        coolGuyEntity = new CoolGuyEntity(world.guy);
        brickEntityList = new ArrayList<BrickEntity>();
        boxEntityList = new ArrayList<BoxEntity>();
        receptacleEntityList = new ArrayList<ReceptacleEntity>();
        floorEntityList = new ArrayList<FloorEntity>();

//        stage.addActor(table);
        //Gdx.input.setInputProcessor(stage);
        //stage.setKeyboardFocus(stage.getActors().first());

        for (Floor floor : this.world.groupFloor) {

            floorEntityList.add(new FloorEntity(new Floor(floor.x, floor.y, floor.isAlive), world));
        }


        for (final FloorEntity floorEntity : floorEntityList) {
            final Node target = new Node(floorEntity.floor.x, floorEntity.floor.y);
            floorEntity.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!coolGuyEntity.isOnWalking()) {
                        //floorEntity.floor(false);
                        PathFinding pathFinding = new PathFinding(world, target);
                        //pathFinding.print();
                        //pathFinding.getPath();
                        int path[] = pathFinding.getPath();
                        if (path[0] != NULL) {
                            SequenceAction animation = new SequenceAction();
                            coolGuyEntity.startWalk(NULL);
                            for (int i = 0; i < path.length; i++) {
                                //onAction(path[i]);
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
                                        labelMoves.setText("moves: "+world.getMoves());
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

                       // Gdx.app.log("path", "" + pathFinding.getPath()[0]);

                    }
                    //Gdx.app.log("BUBBLE", "touchdown");
                }

                ;
            });
            //floorEntity.addListener(new InputListener() {
            //    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            //        Gdx.app.log("BUBBLE", "touchdown");
            //        return true;  // must return true for touchUp event to occur
            //    }
            //
//
            //    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //        Gdx.app.log("BUBBLE", "touchup");
            //        if (!coolGuyEntity.isOnWalking()) {
            //            floorEntity.setIsAlive(false);
            //            PathFinding pathFinding = new PathFinding(world, target);
            //            //pathFinding.print();
            //            //pathFinding.getPath();
            //            int path[] = pathFinding.getPath();
            //            if (path[0] != NULL) {
            //                SequenceAction animation = new SequenceAction();
            //                coolGuyEntity.startWalk(NULL);
            //                for (int i = 0; i < path.length; i++) {
            //                    //onAction(path[i]);
            //                    final int action = path[i];
//
            //                    int amountX = 0;
            //                    int amountY = 0;
            //                    switch (action) {
            //                        case UP:
            //                            amountY = -PLANK_CONSTANT;
            //                            break;
            //                        case DOWN:
            //                            amountY = PLANK_CONSTANT;
            //                            break;
//
            //                        case LEFT:
            //                            amountX = -PLANK_CONSTANT;
            //                            break;
            //                        case RIGHT:
            //                            amountX = PLANK_CONSTANT;
            //                            break;
            //                    }
            //                    world.move(action);
            //                    animation.addAction(Actions.run(new Runnable() {
            //                        public void run() {
            //                            coolGuyEntity.setDirection(action);
            //                        }
            //                    }));
//
            //                    animation.addAction(Actions.moveBy(amountX, amountY, 0.2f));
//
            //                    animation.addAction(Actions.run(new Runnable() {
            //                        public void run() {
            //                            //world.move(action);
            //                        }
            //                    }));
//
            //                }
            //                animation.addAction(Actions.run(new Runnable() {
            //                    public void run() {
            //                        coolGuyEntity.stopWalk();
            //                        floorEntity.setIsAlive(true);
            //                    }
            //                }));
//
            //                coolGuyEntity.addAction(animation);
            //            }
//
            //            Gdx.app.log("path", "" + pathFinding.getPath()[0]);
//
            //        }
            //    }
            //});


            stage.addActor(floorEntity);
        }



        for (Receptacle receptacle : world.groupReceptacle)
            receptacleEntityList.add(new ReceptacleEntity(receptacle));

        for (final ReceptacleEntity receptacleEntity : receptacleEntityList) {
            //receptacleEntity.addAction(Actions.forever(Actions.sequence(Actions.fadeIn(0.5f),Actions.fadeOut(0.5f))));
            // Color TRANSPARENT = new Color(1f, 1f, 1f, .5f);
            // receptacleEntity.setColor(0,1,0,1);
            final Node target = new Node(receptacleEntity.receptacle.x, receptacleEntity.receptacle.y);
            receptacleEntity.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!coolGuyEntity.isOnWalking()) {
                        //  receptacleEntity.setIsAlive(false);
                        PathFinding pathFinding = new PathFinding(world, target);
                        //pathFinding.print();
                        //pathFinding.getPath();
                        int path[] = pathFinding.getPath();
                        if (path[0] != NULL) {
                            SequenceAction animation = new SequenceAction();
                            coolGuyEntity.startWalk(NULL);
                            for (int i = 0; i < path.length; i++) {
                                //onAction(path[i]);
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
                                    //receptacleEntity.setIsAlive(true);
                                }
                            }));

                            coolGuyEntity.addAction(animation);
                        }

                       // Gdx.app.log("path", "" + pathFinding.getPath()[0]);

                    }
                    //
                    // Gdx.app.log("BUBBLE", "touchdown");
                }

                ;
            });
            stage.addActor(receptacleEntity);
            // receptacleEntity.setColor(TRANSPARENT);
            // receptacleEntity.addAction(Actions.fadeIn(2f));
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

//        Window window = new Window("PAUSE", skin);
        //stage2.addActor(textButton);
        Table table = new Table(skin);
        table.setFillParent(true);
        table.left().top();

        //table.debug();

        TextButton undoButton = new TextButton("undo", skin);
        TextButton redoButton = new TextButton("redo", skin);
        TextButton restartButton = new TextButton("restart", skin);
        TextButton viewport = new TextButton("viewport", skin);

        Texture buttonOrange = new Texture(Gdx.files.internal("buttons2/orange.png"));
        Texture buttonBlue = new Texture(Gdx.files.internal("buttons2/blue.png"));
        Texture buttonPurple = new Texture(Gdx.files.internal("buttons2/purple.png"));

        final ImageButtonEntity imageButtonEntityUndo = new ImageButtonEntity(new Texture(Gdx.files.internal(
                "buttons2/undo.png")), buttonOrange, buttonBlue);
        final ImageButtonEntity imageButtonEntityRedo = new ImageButtonEntity(new Texture(Gdx.files.internal(
                "buttons2/redo.png")), buttonOrange, buttonBlue);
        final ImageButtonEntity imageButtonEntityRestart = new ImageButtonEntity(new Texture(Gdx.files.internal(
                "buttons2/restart.png")), buttonOrange, buttonBlue);

        final ImageButtonEntity imageButtonEntityConfig = new ImageButtonEntity(new Texture(Gdx.files.internal(
                "buttons2/config.png")), buttonPurple, buttonBlue);
        final ImageButtonEntity imageButtonEntityHome = new ImageButtonEntity(new Texture(Gdx.files.internal(
                "buttons2/home.png")), buttonPurple, buttonBlue);
        final ImageButtonEntity imageButtonEntityPath = new ImageButtonEntity(new Texture(Gdx.files.internal(
                "buttons2/path.png")), buttonPurple, buttonBlue);

        imageButtonEntityUndo.addListener(new ClickListener() {

            //@Override
            //public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //    imageButtonEntityUndo.setToEnable();
            //}

            @Override
            public void clicked(InputEvent event, float x, float y) {
                //super.clicked(event, x, y);
                onAction(UNDO);
            }

            //@Override
            //public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            //    imageButtonEntityUndo.setToDisable();
            //    return false;  // must return true for touchUp event to occur
            //}
        });


        imageButtonEntityRedo.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                imageButtonEntityRedo.setToDisable();
                return true;  // must return true for touchUp event to occur
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                imageButtonEntityRedo.setToEnable();
                onAction(REDO);
            }
        });

        imageButtonEntityRestart.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                imageButtonEntityRestart.setToDisable();
                return true;  // must return true for touchUp event to occur
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                imageButtonEntityRestart.setToEnable();
                onAction(RESTART);
            }
        });

        labelMoves=new Label("moves: 0",skin);


        imageButtonEntityConfig.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                imageButtonEntityConfig.setToDisable();
                return true;  // must return true for touchUp event to occur
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                imageButtonEntityConfig.setToEnable();
                onAction(NULL);
            }
        });

        imageButtonEntityPath.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                imageButtonEntityPath.setToDisable();
                return true;  // must return true for touchUp event to occur
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                imageButtonEntityPath.setToEnable();
                mainGame.setScreen(new StageScreen(mainGame));
            }
        });


        imageButtonEntityHome.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                imageButtonEntityHome.setToDisable();
                return true;  // must return true for touchUp event to occur
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                imageButtonEntityHome.setToEnable();
                mainGame.setScreen(new TitleScreen(mainGame));
            }
        });


        undoButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onAction(UNDO);
            }
        });

        redoButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onAction(REDO);
            }
        });

        restartButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onAction(RESTART);
            }
        });

        viewport.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onAction(CHANGE_VIEWPORT);
            }
        });
        //table.setDebug(true);
        table.add(imageButtonEntityUndo);
        table.add(imageButtonEntityRedo).row();
        table.add(imageButtonEntityRestart).colspan(2).row();
        table.add(labelMoves).colspan(2).row();
        table.add(viewport).colspan(2);
        Table table2 = new Table(skin);
        table2.left().bottom();
        table2.add(imageButtonEntityHome);
        table2.add(imageButtonEntityPath);
        //table2.add(imageButtonEntityConfig);

        //table.add(undoButton).left();
        //table.row();
        //table.add(redoButton).left();
        //table.row();
        //table.add(restartButton).left();


        stage2.addActor(table2);
        //undoButton.addAction(
        //        Actions.parallel(
        //                Actions.moveBy(-1500,0,.4f),
        //                Actions.fadeOut(.5f)
        //        )
        //);

        tablaWidth = table.getWidth();
        stage2.addActor(table);
        //stage.addActor(window);
        //stage.addListener(new ClickListener() {
        //    @Override
        //    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        //        System.out.println("asno2");
        //        event.handle();//the Stage will stop trying to handle this event
        //        return true; //the inputmultiplexer will stop trying to handle this touch
        //    }
//
        //    @Override
        //    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        //        System.out.println("draa");
        //    }
        //});
        gestureDetector = new GestureDetector(this);

        multiplexer.addProcessor(gestureDetector);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(stage2);

        Gdx.input.setInputProcessor(multiplexer);

        //stage.addListener(new GestureDetector.GestureListener(){
        //    @Override
        //    public boolean fling( float velocityX, float velocityY, int button) {
        //        if (Math.abs(velocityX) > Math.abs(velocityY))
        //            if (velocityX > 0)
        //                world.move(RIGHT);
        //            else
        //                world.move(LEFT);
        //        if (Math.abs(velocityX) < Math.abs(velocityY))
        //            if (velocityY > 0)
        //                world.move(UP);
        //            else
        //                world.move(DOWN);
        //        return true;
        //    }
        //});
        //Gdx.input.setInputProcessor(gestureDetector);
        setBounds();
        //((OrthographicCamera)stage.getCamera()).zoom += 0.5f;
       // System.out.println(((OrthographicCamera) stage.getCamera()).zoom);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.f, 1.f, 1.f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        //System.out.println(input.delta.x);
        // if(input.delta.x==0){
        //stage.getCamera().translate(guyEntity.getX() - stage.getCamera().position.x, guyEntity.getY() - stage.getCamera().position.y, 0);
        if (!viewPortMap)
            stage.getCamera().position.set(coolGuyEntity.getX(), coolGuyEntity.getY(), 0);
        // }
        elapsedTime += delta;
        batch.begin();
        // batch.draw((Texture) animation.getKeyFrame(elapsedTime,true),0,0);
        stage.draw();
        stage2.act();
        stage2.draw();
        stage3.act();
        stage3.draw();
        //  table.drawDebug(stage.getBatch());
        batch.end();
        //System.out.println(intAction.getValue());
        //if(world.win()) {
        //    if (indexLevel < levels.size() - 1) {
        //        indexLevel++;
        //        world = new World(levels.get(indexLevel));
//
        //    } else {
        //        indexLevel = 0;
        //        world = new World(levels.get(indexLevel));
        //    }
        //}


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
        //music.dispose();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        // System.out.println("down");

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        isOnPan = true;
        //Gdx.app.log("longPress", "longPress");
        return false;
    }

    @Override
    public void show() {
        super.show();
        //  music.play();
    }

    @Override
    public void hide() {
        super.hide();
        //music.stop();
        //mainGame.setScreen(new TitleScreen(mainGame));
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
        //if(Math.abs(velocityX)>THREESHOLD_VELOCITY && Math.abs(velocityY)<THREESHOLD_VELOCITY)
        //    if(velocityX>0)
        //        world.move(RIGHT);
        //    else
        //        world.move(LEFT);
        //if(Math.abs(velocityX)<THREESHOLD_VELOCITY && Math.abs(velocityY)>THREESHOLD_VELOCITY)
        //    if(velocityY>0)
        //        world.move(UP);
        //    else
        //        world.move(DOWN);
        if (!isOnPan) {
            //if (velocityX == 0 && velocityY == 0)
                //Gdx.app.log("Fling", "osNOfling");

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
            else {

                //if (velocityX == 0 && velocityY == 0)
                   // Gdx.app.log("Fling", "osNOfling");
                return false;
            }
            //Gdx.app.log("Fling", "fling");
        }

        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        // Gdx.app.log("Pan","pan in: "+deltaX+", "+deltaY);
        //if (!fling(0, 0, 1)) {
        //      Gdx.app.log("Pan","is pan");
        //}ç
        if (isOnPan) {
            Gdx.app.log("Pan", "is pan");
            if (Math.abs(deltaX) > Math.abs(deltaY))
                if (deltaX > 0)
                    onAction(RIGHT);
                else
                    onAction(LEFT);
            else if (Math.abs(deltaX) < Math.abs(deltaY))
                if (deltaY > 0)
                    onAction(UP);
                else
                    onAction(DOWN);
        }
        return false;

    }


    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        //Gdx.app.log("Pan", "pan out");
        isOnPan = false;

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
                //World refWorld = new World(world.);

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
                labelMoves.setText("moves: "+world.getMoves());

                if (index != -2)
                    coolGuyEntity.addAction(
                            Actions.sequence(
                                    Actions.run(new Runnable() {
                                        public void run() {
                                            coolGuyEntity.startWalk(action);
                                        }
                                    }),
                                    Actions.moveBy(amountX, amountY, 0.3f),//, Interpolation.swing),
                                    Actions.run(new Runnable() {
                                        public void run() {
                                            coolGuyEntity.stopWalk();
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
                                        }
                                    })
                            ));

            }
            if (action >= 10 && action < 20) {
                Reel reel = new Reel(world.guy, world.groupBox, world.groupReceptacle, world.groupBrick);
                switch (action) {
                    case UNDO:
                        world.undo();
                        labelMoves.setText("moves: "+world.getMoves());
                        break;
                    case REDO:
                        world.redo();
                        labelMoves.setText("moves: "+world.getMoves());
                        break;
                    case RESTART:
                        world.restart();
                        labelMoves.setText("moves: "+world.getMoves());
                        break;
                }
                coolGuyEntity.addAction(Actions.moveBy(world.guy.x * PLANK_CONSTANT - reel.guy.x * PLANK_CONSTANT, world.guy.y * PLANK_CONSTANT - reel.guy.y * PLANK_CONSTANT, 0.5f, Interpolation.swing));

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
            //System.out.println("YOU WIN");
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
            //table.addAction(Actions.scaleTo(10,10,1));

            stage3.addActor(table);
            Gdx.input.setInputProcessor(stage3);
            stage2.addAction(Actions.moveBy(-500,0,0.5f));
            //stage2.addActor(winButton);
            //stage2.getActors().get(1).addAction(
            //        Actions.parallel(
            //                Actions.moveBy(1500,0,.4f),
            //                Actions.fadeOut(.5f)
            //        )
            //);
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
        float deltaX = maxX - minY /*+ 80*/;
        float deltaY = maxY - minY /*+ 80*/;
        //System.out.println(deltaX + ", " + deltaY);
        float screenX = stage.getWidth();
        float screenY = stage.getHeight();
        //float zoom=Math.max(deltaX/screenX, deltaY/screenY);
        float zoom = Math.max(deltaX / (screenX-160*2), deltaY / screenY);
        ((OrthographicCamera) stage.getCamera()).zoom = zoom;
        //((OrthographicCamera) stage.getCamera()).position.set(0,0,0);
        //((OrthographicCamera) stage.getCamera()).position.set
        ((OrthographicCamera) stage.getCamera()).position.set(-160*zoom + deltaX / 2.f /*+ (PLANK_CONSTANT - 40)*/, deltaY / 2.f /*+ (PLANK_CONSTANT - 40)*/, 0);

    }


}

