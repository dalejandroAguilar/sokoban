package com.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sokoban.entities.BoxEntity;
import com.sokoban.entities.BrickEntity;
import com.sokoban.entities.FloorEntity;
import com.sokoban.entities.GuyEntity;
import com.sokoban.entities.ReceptacleEntity;
import com.sokoban.sokobanWorld.Box;
import com.sokoban.sokobanWorld.Brick;
import com.sokoban.sokobanWorld.Receptacle;
import com.sokoban.sokobanWorld.Reel;
import com.sokoban.sokobanWorld.World;

import java.util.ArrayList;

import static com.sokoban.Constants.*;


public class GameScreen extends BaseScreen implements GestureDetector.GestureListener {
    static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3, NULL = -1, THREESHOLD_VELOCITY = 50;
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
    private ArrayList<BrickEntity> brickEntityList;
    private ArrayList<FloorEntity> floorEntityList;
    private ArrayList<BoxEntity> boxEntityList;
    private ArrayList<ReceptacleEntity> receptacleEntityList;
    private Music music;

    public GameScreen(MainGame mainGame) {
        super(mainGame);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        textButton = new TextButton("asno", skin);
        textButton.setSize(200, 200);
        textButton.setPosition(220, 250);
        stage = new Stage(new FitViewport(640, 360));
        stage2 = new Stage(new FitViewport(640, 360), stage.getBatch());
        music = mainGame.getManager().get("music/Slider.ogg");
        intAction = new IntAction();
        intAction.setDuration(12);
        intAction.setStart(0);
        intAction.setEnd(12);
        intAction.setReverse(true);
        InputMultiplexer multiplexer = new InputMultiplexer();
        String[] data = {"  BBB    ",
                "  BRB    ",
                "  B BBBB ",
                "BBBXGXRB ",
                "BR X  BBB ",
                "BBBBXB   ",
                "   BRB   ",
                "   BBB   "};
        world = new World(data);
        batch = new SpriteBatch();
        Texture[][] guyTextures = new Texture[2][4];

        Texture floorTexture = mainGame.getManager().get("world/Floor.png");
        Texture guyTexture = mainGame.getManager().get("guy/DOWN.png");
        Texture brickTexture = mainGame.getManager().get("world/Brick.png");
        Texture boxTexture = mainGame.getManager().get("world/Box.png");
        Texture embonatedTexture = mainGame.getManager().get("world/Embonated.png");

        Texture receptacleTexture = mainGame.getManager().get("world/Receptacle.png");


        Texture[] textures = new Texture[4];
        guyTextures[0][DOWN] = mainGame.getManager().get("guy/TOP.png");
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


        for (Receptacle receptacle : world.groupReceptacle)
            receptacleEntityList.add(new ReceptacleEntity(receptacle, receptacleTexture));

        for (ReceptacleEntity receptacleEntity : receptacleEntityList) {
            //receptacleEntity.addAction(Actions.forever(Actions.sequence(Actions.fadeIn(0.5f),Actions.fadeOut(0.5f))));
            // Color TRANSPARENT = new Color(1f, 1f, 1f, .5f);
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

        Window window = new Window("PAUSE", skin);
        //stage2.addActor(textButton);
        Table table = new Table(skin);
        table.setFillParent(true);
        table.right();
        table.debug();

        TextButton undoButton = new TextButton("undo", skin);
        TextButton redoButton = new TextButton("redo", skin);
        TextButton restartButton = new TextButton("restart", skin);


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

        table.add(undoButton);
        table.row();
        Integer a;
        table.add(redoButton);
        table.row();
        table.add(restartButton);


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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.f, 1.f, 1.f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        //System.out.println(input.delta.x);
        // if(input.delta.x==0){
        stage.getCamera().translate(guyEntity.getX() - stage.getCamera().position.x, guyEntity.getY() - stage.getCamera().position.y, 0);
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

    }

    @Override
    public void dispose() {
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
            if (velocityX > 0) {
                int index = world.enhancedMove(RIGHT);
                if (index != -2)
                    guyEntity.addAction(Actions.parallel(Actions.moveBy(40, 0f, 0.3f)));


                if (index != -2 && index != -1)
                    boxEntityList.get(index).addAction(Actions.sequence(Actions.moveBy(40, 0f, 0.3f),//, Interpolation.swing),
                            Actions.run(new Runnable() {
                                public void run() {
                                    world.guy.push = false;
                                }
                            })));
            } else {
                int index = world.enhancedMove(LEFT);
                if (index != -2)
                    guyEntity.addAction(Actions.moveBy(-40, 0f, 0.3f));
                if (index != -2 && index != -1)
                    boxEntityList.get(index).addAction(Actions.sequence(Actions.run(new Runnable() {
                                public void run() {
                                    world.guy.push = true;
                                }
                            }), Actions.moveBy(-40, 0f, 0.3f),//, Interpolation.swing),
                            Actions.run(new Runnable() {
                                public void run() {
                                    world.guy.push = false;
                                }
                            })));
            }
        if (Math.abs(velocityX) < Math.abs(velocityY))
            if (velocityY > 0) {

                int index = world.enhancedMove(UP);
                if (index != -2)
                    guyEntity.addAction(Actions.moveBy(0, -40, 0.3f));
                if (index != -2 && index != -1)
                    boxEntityList.get(index).addAction(Actions.sequence(Actions.moveBy(0, -40f, 0.3f),//, Interpolation.swing),
                            Actions.run(new Runnable() {
                                public void run() {
                                    world.guy.push = false;
                                }
                            })));
            } else {
                int index = world.enhancedMove(DOWN);
                if (index != -2)
                    guyEntity.addAction(Actions.moveBy(0, 40, 0.3f));
                if (index != -2 && index != -1)
                    boxEntityList.get(index).addAction(Actions.sequence(Actions.moveBy(0, 40f, 0.3f),//, Interpolation.swing),
                            Actions.run(new Runnable() {
                                public void run() {
                                    world.guy.push = false;
                                }
                            })));
            }
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

    private void onAction(int action){
        Reel reel = new Reel(world.guy, world.groupBox, world.groupReceptacle,world.groupBrick);

        switch (action){
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
        guyEntity.addAction(Actions.moveBy(world.guy.x * 40 - reel.guy.x*40, world.guy.y * 40 - reel.guy.y* 40, 0.5f, Interpolation.swing));

        for (int i=0; i<boxEntityList.size();i++){
            boxEntityList.get(i).addAction(Actions.moveBy(world.groupBox.get(i).x * 40 - reel.groupBox.get(i).x * 40, world.groupBox.get(i).y * 40 -reel.groupBox.get(i).y * 40, 0.5f, Interpolation.swing));
        }
    }
}
