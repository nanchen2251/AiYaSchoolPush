package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.os.Bundle;

import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.ui.view.TitleView;

public class ReleaseCommunityActivity extends ActivityBase {

    private TitleView mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_community);

        bindView();

    }

    private void bindView() {
        mTitleBar = (TitleView) findViewById(R.id.release_community_title);
        mTitleBar.setLeftButtonAsFinish(this);
        mTitleBar.setTitle("发布动态");
    }
}
