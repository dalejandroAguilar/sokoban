package com.sokoban.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sokoban.MainGame;
import com.sokoban.utils.Graphics;
import com.sokoban.utils.Shape;


public class LevelScreen extends BaseScreen implements GestureDetector.GestureListener {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private Stage stage;

    public LevelScreen(MainGame mainGame) {
        super(mainGame);
        stage = new Stage(new FitViewport(1920, 1080));
        Shape shape = new Shape();
        stage.addActor(shape);
        //Gdx.gl.glLineWidth(10);
        stage.getCamera().position.set(0, 0, 0);
        Graphics graphics = shape.graphics();
        drawPath(graphics);
        shape.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //System.out.println("asno2");
                event.handle();//the Stage will stop trying to handle this event
                return true; //the inputmultiplexer will stop trying to handle this touch
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                //System.out.println("draa");
            }
        });

        GestureDetector gestureDetector = new GestureDetector(this);



        Gdx.input.setInputProcessor(gestureDetector);

    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        //((OrthographicCamera) stage.getCamera()).zoom = 2.5f;

    }

    @Override
    public void render(float delta) {
        //super.render(delta);
        Gdx.gl.glClearColor(0.f, 1.f, 1.f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.begin();
        //batch.end();
//
        //shapeRenderer.setColor(Color.BLACK);
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        //shapeRenderer.polyline(getPolyline());
        //shapeRenderer.end();
        stage.act(delta);
        stage.draw();
       // System.out.println(stage.getWidth());
    }

    private float[] getPolyline() {
        float[] vertices = new float[10];
        vertices[0] = 0;
        vertices[1] = 100;
        vertices[2] = 100;
        vertices[3] = 100;
        vertices[4] = 200;
        vertices[5] = 200;
        vertices[6] = 300;
        vertices[7] = 300;
        vertices[8] = 400;
        vertices[9] = 400;
        return vertices;
    }

    private void drawPath(Graphics graphics) {
        float screenWidth = stage.getWidth();
        float screenHeight = stage.getHeight();
        int padding = 20;
        float size = screenWidth / 8;
        int numberOfLevels = 6;

        graphics.setColor(Color.DARK_GRAY);
        graphics.lineWidth(5);
        graphics.beginLine();

        graphics.line(-screenWidth / 2 + padding, 0, -screenWidth / 2 + padding + size, 0);
        for (int i = 0; i < numberOfLevels - 1; i++) {
            switch (i % 4) {
                case 0:
                    graphics.line(-screenWidth / 2 + padding + size * (i + 1), 0, -screenWidth / 2 + padding + size * (i + 2), screenHeight / 2 - padding);
                    break;
                case 1:
                    graphics.line(-screenWidth / 2 + padding + size * (i + 1), screenHeight / 2 - padding, -screenWidth / 2 + padding + size * (i + 2), 0);
                    break;
                case 2:
                    graphics.line(-screenWidth / 2 + padding + size * (i + 1), 0, -screenWidth / 2 + padding + size * (i + 2), -screenHeight / 2 + padding);
                    break;
                case 3:
                    graphics.line(-screenWidth / 2 + padding + size * (i + 1), -screenHeight / 2 + padding, -screenWidth / 2 + padding + size * (i + 2), 0);
                    break;
            }
        }
        graphics.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height);
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
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        stage.getCamera().translate(-deltaX,0,0);
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return true;
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
}
