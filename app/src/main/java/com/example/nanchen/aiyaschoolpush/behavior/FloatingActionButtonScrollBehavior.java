package com.example.nanchen.aiyaschoolpush.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.behavior
 * @date 2016/10/13  09:24
 */

public class FloatingActionButtonScrollBehavior extends FloatingActionButton.Behavior {

    public FloatingActionButtonScrollBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final
    FloatingActionButton child, final View directTargetChild, final View target, final int
                                               nestedScrollAxes) {
        // 确保是竖直判断的滚动
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll
                (coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final
    FloatingActionButton child, final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) { // 往下滑
            child.hide();
            if (mOnStateChangedListener != null){
                mOnStateChangedListener.onChanged(false);
            }
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.show();
            if (mOnStateChangedListener != null){
                mOnStateChangedListener.onChanged(true);
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

    public static <V extends View> FloatingActionButtonScrollBehavior from(V view){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)){
            throw new IllegalArgumentException("这个View不是CoodinatorLayout的子View");
        }
        Behavior behavior = ((LayoutParams)params).getBehavior();
        if (!(behavior instanceof FloatingActionButtonScrollBehavior)){
            throw new IllegalArgumentException("这个View的Behavior不是FloatingActionButtonScrollBehavior");
        }
        return (FloatingActionButtonScrollBehavior) behavior;
    }
}


