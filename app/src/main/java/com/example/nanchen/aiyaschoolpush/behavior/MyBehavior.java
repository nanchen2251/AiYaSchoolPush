package com.example.nanchen.aiyaschoolpush.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.behavior
 * @date 2016/10/13  09:23
 */

public class MyBehavior extends CoordinatorLayout.Behavior {
    //写了这个构造方法才能在XML文件中直接指定
    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;//返回true代表我们关心这个滚动事件
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        if (dy < 0) {//向下滚动
            ViewCompat.animate(child).scaleX(1).alpha(1).start();
            if (mOnStateChangedListener != null){
                mOnStateChangedListener.onChanged(true);
            }
        } else {//向上滚动
            ViewCompat.animate(child).scaleX(0).alpha(0).start();
            if (mOnStateChangedListener != null){
                mOnStateChangedListener.onChanged(false);
            }
        }
    }

    /**
     * 定义一个接口用于隐藏导航栏
     */
    public interface OnStateChangedListener{
        void onChanged(boolean isShow);
    }

    private OnStateChangedListener mOnStateChangedListener;

    public void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {
        mOnStateChangedListener = onStateChangedListener;
    }

    public static <V extends View> MyBehavior from(V view){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)){
            throw new IllegalArgumentException("这个View不是CoodinatorLayout的子View");
        }
        Behavior behavior = ((LayoutParams)params).getBehavior();
        if (!(behavior instanceof MyBehavior)){
            throw new IllegalArgumentException("这个View的Behavior不是MyBehavior");
        }
        return (MyBehavior) behavior;
    }
}
