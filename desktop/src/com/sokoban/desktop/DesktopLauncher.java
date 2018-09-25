package com.sokoban.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sokoban.AdHandler;
import com.sokoban.MainGame;

public class DesktopLauncher implements AdHandler{
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MainGame(new AdMobDesktop()), config);
	}

	@Override
	public void showAds(boolean show) {

	}

	@Override
	public float getHeightBanner() {
		return 0;
	}
}
