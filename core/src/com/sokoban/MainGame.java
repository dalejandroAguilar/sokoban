package com.sokoban;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.sokoban.screens.GameScreen;
import com.sokoban.screens.MainMenuScreen;

import java.io.File;
import java.io.InputStream;

public class MainGame extends Game {
	public GameScreen gameScreen;
    public MainMenuScreen mainMenuScreen;
	private AssetManager manager;

    public AssetManager getManager() {
        return manager;
    }

    public MainGame(){
	    //TODO: aquí no va nada
        
	}

	@Override
	public void create () {

        manager=new AssetManager();
        manager.load("guy/DOWN.png",Texture.class);
        manager.load("guy/LEFT.png",Texture.class);
        manager.load("guy/RIGHT.png",Texture.class);
        manager.load("guy/TOP.png",Texture.class);
        manager.load("guy/DOWN_PUSH.png",Texture.class);
        manager.load("guy/LEFT_PUSH.png",Texture.class);
        manager.load("guy/RIGHT_PUSH.png",Texture.class);
        manager.load("guy/TOP_PUSH.png",Texture.class);
        manager.load("world/Box.png",Texture.class);
        manager.load("world/Embonated.png",Texture.class);
        manager.load("world/Brick.png",Texture.class);
        manager.load("world/Receptacle.png",Texture.class);
        manager.load("world/Floor.png",Texture.class);
        manager.load("world/Dead_Floor.png",Texture.class);
        manager.load("music/Slider.ogg",Music.class);
        manager.load("walk.png",Texture.class);
        manager.load("skin3/pack.atlas", TextureAtlas.class);
       // if (manager.containsAsset("levels/level_1.txt"))
//        manager.load("levels/level_1.txt", FileHandle.class);

        manager.finishLoading();

        mainMenuScreen=new MainMenuScreen(this);
        //gameScreen = new GameScreen(this);
		setScreen(mainMenuScreen);
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




