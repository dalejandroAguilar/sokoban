package com.sokoban;
import android.content.Context;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.os.Handler;
import java.util.logging.LogRecord;

public class AdmobManager implements AdManager {
    private final int ADSHOW=1;
    private final int ADHIDE=0;
    private final String admobID;
    private final String TESTDEVICE ="ca-app-pub-5828894093809520~6065240668";
    public AdView adView=null;
    public RelativeLayout.LayoutParams adParams=null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADSHOW:
                adView.setVisibility(View.VISIBLE);
                break;
                case ADHIDE:
                adView.setVisibility(View.GONE);
                break;
            }
        }
    };

    public AdmobManager(String id){
        admobID=id;
    }

    public void init(Context context){
        adParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        adParams.addRule(RelativeLayout.ALIGN_BOTTOM,RelativeLayout.TRUE);
        adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(admobID);
        AdRequest.Builder requestBuilder = new AdRequest.Builder();
        requestBuilder.addTestDevice(TESTDEVICE);
        adView.loadAd(requestBuilder.build());
    }



    @Override
    public void show() {
        handler.sendEmptyMessage(ADSHOW);
    }

    @Override
    public void hide() {
        handler.sendEmptyMessage(ADHIDE);
    }
}
