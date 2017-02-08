package com.example.nanchen.aiyaschoolpush.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.ui.view.SegmentView;
import com.example.nanchen.aiyaschoolpush.ui.view.SegmentView.onSegmentViewClickListener;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.fragment
 * @date 2016/10/08  08:57
 */

public class MsgFragment extends FragmentBase {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private SegmentView mSegmentView;
    private MyMsgFragment mMyMsgFragment;
    private ContactFragment mContactFragment;
    private ContactListFragment mContactListFragment;
    private FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg,container,false);
        bindView(view);
        return view;
    }

    private void bindView(View view) {
//        mTabLayout = (TabLayout) view.findViewById(R.id.msg_tabLayout);
//        mViewPager = (ViewPager) view.findViewById(R.id.msg_vp);
//
//        List<String> nameList = new ArrayList<>();
//        nameList.add("消息");
//        nameList.add("通讯录");
//
//        List<Fragment> list = new ArrayList<>();
//        list.add(new MyMsgFragment());
//        list.add(new ContactFragment());
//
//        mViewPager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager(),nameList,list));
//        mTabLayout.setupWithViewPager(mViewPager);



        // 消息页面采用仿QQ界面
        mSegmentView = (SegmentView) view.findViewById(R.id.msg_segmentView);
        
        fm = getActivity().getSupportFragmentManager();
        
        mMyMsgFragment = new MyMsgFragment();
        fm.beginTransaction().add(R.id.msg_content,mMyMsgFragment).commit();
        

        mSegmentView.setOnSegmentViewClickListener(new onSegmentViewClickListener() {
            @Override
            public void onSegmentViewClick(View view, int position) {
                hideFragment();
                switch (position){
                    case 0:
                        if (mMyMsgFragment == null){
                            fm.beginTransaction().add(R.id.msg_content,mMyMsgFragment).commit();
                            mMyMsgFragment = new MyMsgFragment();
                        }else {
                            fm.beginTransaction().show(mMyMsgFragment).commit();
                        }
                        break;
                    case 1:
//                        if (mContactFragment == null){
//                            mContactFragment = new ContactFragment();
//                            fm.beginTransaction().add(R.id.msg_content,mContactFragment).commit();
//                        }else {
//                            fm.beginTransaction().show(mContactFragment).commit();
//                        }
                        if (mContactListFragment == null){
                            mContactListFragment = new ContactListFragment();
                            fm.beginTransaction().add(R.id.msg_content,mContactListFragment).commit();
                        }else {
                            fm.beginTransaction().show(mContactListFragment).commit();
                        }
                        break;
                }
            }
        });
    }

    /**
     * 隐藏所有Fragment
     */
    private void hideFragment() {
        if (mMyMsgFragment != null){
            fm.beginTransaction().hide(mMyMsgFragment).commit();
        }
//        if (mContactFragment != null){
//            fm.beginTransaction().hide(mContactFragment).commit();
//        }
        if (mContactListFragment != null){
            fm.beginTransaction().hide(mContactListFragment).commit();
        }
    }

}
