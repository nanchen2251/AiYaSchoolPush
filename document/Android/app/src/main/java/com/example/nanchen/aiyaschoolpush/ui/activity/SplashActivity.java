package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.utils.IntentUtil;


public class SplashActivity extends ActivityBase {

    private static final int GO_LOGIN = 0x123;
    private static final int GO_GUIDE = 0x124;
    private static final long DELAY_TIME = 3000;
    private static final String IS_FIRST_IN = "isFirstIn";
    private static final String GUIDE_FLAG = "guideWelcome";
    private boolean isFirstIn = false;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GO_GUIDE:
                    IntentUtil.newIntent(SplashActivity.this,GuideActivity.class);
                    //activity切换的淡入淡出效果
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    break;
                case GO_LOGIN:
                    IntentUtil.newIntent(SplashActivity.this,LoginActivity.class);
                    //activity切换的淡入淡出效果
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        ImageView imageView = (ImageView) findViewById(R.id.splash_image);
//        imageView.setImageBitmap(BitmapUtil.decodeSampledBitmapFromResource(getResources(),R.drawable.splash_3,imageView.getWidth(),imageView.getHeight()));

        SharedPreferences sp = getSharedPreferences(GUIDE_FLAG,MODE_PRIVATE);
        isFirstIn = sp.getBoolean(IS_FIRST_IN,true);
        if (isFirstIn){
            handler.sendEmptyMessageDelayed(GO_GUIDE,DELAY_TIME);
        }else {

            handler.sendEmptyMessageDelayed(GO_LOGIN,DELAY_TIME);
        }
    }
}
