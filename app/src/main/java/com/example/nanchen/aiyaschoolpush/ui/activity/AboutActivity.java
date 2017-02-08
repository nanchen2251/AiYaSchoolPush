package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.os.Bundle;

import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.ui.view.TitleView;

import me.wangyuwei.particleview.ParticleView;

public class AboutActivity extends ActivityBase {

    private TitleView mTitleBar;
    private ParticleView mParticleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mTitleBar = (TitleView) findViewById(R.id.about_titleBar);
        mTitleBar.setTitle("关于我们");
        mTitleBar.setLeftButtonAsFinish(this);


//        mParticleView = (ParticleView) findViewById(R.id.about_particle);
//        mParticleView.startAnim();
    }
}
