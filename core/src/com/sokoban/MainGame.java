package com.sokoban;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.sokoban.screens.GameScreen;
import com.sokoban.screens.LevelScreen;
import com.sokoban.screens.StageScreen;
import com.sokoban.screens.TitleScreen;

import java.util.logging.Handler;

public class MainGame extends Game {
	public GameScreen gameScreen;
    public TitleScreen titleScreen;
    //public LevelScreen levelScreen;
	private AssetManager manager;
	private LevelManager levelManager;

    public AssetManager getManager() {
        return manager;
    }
    public LevelManager getLevelManager() {
        return levelManager;
    }
    public MainGame(){
	    //TODO: aquí no va nada
        
	}

	@Override
	public void create () {

        manager=new AssetManager();
       //manager.load("guy/DOWN.png",Texture.class);
       //manager.load("guy/LEFT.png",Texture.class);
       //manager.load("guy/RIGHT.png",Texture.class);
       //manager.load("guy/TOP.png",Texture.class);
       //manager.load("guy/DOWN_PUSH.png",Texture.class);
       //manager.load("guy/LEFT_PUSH.png",Texture.class);
       //manager.load("guy/RIGHT_PUSH.png",Texture.class);
       //manager.load("guy/TOP_PUSH.png",Texture.class);
       //manager.load("world/Box.png",Texture.class);
       //manager.load("world/Embonated.png",Texture.class);
       //manager.load("world/Brick.png",Texture.class);
       //manager.load("world/Receptacle.png",Texture.class);
       //manager.load("world/Floor.png",Texture.class);
       //manager.load("world/Dead_Floor.png",Texture.class);
       //manager.load("music/Slider.ogg",Music.class);
       //manager.load("walk.png",Texture.class);
        manager.load("skin3/pack.atlas", TextureAtlas.class);
       // if (manager.containsAsset("levels/level_1.txt"))
//        manager.load("levels/level_1.txt", FileHandle.class);

        manager.finishLoading();

        levelManager=new LevelManager();

        //titleScreen =new TitleScreen(this);
        //levelScreen=new LevelScreen(this);
        //gameScreen = new GameScreen(this);
		setScreen(new TitleScreen(this));

	}


    //@Override
	//public void render () {
//
	//}
	//
	//@Override
	//public void dispose () {
//
	//}
}




