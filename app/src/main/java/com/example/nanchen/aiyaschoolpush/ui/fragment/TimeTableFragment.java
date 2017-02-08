package com.example.nanchen.aiyaschoolpush.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.baoyz.widget.PullRefreshLayout.OnRefreshListener;
import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.model.CourseDao;
import com.example.nanchen.aiyaschoolpush.model.CourseModel;
import com.example.nanchen.aiyaschoolpush.ui.view.CornerTextView;
import com.example.nanchen.aiyaschoolpush.utils.ColorUtils;
import com.example.nanchen.aiyaschoolpush.utils.UIUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.fragment
 * @date 2016/10/08  08:57
 */

public class TimeTableFragment extends FragmentBase {

    private LinearLayout weekNames;
    private LinearLayout sections;
    private List<LinearLayout> mWeekViews;
    private int itemHeight;
    private int maxSection = 12;
    private PullRefreshLayout mRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_table,container,false);
        bindView(view);
        itemHeight = getResources().getDimensionPixelSize(R.dimen.sectionHeight);
        initWeekNameView();
        initSectionView();
        initWeekCourseView();
        setRefreshListener();
        return view;
    }

    private void bindView(View view) {
        weekNames = (LinearLayout) view.findViewById(R.id.weekNames);
        sections = (LinearLayout) view.findViewById(R.id.sections);

        LinearLayout linearLayout1 = (LinearLayout) view.findViewById(R.id.weekPanel_1);
        LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.weekPanel_2);
        LinearLayout linearLayout3 = (LinearLayout) view.findViewById(R.id.weekPanel_3);
        LinearLayout linearLayout4 = (LinearLayout) view.findViewById(R.id.weekPanel_4);
        LinearLayout linearLayout5 = (LinearLayout) view.findViewById(R.id.weekPanel_5);
        LinearLayout linearLayout6 = (LinearLayout) view.findViewById(R.id.weekPanel_6);
        LinearLayout linearLayout7 = (LinearLayout) view.findViewById(R.id.weekPanel_7);

        mWeekViews = new ArrayList<>();
        mWeekViews.add(linearLayout1);
        mWeekViews.add(linearLayout2);
        mWeekViews.add(linearLayout3);
        mWeekViews.add(linearLayout4);
        mWeekViews.add(linearLayout5);
        mWeekViews.add(linearLayout6);
        mWeekViews.add(linearLayout7);

        mRefreshLayout = (PullRefreshLayout) view.findViewById(R.id.refresh_layout);
    }

    /**
     * 初始化课程表
     */
    private void initWeekCourseView() {
        for (int i = 0; i < mWeekViews.size(); i++) {
            initWeekPanel(mWeekViews.get(i), CourseDao.getCourseData()[i]);
        }
    }

    /**
     * 下拉刷新
     */
    private void setRefreshListener() {
        mRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_RING);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearChildView();
                initWeekCourseView();
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });

    }

    /**
     * 顶部周一到周日的布局
     **/
    private void initWeekNameView() {
        for (int i = 0; i < mWeekViews.size() + 1; i++) {
            TextView tvWeekName = new TextView(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
            if (i != 0) {
                lp.weight = 1;
                tvWeekName.setText("周" + intToZH(i));
                if (i == getWeekDay()) {
                    tvWeekName.setTextColor(Color.parseColor("#FF0000"));
                } else {
                    tvWeekName.setTextColor(Color.parseColor("#4A4A4A"));
                }
            } else {
                lp.weight = 0.8f;
                tvWeekName.setText(getMonth() + "月");
            }
            tvWeekName.setGravity(Gravity.CENTER_HORIZONTAL);
            tvWeekName.setLayoutParams(lp);
            weekNames.addView(tvWeekName);
        }
    }

    /**
     * 左边节次布局，设定每天最多12节课
     */
    private void initSectionView() {
        for (int i = 1; i <= maxSection; i++) {
            TextView tvSection = new TextView(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, getResources().getDimensionPixelSize(R.dimen.sectionHeight));
            lp.gravity = Gravity.CENTER;
            tvSection.setGravity(Gravity.CENTER);
            tvSection.setText(String.valueOf(i));
            tvSection.setLayoutParams(lp);
            sections.addView(tvSection);
        }
    }

    /**
     * 当前星期
     */
    public int getWeekDay() {
        int w = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        if (w <= 0) {
            w = 7;
        }
        return w;
    }

    /**
     * 当前月份
     */
    public int getMonth() {
        int w = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return w;
    }

    /**
     * 每次刷新前清除每个LinearLayout上的课程view
     */
    private void clearChildView() {
        for (int i = 0; i < mWeekViews.size(); i++) {
            if (mWeekViews.get(i) != null)
                if (mWeekViews.get(i).getChildCount() > 0)
                    mWeekViews.get(i).removeAllViews();
        }
    }


    public void initWeekPanel(LinearLayout ll, List<CourseModel> data) {

        if (ll == null || data == null || data.size() < 1)
            return;
        CourseModel firstCourse = data.get(0);
        for (int i = 0; i < data.size(); i++) {
            final CourseModel courseModel = data.get(i);

            if (courseModel.getSection() == 0 || courseModel.getSectionSpan() == 0)
                return;
            FrameLayout frameLayout = new FrameLayout(getActivity());

            CornerTextView tv = new CornerTextView(getActivity(),
                    ColorUtils.getCourseBgColor(courseModel.getCourseFlag()),
                    dip2px(getActivity(), 3));
            LinearLayout.LayoutParams frameLp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    itemHeight * courseModel.getSectionSpan());
            LinearLayout.LayoutParams tvLp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            if (i == 0) {
                frameLp.setMargins(0, (courseModel.getSection() - 1) * itemHeight, 0, 0);
            } else {
                frameLp.setMargins(0, (courseModel.getSection() - (firstCourse.getSection() + firstCourse.getSectionSpan())) * itemHeight, 0, 0);
            }
            tv.setLayoutParams(tvLp);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(12);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
            tv.setText(courseModel.getCourseName() + "\n @" + courseModel.getClassRoom());

            frameLayout.setLayoutParams(frameLp);
            frameLayout.addView(tv);
            frameLayout.setPadding(2, 2, 2, 2);
            ll.addView(frameLayout);
            firstCourse = courseModel;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showToast(courseModel.getCourseName());
                }
            });
        }
    }

    /**
     * Toast
     */
    private void showToast(String msg) {
        if (TextUtils.isEmpty(msg))
            return;
        UIUtil.showToast(msg);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 数字转换中文
     */
    public static String intToZH(int i) {
        String[] zh = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] unit = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十"};

        String str = "";
        StringBuffer sb = new StringBuffer(String.valueOf(i));
        sb = sb.reverse();
        int r = 0;
        int l = 0;
        for (int j = 0; j < sb.length(); j++) {
            r = Integer.valueOf(sb.substring(j, j + 1));
            if (j != 0)
                l = Integer.valueOf(sb.substring(j - 1, j));
            if (j == 0) {
                if (r != 0 || sb.length() == 1)
                    str = zh[r];
                continue;
            }
            if (j == 1 || j == 2 || j == 3 || j == 5 || j == 6 || j == 7 || j == 9) {
                if (r != 0)
                    str = zh[r] + unit[j] + str;
                else if (l != 0)
                    str = zh[r] + str;
                continue;
            }
            if (j == 4 || j == 8) {
                str = unit[j] + str;
                if ((l != 0 && r == 0) || r != 0)
                    str = zh[r] + str;
                continue;
            }
        }
        if (str.equals("七"))
            str = "日";
        return str;
    }



}
