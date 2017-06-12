package com.example.nanchen.aiyaschoolpush.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nanchen.aiyaschoolpush.R;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.view
 * @date 2016/10/08  16:50
 */

public class SegmentView extends LinearLayout {
    private TextView tv_left;
    private TextView tv_right;
    private onSegmentViewClickListener segmentViewClickListener;

    /**
     * 这是代码加载UI必须重写的方法
     * @param context
     */
    public SegmentView(Context context) {
        super(context);
        initView();
    }

    /**
     * 这是在xml布局使用必须重写的方法
     * @param context
     * @param attrs
     */
    public SegmentView(Context context, AttributeSet attrs){
        super(context,attrs);
        initView();

    }
    private void initView() {
        tv_left = new TextView(getContext());
        tv_right = new TextView(getContext());

        //设置TextView的布局宽高并设置weight属性都为1
        tv_left.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1));;
        tv_right.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1));;

        //初始化默认文字
        tv_left.setText("消息");
        tv_right.setText("通讯录");

        //实现不同的按钮状态，不同的颜色
        ColorStateList csl = getResources().getColorStateList(R.color.segment_text_color_selector);
        tv_left.setTextColor(csl);
        tv_right.setTextColor(csl);

        //设置内容居中
        tv_left.setGravity(Gravity.CENTER);
        tv_right.setGravity(Gravity.CENTER);

        //设置TextView的内边距
        tv_left.setPadding(5,6,5,6);
        tv_right.setPadding(5,6,5,6);

        //设置文字大小
        setSegmentTextSize(22);

        //设置背景资源
        tv_left.setBackgroundResource(R.drawable.segment_left_background);
        tv_right.setBackgroundResource(R.drawable.segment_right_background);

        //默认左侧为选中状态
        tv_left.setSelected(true);

        //加入TextView
        this.removeAllViews();
        this.addView(tv_left);
        this.addView(tv_right);
        this.invalidate();

        tv_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_left.isSelected()){
                    return;
                }
                tv_left.setSelected(true);
                tv_right.setSelected(false);
                if(segmentViewClickListener!=null){
                    segmentViewClickListener.onSegmentViewClick(tv_left,0);
                }
            }
        });
        tv_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_right.isSelected()){
                    return;
                }
                tv_left.setSelected(false);
                tv_right.setSelected(true);
                if(segmentViewClickListener!=null){
                    segmentViewClickListener.onSegmentViewClick(tv_right,1);
                }
            }
        });
    }

    /**
     * 设置字体大小
     * @param dp
     */
    private void setSegmentTextSize(int dp){
        tv_left.setTextSize(TypedValue.COMPLEX_UNIT_DIP,dp);
        tv_right.setTextSize(TypedValue.COMPLEX_UNIT_DIP,dp);
    }


    /**
     * 定义一个接口用于接收点击事件
     */
    public interface onSegmentViewClickListener{
        public void onSegmentViewClick(View view,int position);
    }

    /**
     * 手动设置选中的状态
     *
     * @param i
     */
    public void setSelect(int i) {
        if (i == 0) {
            tv_left.setSelected(true);
            tv_right.setSelected(false);
        } else {
            tv_left.setSelected(false);
            tv_right.setSelected(true);
        }
    }

    public void setOnSegmentViewClickListener(
            onSegmentViewClickListener segmentViewClickListener) {
        this.segmentViewClickListener = segmentViewClickListener;
    }


    /**
     * 设置控件显示的文字
     *
     * @param text
     * @param position
     */
    public void setSegmentText(CharSequence text, int position) {
        if (position == 0) {
            tv_left.setText(text);
        }
        if (position == 1) {
            tv_right.setText(text);
        }
    }
}
