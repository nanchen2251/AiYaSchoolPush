package com.example.nanchen.aiyaschoolpush.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.nanchen.aiyaschoolpush.AppService;
import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.adapter.MyPagerAdapter;
import com.example.nanchen.aiyaschoolpush.config.AddConfig;
import com.example.nanchen.aiyaschoolpush.config.Consts;
import com.example.nanchen.aiyaschoolpush.ui.activity.ReleaseActivity;
import com.example.nanchen.aiyaschoolpush.ui.view.TitleView;
import com.example.nanchen.aiyaschoolpush.utils.CircularAnimUtil;
import com.example.nanchen.aiyaschoolpush.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 *
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.fragment
 * @date 2016/09/30  14:31
 */

public class HomeFragment extends FragmentBase {

    private static final String TAG = "HomeFragment";
    private FloatingActionButton mFab;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TitleView mTitleBar;
    private String mName = AddConfig.NOTICE;

    private List<String> nameList;
    private List<Fragment> list;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (nameList != null){
            nameList.clear();
            nameList = null;
        }
        if (list != null){
            list.clear();
            list = null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        bindView(view);
        return view;
    }

    private void bindView(View view) {
        mTitleBar = (TitleView) view.findViewById(R.id.home_titleBar);
        mTitleBar.setTitle("首页");

        mTabLayout = (TabLayout) view.findViewById(R.id.home_tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.home_vp);

        mFab = (FloatingActionButton) view.findViewById(R.id.home_fab);

        if (AppService.getInstance().getCurrentUser() != null){
            int type = AppService.getInstance().getCurrentUser().type;
            Log.e(TAG, "bindView: type:"+type );
            if (type == Consts.USER_TYPE_TEACHER){
                mFab.setVisibility(View.VISIBLE);
            }
        }

        mFab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppService.getInstance().getCurrentUser().type != Consts.USER_TYPE_TEACHER){
                    UIUtil.showToast("非相关人员暂时不允许发公告作业！");
                    return;
                }

                Intent intent = new Intent(getActivity(), ReleaseActivity.class);
                intent.putExtra("name", mName);
                Log.e(TAG,mName);
                CircularAnimUtil.startActivity(getActivity(), intent, mFab,
                        R.color.main_bg_color1);
            }
        });

        nameList = new ArrayList<>();
        nameList.add("公告");
        nameList.add("作业");
        nameList.add("课表");
//        nameList.add("社区");

        list = new ArrayList<>();
        list.add(new NoticeFragment());
        list.add(new HomeworkFragment());
        list.add(new TimeTableFragment());
//        list.add(new CommunityFragment());

        mViewPager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager(),nameList,list));
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int type = AppService.getInstance().getCurrentUser().type;
                Log.e(TAG, "onPageSelected: type:"+ type);
                mFab.setVisibility(View.GONE);
                if (position == 0 && type == Consts.USER_TYPE_TEACHER){
                    mFab.setVisibility(View.VISIBLE);
                    mName = AddConfig.NOTICE;
                }
                if (position == 1 && type == Consts.USER_TYPE_TEACHER){
                    mFab.setVisibility(View.VISIBLE);
                    mName = AddConfig.HOMEWORK;
                }
                if (position == 2){
                    mFab.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        MyBehavior behavior = MyBehavior.from(mFab);
//        behavior.setOnStateChangedListener(mOnStateChangedListener);


    }

//    private OnStateChangedListener mOnStateChangedListener = new OnStateChangedListener() {
//        @Override
//        public void onChanged(boolean isShow) {
//            Log.e(TAG,isShow+"");
//            if (isShow){
//                mTitleBar.setVisibility(View.VISIBLE);
//            }else {
//                mTitleBar.setVisibility(View.GONE);
//            }
//        }
//    };
}
