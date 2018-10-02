package com.sokoban;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.sokoban.screens.GameScreen;
import com.sokoban.screens.TitleScreen;

public class MainGame extends Game {
	public GameScreen gameScreen;
    public TitleScreen titleScreen;
    public float resolution;
    //public LevelScreen levelScreen;
	private AssetManager manager;
	private LevelManager levelManager;
    public AdHandler handler;
    public AssetManager getManager() {
        return manager;
    }
    public LevelManager getLevelManager() {
        return levelManager;
    }
    public MainGame(AdHandler handler){
	    //TODO: aquí no va nada
        this.handler=handler;
	}

	@Override
	public void create () {
        manager=new AssetManager();
        manager.load("skin3/pack.atlas", TextureAtlas.class);
        manager.finishLoading();
        levelManager=new LevelManager();
        resolution= Gdx.graphics.getHeight();
        setScreen(new TitleScreen(this));
	}
}




