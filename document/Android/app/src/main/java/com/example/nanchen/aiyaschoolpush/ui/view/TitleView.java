package com.example.nanchen.aiyaschoolpush.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.utils.DisplayMetricsUtil;

/**
 * 标题栏控件
 * 
 * <code>
 * 基本用法：
	TitleView titleView;
	titleView.setTitle("标题");
 * </code>
 * 
 * <code>
 * 自定义视图XML写法：
	<TitleView>
		<TextView android:tag="left" android:text="我是左侧的自定义视图" />
		<TextView android:tag="center" android:text="我是中间的自定义视图" />
		<TextView android:tag="right" android:text="我是右侧的自定义视图" />
	</TitleView>
 * </code>
 * 
 * 注：不需要自定义视图TitleView的XML里无需写内容
 *
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.view
 * @date 2016/09/08  19:58
 * */
public class TitleView extends FrameLayout {
	public final static String CUSTOM_VIEW_TYPE_LEFT = "left";
	public final static String CUSTOM_VIEW_TYPE_CENTER = "center";
	public final static String CUSTOM_VIEW_TYPE_RIGHT = "right";
	Context mContext;
	LinearLayout rootLayout;
	FrameLayout mLeftParent;
	TextView mLeftText;
	FrameLayout mLeftCustomViewContainer;
	FrameLayout mCenterParent;
	public TextView mCenterText;
	FrameLayout mCenterCustomViewContainer;
	FrameLayout mRightParent;
	TextView mRightText;
	FrameLayout mRightCustomViewContainer;
	View mBottomLine;

	public TitleView(Context context) {
		this(context, null, 0);
	}

	public TitleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
	}

	/**
	 * 设置左侧按钮单击监听
	 * */
	public void setLeftButtonOnClickListener(OnClickListener l) {
		mLeftParent.setOnClickListener(l);
	}

	/**
	 * 设置左侧按钮文本，隐藏自定义视图
	 * */
	public void setLeftButtonText(CharSequence text) {
		showView(0, 0);
		mLeftText.setText(text);
	}

	/**
	 * 设置左侧按钮文本颜色
	 * */
	public void setLeftButtonTextColor(int color) {
		//mLeftText.setTextColor(color);
	}

	/**
	 * 设置左侧按钮字体大小
	 * */
	public void setLeftButtonTextSize(int size) {
		mLeftText.setTextSize(size);
	}

	/**
	 * 设置左侧按钮大小
	 * */
	public void setLeftButtonSize(int width, int height) {
		mLeftText.setWidth(width);
		mLeftText.setHeight(height);
	}

	/**
	 * 设置左侧图像，隐藏自定义视图
	 * */
	public void setLeftButtonImage(int resId, int width, int height) {
		showView(0, 0);
		mLeftText.setText("");
		mLeftText.setWidth(width);
		mLeftText.setWidth(height);
		mLeftText.setBackgroundResource(resId);
	}

	public void setLeftButtonImage(int resId) {
		setLeftButtonImage(resId, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	/**
	 * 设置左侧按钮为Finish按钮
	 * */
	public void setLeftButtonAsFinish(final Activity a) {
		showView(0, 0);
		mLeftText.setText("\ue60c");
		setLeftButtonOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				a.finish();
			}
		});
	}
	
	/**
	 * 设置左侧按钮为Finish白色按钮
	 * */
	public void setLeftButtonAsFinishWhite(final Activity a) {
		showView(0, 0);
		mLeftText.setText("\ue60c");
		mLeftText.setTextColor(a.getResources().getColor(R.color.white1));
		setLeftButtonOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				a.finish();
			}
		});
	}
	/**
	 * 设置左侧按钮为自定义视图
	 * */
	public void setLeftButtonAsCustomView(View v) {
		showView(0, 1);
		if (mLeftCustomViewContainer.getChildCount() > 0)
			mLeftCustomViewContainer.removeAllViews();
		mLeftCustomViewContainer.addView(v);
	}

	/**
	 * 设置标题文本，隐藏自定义VIEW
	 * */
	public void setTitle(CharSequence title) {
		showView(1, 0);
		mCenterText.setText(title);
	}

	/**
	 * 设置标题点击事件
	 */
	public void setTitleClickListenter(OnClickListener clickListener) {
		mCenterText.setOnClickListener(clickListener);
	}

	public void setTitle(int resId) {
		showView(1, 0);
		mCenterText.setText(resId);
	}

	/**
	 * 设置标题文本颜色
	 * */
	public void setTitleTextColor(int color) {
		//mCenterText.setTextColor(color);
		//mCenterText.setTextColor(color);
	}

	/**
	 * 设置标题文本字体大小
	 */
	public void setTitleTextSize(int fontSize) {
		mCenterText.setTextSize(fontSize);
	}

	/**
	 * 设置中间按钮为自定义视图
	 * */
	public void setCenterButtonAsCustomView(View v) {
		showView(1, 1);
		if (mCenterCustomViewContainer.getChildCount() > 0)
			mCenterCustomViewContainer.removeAllViews();
		mCenterCustomViewContainer.addView(v);
	}

	/**
	 * 设置右侧按钮单击监听
	 * */
	public void setRightButtonOnClickListener(OnClickListener l) {
		mRightParent.setOnClickListener(l);
	}

	/**
	 * 设置右侧按钮文本，隐藏自定义视图
	 * */
	public void setRightButtonText(CharSequence text) {
		showView(2, 0);
		mRightText.setText(text);
	}

	/**
	 * 设置右侧按钮文本颜色
	 * */
	public void setRightButtonTextColor(int color) {
		//mRightText.setTextColor(color);
	}

	/**
	 * 设置右侧按钮单击监听
	 * */
	public void setRightButtonTextVisibility(int visibility) {
		mRightParent.setVisibility(visibility);
	}

	/**
	 * 设置右侧按钮文本颜色
	 * */
	public void changeRightButtonTextColor(int color) {
		mRightText.setTextColor(color);
	}

	/**
	 * 设置右侧按钮背景
	 * */
	public void setRightButtonTextColorBg(Drawable drawable) {
		mRightText.setBackgroundDrawable(drawable);
	}
	
	/**
	 * 设置右侧按钮字体大小
	 * */
	public void setRightButtonTextSize(int size) {
		mRightText.setTextSize(size);
	}
	/**
	 * 修正右侧按钮 使用文字图标是的错位
	 * */
	public void setFixRightButtonPadingTop(){
		mRightText.setPadding(mRightText.getPaddingLeft(), DisplayMetricsUtil.dip2px(10), mRightText.getPaddingRight(), mRightText.getPaddingBottom());
	}

	/**
	 * 修正右侧按钮 使用文字图标是的错位
	 * */
	public void setFixRightButtonPadingTop(int dp){
		mRightText.setPadding(mRightText.getPaddingLeft(), DisplayMetricsUtil.dip2px(dp),mRightText.getPaddingRight(),mRightText.getPaddingBottom());
	}

	/**
	 * 设置右侧按钮大小
	 * */
	public void setRightButtonSize(int width, int height) {
		mRightText.setWidth(width);
		mRightText.setHeight(height);
	}
	public void setTitleColor(int color){
		rootLayout.setBackgroundColor(color);
	}
	/**
	 * 设置右侧图像，隐藏自定义视图
	 * */
	public void setRightButtonImage(int resId, int width, int height) {
		showView(2, 0);
		mRightText.setText("");
		mRightText.setWidth(width);
		mRightText.setHeight(height);
		mRightText.setBackgroundResource(resId);
	}

	public void setRightButtonImage(int resId) {
		setRightButtonImage(resId, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	/**
	 * 设置右侧按钮为自定义视图
	 * */
	public void setRightButtonAsCustomView(View v) {
		showView(2, 1);
		if (mRightCustomViewContainer.getChildCount() > 0)
			mRightCustomViewContainer.removeAllViews();
		mRightCustomViewContainer.addView(v);
	}
	public  void  setRootLayoutBackGround(int color){
		rootLayout.setBackgroundColor(color);
	}
	/**
	 * 设置底边线是否可见
	 * @param show
	 */
	public void showBottomLine(boolean show) {
		if (show) {
			mBottomLine.setVisibility(View.VISIBLE);
		} else {
			mBottomLine.setVisibility(View.GONE);
		}
	}

	/**
	 * 显示指定的组内的指定按钮（或文本），隐藏组内其它按钮
	 * 
	 * @param btnIndex
	 *            按钮或文本索引从0开始,共3个（左、中、右）
	 * @param btnType
	 *            按钮或文本类型（见组内定义）
	 * */
	private void showView(int btnIndex, int btnType) {
		switch (btnIndex) {
		case 0:
			mLeftText.setVisibility(btnType == 0 ? View.VISIBLE : View.GONE);
			mLeftCustomViewContainer.setVisibility(btnType == 1 ? View.VISIBLE : View.GONE);
			break;
		case 1:
			mCenterText.setVisibility(btnType == 0 ? View.VISIBLE : View.GONE);
			mCenterCustomViewContainer.setVisibility(btnType == 1 ? View.VISIBLE : View.GONE);
			break;
		case 2:
			mRightText.setVisibility(btnType == 0 ? View.VISIBLE : View.GONE);
			mRightCustomViewContainer.setVisibility(btnType == 1 ? View.VISIBLE : View.GONE);
			break;
		default:
			throw new IllegalArgumentException("btnIndex");
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		/* 检查是否包含自定义视图 */
		View leftCustomView = null;
		View centerCustomView = null;
		View rightCustomView = null;
		int childCount = getChildCount();
		for (int i = 0; i < childCount; ++i) {
			View cv = getChildAt(i);
			String typeTag = (String) cv.getTag();
			if (CUSTOM_VIEW_TYPE_LEFT.equals(typeTag))
				leftCustomView = cv;
			else if (CUSTOM_VIEW_TYPE_CENTER.equals(typeTag))
				centerCustomView = cv;
			else if (CUSTOM_VIEW_TYPE_RIGHT.equals(typeTag))
				rightCustomView = cv;
			else
				throw new InflateException("TitleView 不支持自定义视图类型：" + typeTag);
		}

		this.removeAllViews();

		/* 初始化父容器 */
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View root = inflater.inflate(R.layout.view_title_view, this, true);
		rootLayout=(LinearLayout) root.findViewById(R.id.root_layout);
		mLeftParent = (FrameLayout) root.findViewById(R.id.titleview_left_parent);
		mLeftText = (TextView) root.findViewById(R.id.titleview_left_text);
		mLeftText.setTextColor(getResources().getColor(R.color.white1));

		//mLeftText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		mLeftCustomViewContainer = (FrameLayout) root.findViewById(R.id.titleview_left_custom_view);
		mCenterParent = (FrameLayout) root.findViewById(R.id.titleview_center_parent);
		mCenterText = (TextView) root.findViewById(R.id.titleview_center_text);
		mCenterText.setTextColor(getResources().getColor(R.color.white1));

		mCenterCustomViewContainer = (FrameLayout) root.findViewById(R.id.titleview_center_custom_view);
		mRightParent = (FrameLayout) root.findViewById(R.id.titleview_right_parent);
		mRightText = (TextView) root.findViewById(R.id.titleview_right_text);
		mRightText.setTextColor(getResources().getColor(R.color.white1));

		mRightCustomViewContainer = (FrameLayout) root.findViewById(R.id.titleview_right_custom_view);
		mBottomLine = (View) root.findViewById(R.id.line);
		rootLayout.setBackgroundColor(mContext.getResources().getColor(R.color.main_bg_color1));
		if (leftCustomView != null)
			setLeftButtonAsCustomView(leftCustomView);
		if (centerCustomView != null)
			setCenterButtonAsCustomView(centerCustomView);
		if (rightCustomView != null)
			setRightButtonAsCustomView(rightCustomView);
	}
}
