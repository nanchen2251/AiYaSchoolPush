package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.adapter.GuidePagerAdapter;
import com.example.nanchen.aiyaschoolpush.utils.IntentUtil;

import java.util.ArrayList;
import java.util.List;


public class GuideActivity extends ActivityBase implements OnPageChangeListener{

    private static final String IS_FIRST_IN = "isFirstIn";
    private static final String GUIDE_FLAG = "guideWelcome";
    private List<View> mViews;
    private ViewPager mViewPager;
    private GuidePagerAdapter mAdapter;
    private int []dotsId = {R.id.guide_image1,R.id.guide_image2,R.id.guide_image3};
    private ImageView[] dotsView;
    private ImageView mBtnStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initViews();
        initDots();
    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mViews = new ArrayList<>();
        // 获取View对象
        View view1 = inflater.inflate(R.layout.layout_guide_background1,null);
        View view2 = inflater.inflate(R.layout.layout_guide_background2,null);
        View view3 = inflater.inflate(R.layout.layout_guide_background3,null);

        // view对象放在List<view>中
        mViews.add(view1);
        mViews.add(view2);
        mViews.add(view3);

        // list<View>放在适配器中
        mAdapter = new GuidePagerAdapter(this,mViews);

        // 获取viewPager，设置适配器
        mViewPager = (ViewPager) findViewById(R.id.guide_vp);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);

        mBtnStart = (ImageView) view3.findViewById(R.id.guide_btn_start);
        mBtnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences(GUIDE_FLAG,MODE_PRIVATE);
                sp.edit().putBoolean(IS_FIRST_IN,false).apply();// 设置为false，不再显示引导页

                IntentUtil.newIntent(GuideActivity.this,LoginActivity.class);
                finish();
            }
        });
    }

    private void initDots() {
        dotsView = new ImageView[mViews.size()];
        for (int i = 0; i < mViews.size(); i++) {
            dotsView[i] = (ImageView) findViewById(dotsId[i]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        for (int i = 0; i < dotsId.length; i++) {
            if (i == position){
                dotsView[i].setImageResource(R.drawable.full_holo);
            }else {
                dotsView[i].setImageResource(R.drawable.empty_holo);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
