package com.sokoban.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.IntAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sokoban.BaseScreen;
import com.sokoban.LevelManager;
import com.sokoban.LevelsManage;
import com.sokoban.MainGame;
import com.sokoban.entities.BoxEntity;
import com.sokoban.entities.BrickEntity;
import com.sokoban.entities.CoolGuyEntity;
import com.sokoban.entities.FloorEntity;
import com.sokoban.entities.GirlEntity;
import com.sokoban.entities.GuyEntity;
import com.sokoban.entities.ReceptacleEntity;
import com.sokoban.sokobanWorld.Box;
import com.sokoban.sokobanWorld.Brick;
import com.sokoban.sokobanWorld.Receptacle;
import com.sokoban.sokobanWorld.Reel;
import com.sokoban.sokobanWorld.World;

import java.io.File;
import java.util.ArrayList;

import static com.sokoban.Constants.CHANGE_VIEWPORT;
import static com.sokoban.Constants.PLANK_CONSTANT;
import static com.sokoban.Constants.REDO;
import static com.sokoban.Constants.RESTART;
import static com.sokoban.Constants.UNDO;
import static com.sokoban.Constants.VIEWPORT_MAP;

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
    private Table table;
    private World world;
    private GuyEntity guyEntity;
    private GirlEntity girlEntity;
    private CoolGuyEntity coolGuyEntity;
    private ArrayList<BrickEntity> brickEntityList;
    private ArrayList<FloorEntity> floorEntityList;
    private ArrayList<BoxEntity> boxEntityList;
    private ArrayList<ReceptacleEntity> receptacleEntityList;
    private Music music;
    //private ArrayList<String[]> levels;
    private int indexLevel;
    private float minX, minY, maxX, maxY;
    private boolean viewPortMap;


    public GameScreen(MainGame mainGame) {
        super(mainGame);
        indexLevel = 0;
        viewPortMap=true;
        TextureAtlas atlas = mainGame.getManager().get("skin3/pack.atlas", TextureAtlas.class);
        Skin skin = new Skin(Gdx.files.internal("skin3/skin.json"), atlas);
        textButton = new TextButton("asno", skin);
        textButton.setSize(200, 200);
        textButton.setPosition(220, 250);
        stage = new Stage(new FitViewport(1920, 1080));
        stage2 = new Stage(new FitViewport(1920, 1080), stage.getBatch());
        music = mainGame.getManager().get("music/Slider.ogg");
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

        String[][] levels = LevelManager.getAllFiles();
        //System.out.println("File[0]"+files[1].name());
        //System.out.println("File[0]"+file.readString());
                //mainGame.getManager().get("levels/level_1.txt", FileHandle.class);
        //String[] level = LevelsManage.fromFileToStringArray((File));
        for(int i=0; i<levels[0].length; i++)
            System.out.println(levels[0][i]);

        world = new World(levels[3]);

        batch = new SpriteBatch();
        Texture[][] guyTextures = new Texture[2][4];

        Texture floorTexture = mainGame.getManager().get("world/Floor.png");
        Texture guyTexture = mainGame.getManager().get("guy/DOWN.png");
        Texture brickTexture = mainGame.getManager().get("world/Brick.png");
        Texture boxTexture = mainGame.getManager().get("world/Box.png");
        Texture embonatedTexture = mainGame.getManager().get("world/Embonated.png");

        Texture receptacleTexture = mainGame.getManager().get("world/Receptacle.png");


        Texture[] textures = new Texture[4];
        guyTextures[0][DOWN] = new Texture("guy/DOWN.png");
        guyTextures[1][DOWN] = mainGame.getManager().get("guy/TOP_PUSH.png");
        guyTextures[0][RIGHT] = mainGame.getManager().get("guy/RIGHT.png");
        guyTextures[1][RIGHT] = mainGame.getManager().get("guy/RIGHT_PUSH.png");
        guyTextures[0][UP] = mainGame.getManager().get("guy/DOWN.png");
        guyTextures[1][UP] = mainGame.getManager().get("guy/DOWN_PUSH.png");
        guyTextures[0][LEFT] = mainGame.getManager().get("guy/LEFT.png");
        guyTextures[1][LEFT] = mainGame.getManager().get("guy/LEFT_PUSH.png");
        Texture[] boxTextures = new Texture[2];
        boxTextures[0] = boxTexture;
        boxTextures[1] = embonatedTexture;

        textures[0] = mainGame.getManager().get("world/Floor.png");
        textures[1] = mainGame.getManager().get("guy/DOWN.png");
        textures[2] = mainGame.getManager().get("world/Box.png");
        animation = new Animation(1f / 30f, textures);
        System.out.println(world.guy.x + "," + world.guy.y);
        guyEntity = new GuyEntity(world.guy, guyTextures);
        Texture girlTexture = mainGame.getManager().get("walk.png");
        girlEntity = new GirlEntity(world.guy, girlTexture);
        coolGuyEntity = new CoolGuyEntity(world.guy);
        brickEntityList = new ArrayList<BrickEntity>();
        boxEntityList = new ArrayList<BoxEntity>();
        receptacleEntityList = new ArrayList<ReceptacleEntity>();
        floorEntityList = new ArrayList<FloorEntity>();

//        stage.addActor(table);
        //Gdx.input.setInputProcessor(stage);
        //stage.setKeyboardFocus(stage.getActors().first());

        for (Brick floor : this.world.allFloors())
            floorEntityList.add(new FloorEntity(new Brick(floor.x, floor.y), floorTexture, world));

        for (FloorEntity floorEntity : floorEntityList) {
            stage.addActor(floorEntity);
        }

        stage.addActor(girlEntity);


        for (Receptacle receptacle : world.groupReceptacle)
            receptacleEntityList.add(new ReceptacleEntity(receptacle, receptacleTexture));

        for (ReceptacleEntity receptacleEntity : receptacleEntityList) {
            //receptacleEntity.addAction(Actions.forever(Actions.sequence(Actions.fadeIn(0.5f),Actions.fadeOut(0.5f))));
            // Color TRANSPARENT = new Color(1f, 1f, 1f, .5f);
            // receptacleEntity.setColor(0,1,0,1);
            stage.addActor(receptacleEntity);
            // receptacleEntity.setColor(TRANSPARENT);
            // receptacleEntity.addAction(Actions.fadeIn(2f));
        }

        stage.addActor(guyEntity);


        for (Brick brick : world.groupBrick)
            brickEntityList.add(new BrickEntity(brick, brickTexture));

        for (BrickEntity brickEntity : brickEntityList)
            stage.addActor(brickEntity);

        for (Box box : world.groupBox)
            boxEntityList.add(new BoxEntity(box, boxTextures));

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

        table.add(undoButton);
        table.row();
        table.add(redoButton);
        table.row();
        table.add(restartButton);
        table.row();
        table.add(viewport);
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

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(stage2);
        multiplexer.addProcessor(gestureDetector);

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
        System.out.println(((OrthographicCamera) stage.getCamera()).zoom);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.f, 1.f, 1.f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        girlEntity.setX(guyEntity.getX());
        stage.act(Gdx.graphics.getDeltaTime());
        //System.out.println(input.delta.x);
        // if(input.delta.x==0){
        //stage.getCamera().translate(guyEntity.getX() - stage.getCamera().position.x, guyEntity.getY() - stage.getCamera().position.y, 0);
        if(!viewPortMap)
            stage.getCamera().position.set(coolGuyEntity.getX(), coolGuyEntity.getY(), 0);
        // }
        elapsedTime += delta;
        batch.begin();
        // batch.draw((Texture) animation.getKeyFrame(elapsedTime,true),0,0);
        stage.draw();
        stage2.act();
        stage2.draw();
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
        stage.dispose();
        music.dispose();
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
        return false;
    }

    @Override
    public void show() {
        super.show();
        music.play();
    }

    @Override
    public void hide() {
        super.hide();
        music.stop();
    }


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height);
        stage2.getViewport().update(width, height);
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


        if (Math.abs(velocityX) > Math.abs(velocityY))
            if (velocityX > 0)
                onAction(RIGHT);
            else {
                onAction(LEFT);
            }
        if (Math.abs(velocityX) < Math.abs(velocityY))
            if (velocityY > 0)
                onAction(UP);
            else
                onAction(DOWN);

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

                coolGuyEntity.changeDirection(action);

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
                        break;
                    case REDO:
                        world.redo();
                        break;
                    case RESTART:
                        world.restart();
                        break;
                }
                coolGuyEntity.addAction(Actions.moveBy(world.guy.x * PLANK_CONSTANT - reel.guy.x * PLANK_CONSTANT, world.guy.y * PLANK_CONSTANT - reel.guy.y * PLANK_CONSTANT, 0.5f, Interpolation.swing));

                for (int i = 0; i < boxEntityList.size(); i++) {
                    boxEntityList.get(i).addAction(Actions.moveBy(world.groupBox.get(i).x * PLANK_CONSTANT - reel.groupBox.get(i).x * PLANK_CONSTANT, world.groupBox.get(i).y * PLANK_CONSTANT - reel.groupBox.get(i).y * PLANK_CONSTANT, 0.5f, Interpolation.swing));
                }
            }

        }
        if (action==CHANGE_VIEWPORT){
            viewPortMap=!viewPortMap;
            if(viewPortMap)
                setBounds();
            if(!viewPortMap)
                ((OrthographicCamera) stage.getCamera()).zoom = 1 ;
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
        System.out.println(deltaX + ", " + deltaY);
        float screenX = 1920;
        float screenY = 1080;
        //float zoom=Math.max(deltaX/screenX, deltaY/screenY);
        float zoom = Math.min(screenX / deltaX, screenY / deltaY);
        ((OrthographicCamera) stage.getCamera()).zoom = 1 / zoom;
        ((OrthographicCamera) stage.getCamera()).position.set(deltaX / 2 /*+ (PLANK_CONSTANT - 40)*/, deltaY / 2 /*+ (PLANK_CONSTANT - 40)*/, 0);
    }
}
