package com.example.nanchen.aiyaschoolpush.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 引导页Guide的适配器
 *
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.adapter
 * @date 2016/09/13  17:15
 */
public class GuidePagerAdapter extends PagerAdapter {
    private Context context;
    private List<View> views;

    public GuidePagerAdapter(Context context, List<View> views) {
        this.context = context;
        this.views = views;
    }

    @Override
    public int getCount() {
        return views == null ? 0 : views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }
}
