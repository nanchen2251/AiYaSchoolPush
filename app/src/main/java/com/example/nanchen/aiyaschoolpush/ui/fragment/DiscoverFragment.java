package com.example.nanchen.aiyaschoolpush.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.model.ActivityModel;
import com.example.nanchen.aiyaschoolpush.utils.ScreenUtil;
import com.example.nanchen.aiyaschoolpush.ui.view.LinearLayoutListItemView;
import com.example.nanchen.aiyaschoolpush.ui.view.TitleView;
import com.example.nanchen.aiyaschoolpush.ui.view.WavyLineView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.fragment
 * @date 2016/10/08  08:57
 */

public class DiscoverFragment extends FragmentBase implements OnSliderClickListener{

    private TitleView mTitleBar;
    private SliderLayout mSliderLayout;
    private List<ActivityModel> mActivityModels;
    private WavyLineView mWavyLine;
    private LinearLayoutListItemView mItemWeather;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover,container,false);
        bindView(view);
        loadSlider();
        return view;
    }

    private void loadSlider() {
        mActivityModels = new ArrayList<>();

        // 加载一些假数据
        loadSomeData();


        // 以下是发现模块的自动轮播
        mSliderLayout.setClickable(true);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);//设置在底部
        for (ActivityModel model : mActivityModels) {
            TextSliderView textSliderView = new TextSliderView(this.getActivity());
            textSliderView.description(model.getTitle());
            textSliderView.image(model.getId());
            textSliderView.setOnSliderClickListener(this);
            textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
            mSliderLayout.addSlider(textSliderView);
        }
        mSliderLayout.startAutoCycle();//开始自动滑动
    }

    private void loadSomeData() {
        mActivityModels.add(new ActivityModel(R.drawable.activity1,"大手牵小手，你我共成长，快乐每一天"));
        mActivityModels.add(new ActivityModel(R.drawable.activity2,"乐享童趣"));
        mActivityModels.add(new ActivityModel(R.drawable.activity3,"走出家门，用爱沟通"));
    }

    private void bindView(View view) {
        mTitleBar = (TitleView) view.findViewById(R.id.discover_titleBar);
        mTitleBar.setTitle("发现");


        mSliderLayout = (SliderLayout) view.findViewById(R.id.discover_slider);

        mWavyLine = (WavyLineView) view.findViewById(R.id.discover_wavyLine);

        int initStrokeWidth = 2;
        int initAmplitude = 10;
        float initPeriod = (float)(2 * Math.PI / 120);
        mWavyLine.setPeriod(initPeriod);
        mWavyLine.setAmplitude(initAmplitude);
        mWavyLine.setStrokeWidth(ScreenUtil.dp2px(initStrokeWidth));

        mItemWeather = (LinearLayoutListItemView) view.findViewById(R.id.discover_item_weather);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(),"你点击了参加一个活动",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSliderLayout.removeAllSliders();
        mSliderLayout = null;
    }
}
