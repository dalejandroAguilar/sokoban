package com.sokoban;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.sokoban.MainGame;

public class AndroidLauncher extends AndroidApplication {

	final AndroidLauncher context=this;
	final AdmobManager adMob;
	final MainGame mainGame;

	public AndroidLauncher() {
		adMob=new AdmobManager("ca-app-pub-5828894093809520/7263784026");
		mainGame=new MainGame(adMob);
	}

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adMob.init(context);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock =true;
		View gameView = initializeForView(mainGame,config);
		RelativeLayout layout= new RelativeLayout(this);
		layout.addView(gameView);
		layout.addView(adMob.adView, adMob.adParams);
		setContentView(layout);
		//initialize(new MainGame(), config);
	}
}
