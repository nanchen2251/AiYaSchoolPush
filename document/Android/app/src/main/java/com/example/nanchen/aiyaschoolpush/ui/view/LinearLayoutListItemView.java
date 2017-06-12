package com.example.nanchen.aiyaschoolpush.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nanchen.aiyaschoolpush.App;
import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.utils.ScreenUtil;


/**
 * 带有右箭头的菜单
 */
public class LinearLayoutListItemView extends LinearLayout implements OnClickListener {

	public static final int LIST_ITEM_STYLE_LISTITEM_TEXT = 0;
	public static final int LIST_ITEM_STYLE_LISTITEM_MENU = 1;
	
	public static final int LINE_SHOW_MODEL_NO_LINE = 0;//无线
	public static final int LINE_SHOW_MODEL_TOP_LINE = 1;//上有线
	public static final int LINE_SHOW_MODEL_BOTTOM_LINE = 2;//下有线
	public static final int LINE_SHOW_MODEL_TOP_AND_BOTTOM_LINE = 3;//上下都有线
	
	private static long mLong_LastClickTime;
	
	private RippleView mRV_Item;

	// 用户接受用户点击的LinearLayout
	private LinearLayout mLL_Item;
	private LinearLayout mView_Body;

	// 左侧文本
	private TextView mTv_LeftText;
	// 左侧文本2
	private TextView mTv_LeftText2;
    // 左侧文本角标
    private TextView mTv_LeftTextSuperscript;
	// 右侧文本
	private TextView mTv_RightText;
	// 右侧文本2
	private TextView mTv_RightText2;
    // 右侧文本3
    private TextView mTv_RightText3;
	// 底部文本
	private TextView mTv_BottomText;
	// 消息数字
	private TextView mTv_MessageNumber;
    // 新消息（无数字）
    private TextView mTv_NewMsg;

	//左侧文字图标
	private TextView mIv_LeftIconText;
	// 左侧图标
	private ImageView mIv_LeftIcon;
	// 右侧图标
	private ImageView mIv_RightIcon;
	// 右侧图标2
	private ImageView mIv_RightIcon2;
    // 右侧图标(字体文件)
    private IcomoonTextView mIv_RightIconText;
    
    private boolean mUseRightIconText = false;
	
	//上边线
	private LinearLayout mLL_TopLine_Container;
	private ImageView mView_TopLine;
	
	//下边线
	private LinearLayout mLL_BottomLine_Container;
	private ImageView mView_BottomLine;

	// ListItem样式
	private int mListItem_Style;
	// 上下边线显示模式
	private int mListItem_Line_Show_Model;

	private String icon_lefttext;
	private int mLeftIcon;
	private int mRightIcon;
	private int mRightIcon2;

	private String mLeftText;
	private String mLeftText2;
    private String mLeftTextSuperscript;
	private String mRightText;
	private String mRightText2;
    private String mRightText3;
	private String mBottomText;
	private String mMessageNumberText;
	
	private int mLeftTextFontSize;
	private int mLeftText2FontSize;
	private int mRightTextFontSize;
	private int mRightText2FontSize;
    private int mRightText3FontSize;
	private int mBottomTextFontSize;
	
	private int mLeftTextFontColor;
	private int mLeftText2FontColor;
	private int mRightTextFontColor;
	private int mRightText2FontColor;
    private int mRightText3FontColor;
	private int mBottomTextFontColor;
	
	private Drawable mRightTextBackground;
	
	private int mLeftText_MarginLeft =-1;	
	private int mLeftText_MarginRight =-1;
	private int mLeft2Text_MarginLeft =-1;
	private int mLeft2Text_MarginRight =-1;
	private int mRightText_MarginLeft = -1;
	private int mRightText_MarginRight = -1;
	
	//分割线宽度,默认为100%
	private int mList_Item_Top_Line_Width_Size = 100;
	private int mList_Item_Bottom_Line_Width_Size = 100;
	
	//分割线高度,默认为1px
	private int mList_Item_Top_Line_Height_Size = 1;
	private int mList_Item_Bottom_Line_Height_Size = 1;
	
	//Item高度
	private int mList_Item_Height = -1;
	//左侧头像尺寸
	private int mList_Item_Image_Size = -1;
	
	private int mWidth;
	
	private boolean mClickable = false;
	private boolean mLeft2TextSingleLine = false;
    private boolean mLeftTextSuperscriptShow = false;
	private boolean mRightTextSingleLine = false;

	private OnLinearLayoutListItemClickListener mOnLinearLayoutListItemClickListener;
	private OnClickListener mOnClickListener;
	
	private Object mObject;

	private Context mContext;

	//是否执行点击延迟操作
	private boolean nomalClickFlag = false;
	
	public LinearLayoutListItemView(Context context) {
		super(context);
		mContext = context;

	}

	public LinearLayoutListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		
		initView(context,attrs);
	}
	
	private void initView(Context context, AttributeSet attrs) {
		
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		mWidth = wm.getDefaultDisplay().getWidth();
		
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LinearLayoutListItemViewStyle);

		View viewRoot = LayoutInflater.from(mContext).inflate(R.layout.view_linearlayout_listitem, null);
		addView(viewRoot, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		mRV_Item =(RippleView) viewRoot.findViewById(R.id.mRV_Item);
		mLL_Item = (LinearLayout) viewRoot.findViewById(R.id.mLL_Item);
		mView_Body = (LinearLayout) viewRoot.findViewById(R.id.mView_Body);

		mIv_LeftIconText = (TextView) viewRoot.findViewById(R.id.mIv_LeftIconText);
		mIv_LeftIcon = (ImageView) viewRoot.findViewById(R.id.mIv_LeftIcon);
		mTv_LeftText = (TextView) viewRoot.findViewById(R.id.mTv_LeftText);
		mTv_LeftText2 = (TextView) viewRoot.findViewById(R.id.mTv_LeftText2);
        mTv_LeftTextSuperscript = (TextView) viewRoot.findViewById(R.id.mTv_LeftTextSuperscript);
		
		mIv_RightIcon = (ImageView) viewRoot.findViewById(R.id.mIv_RightIcon);
        mIv_RightIconText = (IcomoonTextView) viewRoot.findViewById(R.id.mIv_RightIconText);
		mIv_RightIcon2 = (ImageView) viewRoot.findViewById(R.id.mIv_RightIcon2);
		mTv_MessageNumber = (TextView) viewRoot.findViewById(R.id.mTv_MessageNumber);
        mTv_NewMsg = (TextView) viewRoot.findViewById(R.id.mTv_NewMsg);
		mTv_RightText = (TextView) viewRoot.findViewById(R.id.mTv_RightText);
		mTv_RightText2 = (TextView) viewRoot.findViewById(R.id.mTv_RightText2);
        mTv_RightText3 = (TextView) viewRoot.findViewById(R.id.mTv_RightText3);
		
		mTv_BottomText = (TextView) viewRoot.findViewById(R.id.mTv_BottomText);
		
		mLL_TopLine_Container = (LinearLayout)viewRoot.findViewById(R.id.mLL_TopLine_Container);
		mLL_BottomLine_Container = (LinearLayout)viewRoot.findViewById(R.id.mLL_BottomLine_Container);
		mView_TopLine = (ImageView)viewRoot.findViewById(R.id.mView_TopLine);
		mView_BottomLine = (ImageView)viewRoot.findViewById(R.id.mView_BottomLine);


		icon_lefttext = array.getString(R.styleable.LinearLayoutListItemViewStyle_icon_lefttext);
		mLeftIcon = array.getResourceId(R.styleable.LinearLayoutListItemViewStyle_icon_left, -1);
		mRightIcon = array.getResourceId(R.styleable.LinearLayoutListItemViewStyle_icon_right, -1);
		mRightIcon2 = array.getResourceId(R.styleable.LinearLayoutListItemViewStyle_icon_right, -1);
        mUseRightIconText = array.getBoolean(R.styleable.LinearLayoutListItemViewStyle_icon_text_right, false);
		
		mLeftText = array.getString(R.styleable.LinearLayoutListItemViewStyle_text_left);
		mLeftText2 = array.getString(R.styleable.LinearLayoutListItemViewStyle_text_left2);
        mLeftTextSuperscript = array.getString(R.styleable.LinearLayoutListItemViewStyle_text_left_superscript);
		mRightText = array.getString(R.styleable.LinearLayoutListItemViewStyle_text_right);
		mRightText2 = array.getString(R.styleable.LinearLayoutListItemViewStyle_text_right2);
        mRightText3 = array.getString(R.styleable.LinearLayoutListItemViewStyle_text_right3);
		mBottomText = array.getString(R.styleable.LinearLayoutListItemViewStyle_text_bottom);
		
		mLeftTextFontColor = array.getColor(R.styleable.LinearLayoutListItemViewStyle_text_left_fontcolor, -1);
		mLeftText2FontColor = array.getColor(R.styleable.LinearLayoutListItemViewStyle_text_left2_fontcolor, -1);
		mRightTextFontColor = array.getColor(R.styleable.LinearLayoutListItemViewStyle_text_right_fontcolor, -1);
		mRightText2FontColor = array.getColor(R.styleable.LinearLayoutListItemViewStyle_text_right2_fontcolor, -1);
        mRightText3FontColor = array.getColor(R.styleable.LinearLayoutListItemViewStyle_text_right3_fontcolor, -1);
		mBottomTextFontColor = array.getColor(R.styleable.LinearLayoutListItemViewStyle_text_bottom_fontcolor, -1);
		
		mRightTextBackground = array.getDrawable(R.styleable.LinearLayoutListItemViewStyle_text_right_background);
		
		mLeftTextFontSize = array.getInt(R.styleable.LinearLayoutListItemViewStyle_text_left_fontsize, -1);
		mLeftText2FontSize = array.getInt(R.styleable.LinearLayoutListItemViewStyle_text_left2_fontsize, -1);
		mRightTextFontSize = array.getInt(R.styleable.LinearLayoutListItemViewStyle_text_right_fontsize, -1);
		mRightText2FontSize = array.getInt(R.styleable.LinearLayoutListItemViewStyle_text_right2_fontsize, -1);
        mRightText3FontSize = array.getInt(R.styleable.LinearLayoutListItemViewStyle_text_right3_fontsize, -1);
		mBottomTextFontSize = array.getInt(R.styleable.LinearLayoutListItemViewStyle_text_bottom_fontsize, -1);
		
		mLeftText_MarginLeft = array.getInt(R.styleable.LinearLayoutListItemViewStyle_text_margin_left, -1);
		mLeftText_MarginRight = array.getInt(R.styleable.LinearLayoutListItemViewStyle_text_margin_left_right, -1);
		mLeft2Text_MarginLeft = array.getInt(R.styleable.LinearLayoutListItemViewStyle_text_margin_left2_left, -1);
		mLeft2Text_MarginRight = array.getInt(R.styleable.LinearLayoutListItemViewStyle_text_margin_left2_right, -1);
		mRightText_MarginLeft = array.getInt(R.styleable.LinearLayoutListItemViewStyle_text_margin_right_left, -1);
		mRightText_MarginRight = array.getInt(R.styleable.LinearLayoutListItemViewStyle_text_margin_right, -1);

		mClickable = array.getBoolean(R.styleable.LinearLayoutListItemViewStyle_clickable, false);
		mLeft2TextSingleLine = array.getBoolean(R.styleable.LinearLayoutListItemViewStyle_text_left2_single_line, false);
        mLeftTextSuperscriptShow = array.getBoolean(R.styleable.LinearLayoutListItemViewStyle_text_left_superscript_show, false);
		mRightTextSingleLine = array.getBoolean(R.styleable.LinearLayoutListItemViewStyle_text_right_single_line, false);
		
		mListItem_Style = array.getInt(R.styleable.LinearLayoutListItemViewStyle_list_item_style, 0);
		mListItem_Line_Show_Model = array.getInt(R.styleable.LinearLayoutListItemViewStyle_list_item_line_show_model, 0);
		
		mList_Item_Top_Line_Width_Size = array.getInt(R.styleable.LinearLayoutListItemViewStyle_list_item_top_line_width_size, 100);
		mList_Item_Bottom_Line_Width_Size = array.getInt(R.styleable.LinearLayoutListItemViewStyle_list_item_bottom_line_width_size, 100);
		
		mList_Item_Top_Line_Height_Size = array.getInt(R.styleable.LinearLayoutListItemViewStyle_list_item_top_line_height_size, 1);
		mList_Item_Bottom_Line_Height_Size = array.getInt(R.styleable.LinearLayoutListItemViewStyle_list_item_bottom_line_height_size, 1);
	
//		mList_Item_Height = DisplayMetrics.dip2px(mContext, array.getInt(R.styleable.LinearLayoutListItemViewStyle_list_item_height, -1));	
//		mList_Item_Image_Size = DisplayMetrics.dip2px(mContext, array.getInt(R.styleable.LinearLayoutListItemViewStyle_list_item_image_size, -1));
		
		mList_Item_Height = array.getInt(R.styleable.LinearLayoutListItemViewStyle_list_item_height, -1);	
		mList_Item_Image_Size = array.getInt(R.styleable.LinearLayoutListItemViewStyle_list_item_image_size, -1);
	
		if(mLL_Item != null){
			mLL_Item.setClickable(mClickable);
			mLL_Item.setFocusable(mClickable);
		}
		
		if(mRV_Item != null){
			mRV_Item.setClickable(mClickable);
			mRV_Item.setFocusable(mClickable);
		}
		
		if(mView_Body != null && mList_Item_Height != -1){
			LayoutParams params = (LayoutParams)mView_Body.getLayoutParams();
			params.height = ScreenUtil.dp2px(App.getAppContext(),mList_Item_Height);
			mView_Body .setLayoutParams(params);
		}
		
		if(mIv_LeftIcon != null && mList_Item_Image_Size != -1){
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mIv_LeftIcon.getLayoutParams();
			params.width = ScreenUtil.dp2px(App.getAppContext(),mList_Item_Image_Size);
			params.height = ScreenUtil.dp2px(App.getAppContext(),mList_Item_Image_Size);
			mIv_LeftIcon .setLayoutParams(params);
		}
		
		if(mTv_LeftText != null && mLeftTextFontColor != -1){
			mTv_LeftText.setTextColor(mLeftTextFontColor);
		}
		
		if(mTv_LeftText2 != null && mLeftText2FontColor != -1){
			mTv_LeftText2.setTextColor(mLeftText2FontColor);
		}
		
		if(mTv_RightText != null && mRightTextFontColor != -1){
			mTv_RightText.setTextColor(mRightTextFontColor);
		}
		
		if(mTv_RightText2 != null && mRightText2FontColor != -1){
			mTv_RightText2.setTextColor(mRightText2FontColor);
		}

        if(mTv_RightText3 != null && mRightText3FontColor != -1){
            mTv_RightText3.setTextColor(mRightText3FontColor);
        }
		
		if(mTv_BottomText != null && mBottomTextFontColor != -1){
			mTv_BottomText.setTextColor(mBottomTextFontColor);
		}
		
		setLineShowModel(mListItem_Line_Show_Model);
		setItemStyle(mListItem_Style);
	}
	
	/**
	 * 设置显示样式
	 * @param style
	 * 			参见：LIST_ITEM_STYLE_LISTITEM_TEXT | LIST_ITEM_STYLE_LISTITEM_MENU
	 */
	public void setItemStyle(int style) {
		mListItem_Style = style;
		
		switch (mListItem_Style) {
		case LIST_ITEM_STYLE_LISTITEM_TEXT:
			if(mLL_Item != null){
				mLL_Item.setClickable(false);
				mLL_Item.setFocusable(false);
			}
			
			if(mRV_Item != null){
				mRV_Item.setClickable(false);
				mRV_Item.setFocusable(false);
			}
			
			mIv_RightIcon.setVisibility(View.GONE);
            mIv_RightIconText.setVisibility(View.GONE);

			if (null!=icon_lefttext){
				mIv_LeftIconText.setVisibility(View.VISIBLE);
				mIv_LeftIconText.setText(icon_lefttext);
			}
			if (mLeftIcon != -1) {
				mIv_LeftIcon.setVisibility(View.VISIBLE);
				mIv_LeftIcon.setImageResource(mLeftIcon);
			}
			if (mRightIcon != -1) {
				mIv_RightIcon.setVisibility(View.VISIBLE);
                mIv_RightIconText.setVisibility(View.GONE);
				mIv_RightIcon.setImageResource(mRightIcon);
			}

			if (mTv_LeftText != null) {
				mTv_LeftText.setText(mLeftText);
				
				if(mLeftText_MarginLeft != -1){
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mTv_LeftText.getLayoutParams();
					params.setMargins(ScreenUtil.dp2px(App.getAppContext(),mLeftText_MarginLeft), params.topMargin, params.rightMargin, params.bottomMargin);
					mTv_LeftText .setLayoutParams(params);
				}
				if (mLeftText_MarginRight != -1) {
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mTv_LeftText.getLayoutParams();
					params.setMargins(params.leftMargin, params.topMargin, ScreenUtil.dp2px(App.getAppContext(),mLeftText_MarginRight), params.bottomMargin);
					mTv_LeftText .setLayoutParams(params);
				}
			}
			
			if (mTv_LeftText2 != null) {
				mTv_LeftText2.setText(mLeftText2);
				
				if (mLeft2Text_MarginLeft != -1) {
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mTv_LeftText2.getLayoutParams();
					params.setMargins(ScreenUtil.dp2px(App.getAppContext(),mLeft2Text_MarginLeft), params.topMargin, params.rightMargin, params.bottomMargin);
					mTv_LeftText2 .setLayoutParams(params);
				}
				if (mLeft2Text_MarginRight != -1) {
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mTv_LeftText2.getLayoutParams();
					params.setMargins(params.leftMargin, params.topMargin, ScreenUtil.dp2px(App.getAppContext(),mLeft2Text_MarginRight), params.bottomMargin);
					mTv_LeftText2 .setLayoutParams(params);
				}
				
				if (mLeft2TextSingleLine) {
					mTv_LeftText2.setSingleLine(true);
					mTv_LeftText2.setEllipsize(TruncateAt.END);
				}
			}
            
            if (mTv_LeftTextSuperscript != null) {
                mTv_LeftTextSuperscript.setText(mLeftTextSuperscript);
                if (mLeftTextSuperscriptShow) {
                    mTv_LeftTextSuperscript.setVisibility(View.VISIBLE);
                } else {
                    mTv_LeftTextSuperscript.setVisibility(View.GONE);
                }
            }

			if (mTv_RightText != null) {
				mTv_RightText.setText(mRightText);
				setRightTextFontSize(mRightTextFontSize);
				setRightTextFontColor(mRightTextFontColor);
				
				if(mOnClickListener != null){
					mTv_RightText.setOnClickListener(this);
				}
				
				if(mRightTextBackground != null){
					int intPadding1 = ScreenUtil.dp2px(App.getAppContext(),12);
					int intPadding2 = ScreenUtil.dp2px(App.getAppContext(),8);
					mTv_RightText.setPadding(intPadding1, intPadding2, intPadding1, intPadding2);
					mTv_RightText.setBackgroundDrawable(mRightTextBackground);
				}	
				
				if(mRightText_MarginRight != -1){
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mTv_RightText.getLayoutParams();
					params.setMargins(params.leftMargin, params.topMargin, ScreenUtil.dp2px(App.getAppContext(),mRightText_MarginRight), params.bottomMargin);
					mTv_RightText .setLayoutParams(params);
				}
				if (mRightText_MarginLeft != -1) {
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mTv_RightText.getLayoutParams();
					params.setMargins(ScreenUtil.dp2px(App.getAppContext(),mRightText_MarginLeft), params.topMargin, params.rightMargin, params.bottomMargin);
					mTv_RightText .setLayoutParams(params);
				}
				
				if (mRightTextSingleLine) {
					mTv_RightText.setSingleLine(true);
					mTv_RightText.setEllipsize(TruncateAt.END);
				}
			}
			
			if (mTv_RightText2 != null) {
				mTv_RightText2.setText(mRightText2);
				setRightText2FontSize(mRightText2FontSize);
				setRightText2FontColor(mRightText2FontColor);
			}

            if (mTv_RightText3 != null) {
                mTv_RightText3.setText(mRightText3);
                setRightText3FontSize(mRightText3FontSize);
                setRightText3FontColor(mRightText3FontColor);
            }
			
			if (mTv_BottomText != null) {
				setBottomText(mBottomText);
				setBottomTextFontSize(mBottomTextFontSize);
				setBottomTextFontColor(mBottomTextFontColor);
			}

			break;
			
		case LIST_ITEM_STYLE_LISTITEM_MENU:	
            if (mUseRightIconText) {
                mIv_RightIconText.setVisibility(View.VISIBLE);
                mIv_RightIcon.setVisibility(View.GONE);
            } else {
                mIv_RightIcon.setVisibility(View.VISIBLE);
                mIv_RightIconText.setVisibility(View.GONE);
            }

			if (null!=icon_lefttext){
				mIv_LeftIconText.setVisibility(View.VISIBLE);
				mIv_LeftIconText.setText(icon_lefttext);
			}

			if (mLeftIcon != -1) {
				mIv_LeftIcon.setVisibility(View.VISIBLE);
				mIv_LeftIcon.setImageResource(mLeftIcon);
			}
			if (mRightIcon != -1) {
				mIv_RightIcon.setVisibility(View.VISIBLE);
                mIv_RightIconText.setVisibility(View.GONE);
				mIv_RightIcon.setImageResource(mRightIcon);
			}

			if (mTv_LeftText != null) {
				mTv_LeftText.setText(mLeftText);
				
				if(mLeftText_MarginLeft != -1){
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mTv_LeftText.getLayoutParams();
					params.setMargins(ScreenUtil.dp2px(App.getAppContext(),mLeftText_MarginLeft), params.topMargin, params.rightMargin, params.bottomMargin);// 通过自定义坐标来放置你的控件
					mTv_LeftText.setLayoutParams(params);
				}
				if (mLeftText_MarginRight != -1) {
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mTv_LeftText.getLayoutParams();
					params.setMargins(params.leftMargin, params.topMargin, ScreenUtil.dp2px(App.getAppContext(),mLeftText_MarginRight), params.bottomMargin);
					mTv_LeftText.setLayoutParams(params);
				}
			}
			
			if (mTv_LeftText2 != null) {
				mTv_LeftText2.setText(mLeftText2);
				
				if (mLeft2Text_MarginLeft != -1) {
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mTv_LeftText2.getLayoutParams();
					params.setMargins(ScreenUtil.dp2px(App.getAppContext(),mLeft2Text_MarginLeft), params.topMargin, params.rightMargin, params.bottomMargin);
					mTv_LeftText2 .setLayoutParams(params);
				}
				if (mLeft2Text_MarginRight != -1) {
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mTv_LeftText2.getLayoutParams();
					params.setMargins(params.leftMargin, params.topMargin, ScreenUtil.dp2px(App.getAppContext(),mLeft2Text_MarginRight), params.bottomMargin);
					mTv_LeftText2 .setLayoutParams(params);
				}
				
				if (mLeft2TextSingleLine) {
					mTv_LeftText2.setSingleLine(true);
					mTv_LeftText2.setEllipsize(TruncateAt.END);
				}
			}

            if (mTv_LeftTextSuperscript != null) {
                mTv_LeftTextSuperscript.setText(mLeftTextSuperscript);
                if (mLeftTextSuperscriptShow) {
                    mTv_LeftTextSuperscript.setVisibility(View.VISIBLE);
                } else {
                    mTv_LeftTextSuperscript.setVisibility(View.GONE);
                }
            }

			if (mTv_RightText != null) {
				mTv_RightText.setText(mRightText);
				setRightTextFontSize(mRightTextFontSize);
				setRightTextFontColor(mRightTextFontColor);
				
				if(mRightTextBackground != null){
					int intPadding1 = ScreenUtil.dp2px(App.getAppContext(),12);
					int intPadding2 = ScreenUtil.dp2px(App.getAppContext(),8);
					mTv_RightText.setPadding(intPadding1, intPadding2, intPadding1, intPadding2);
					mTv_RightText.setBackgroundDrawable(mRightTextBackground);
				}

				if(mRightText_MarginRight != -1){
						RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mTv_RightText.getLayoutParams();
						params.setMargins(params.leftMargin, params.topMargin, ScreenUtil.dp2px(App.getAppContext(),mRightText_MarginRight), params.bottomMargin);// 通过自定义坐标来放置你的控件
						mTv_RightText .setLayoutParams(params);
				}
				
				if (mRightTextSingleLine) {
					mTv_RightText.setSingleLine(true);
					mTv_RightText.setEllipsize(TruncateAt.END);
				}
			}
			
			if (mTv_RightText2 != null) {
				mTv_RightText2.setText(mRightText2);
				setRightText2FontSize(mRightText2FontSize);
				setRightText2FontColor(mRightText2FontColor);
			}

            if (mTv_RightText3 != null) {
                mTv_RightText3.setText(mRightText3);
                setRightText3FontSize(mRightText3FontSize);
                setRightText3FontColor(mRightText3FontColor);
            }
			
			if (mTv_BottomText != null) {
				setBottomText(mBottomText);
				setBottomTextFontSize(mBottomTextFontSize);
				setBottomTextFontColor(mBottomTextFontColor);
			}

			if(mRV_Item != null){
				mRV_Item.setOnClickListener(this);
			}
			break;
		
		default:
			break;
		}
	}

	@Override
	public void onClick(final View v) {
		if (isNomalClickFlag()) {
			if (R.id.mRV_Item == v.getId() && mOnLinearLayoutListItemClickListener != null) {
				    	if(mListItem_Style == LIST_ITEM_STYLE_LISTITEM_MENU){
				    		mOnLinearLayoutListItemClickListener.onLinearLayoutListItemClick(mObject);
				    	}
			}
			if (R.id.mTv_RightText == v.getId() && mOnClickListener != null) {
				    	mOnClickListener.onClick(v);
			}
		}else{
			if(isFastClick()){
				return;
			}

			if (R.id.mRV_Item == v.getId() && mOnLinearLayoutListItemClickListener != null) {
				new Handler().postDelayed(new Runnable(){
				    public void run() {   
				    	if(mListItem_Style == LIST_ITEM_STYLE_LISTITEM_MENU){
				    		mOnLinearLayoutListItemClickListener.onLinearLayoutListItemClick(mObject);
				    	}
				    }   
				 }, 260);  	
			}
			
			if (R.id.mTv_RightText == v.getId() && mOnClickListener != null) {
				new Handler().postDelayed(new Runnable(){   
				    public void run() {   
				    	mOnClickListener.onClick(v);
				    }   
				 }, 260);  	
			}
		}
		
	}
	
	private synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - mLong_LastClickTime < 1000) {
            return true;
        }
        mLong_LastClickTime = time;
        return false;
    }


	public void setLeftIcon(int icon) {
		mLeftIcon = icon;
		mIv_LeftIcon.setImageResource(mLeftIcon);
	}
	
	public void setLeftIconSize(int size){
		if(mIv_LeftIcon != null && size != -1){
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mIv_LeftIcon.getLayoutParams();
			params.width = ScreenUtil.dp2px(App.getAppContext(),size);
			params.height = ScreenUtil.dp2px(App.getAppContext(),size);
			mIv_LeftIcon .setLayoutParams(params);
		}
	}
	
	public ImageView getLeftIconImageView(){
		if(mIv_LeftIcon != null){
			mIv_LeftIcon.setVisibility(View.VISIBLE);
			return mIv_LeftIcon;
		}else{
			return null;
		}
	}

	public void setRightIcon(int icon) {
		mRightIcon = icon;
		mIv_RightIcon.setImageResource(mRightIcon);
        mIv_RightIconText.setVisibility(View.GONE);
	}
	
	public void setRightIconVisiblity(Boolean value) {
		if(value){
			mIv_RightIcon.setVisibility(View.VISIBLE);
            mIv_RightIconText.setVisibility(View.GONE);
		}else{
			mIv_RightIcon.setVisibility(View.GONE);
		}
	}

    public void setRightIconTextVisiblity(Boolean value) {
        if(value){
            mIv_RightIconText.setVisibility(View.VISIBLE);
            mIv_RightIcon.setVisibility(View.GONE);
        }else{
            mIv_RightIconText.setVisibility(View.GONE);
        }
    }
	
	public void setRightIcon2(int icon) {
		if(mIv_RightIcon2 != null){
			mRightIcon2 = icon;
			mIv_RightIcon2.setVisibility(View.VISIBLE);
			mIv_RightIcon2.setImageResource(mRightIcon2);
		}
	}
	
	public void setRightIcon2(Bitmap bitmap) {
		if(mIv_RightIcon2 != null){
			mIv_RightIcon2.setVisibility(View.VISIBLE);
			mIv_RightIcon2.setImageBitmap(bitmap);
		}
	}
	
	public void setRightIcon2(Drawable drawable) {
		if(mIv_RightIcon2 != null){
			mIv_RightIcon2.setVisibility(View.VISIBLE);
			mIv_RightIcon2.setImageDrawable(drawable);
		}
	}
	
	public ImageView getRightIcon2ImageView(){
		if(mIv_RightIcon2 != null){
			mIv_RightIcon2.setVisibility(View.VISIBLE);
			return mIv_RightIcon2;
		}else{
			return null;
		}
	}
	
	public String getLeftText(){
		return mLeftText;
	}
	
	public TextView getLeftTextView() {
		return mTv_LeftText;
	}

	public void setLeftText(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            mLeftText = title.toString();
            mTv_LeftText.setText(title);
        }
	}
	
	public void setLeftTextMaxEms(int value){
		if(value != -1){
			mTv_LeftText.setMaxEms(value);
			mTv_LeftText.setEllipsize(TruncateAt.END);
		}
	}
	
	/**
	 * 设置 Ems ...
	 * @param value Ems值
	 * @param singleLine 是否折行
     * @param middle 省略号的位置（true: 中间; false: 最后） 
	 */
	public void setLeftTextMaxEms(int value, boolean singleLine, boolean middle){
		if(value != -1){
			mTv_LeftText.setMaxEms(value);
			mTv_LeftText.setSingleLine(singleLine);
            if (middle) {
                mTv_LeftText.setEllipsize(TruncateAt.MIDDLE);
            } else {
                mTv_LeftText.setEllipsize(TruncateAt.END);
            }
		}
	}

	public void setLeftTextFontSize(float size) {
//		if(mLeftTextFontSize != -1){
			mTv_LeftText.setTextSize(size);
//		}
	}
	
	public void setLeftTextFontSize(int unit, float size) {
//		if(mLeftTextFontSize != -1){
			mTv_LeftText.setTextSize(unit,size);
//		}
	}
	
	public String getLeftText2(){
		return mLeftText2;
	}
	
	public TextView getLeft2TextView() {
		return mTv_LeftText2;
	}
	
	public void setLeftText2(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            mLeftText2 = title.toString();
            mTv_LeftText2.setText(title);
        }
	}
	
	public void setLeftText2MaxEms(int value){
		if(value != -1){
			mTv_LeftText2.setMaxEms(value);
			mTv_LeftText2.setEllipsize(TruncateAt.END);
		}
	}
	
	/**
	 * 设置 Ems ...
	 * @param value Ems值
	 * @param singleLine 是否折行
	 */
	public void setLeftText2MaxEms(int value,boolean singleLine){
		if(value != -1){
			mTv_LeftText2.setMaxEms(value);
			mTv_LeftText2.setSingleLine(true);
			mTv_LeftText2.setEllipsize(TruncateAt.END);
		}
	}

	public void setLeftText2FontSize(float size) {
		if(mLeftText2FontSize != -1){
			mTv_LeftText2.setTextSize(size);
		}
	}
	
	public void showListItemTopLine(boolean show) {
		if(mLL_TopLine_Container != null){
			if(show){
				mLL_TopLine_Container.setVisibility(View.VISIBLE);
			}else{
				mLL_TopLine_Container.setVisibility(View.GONE);
			}
		}
	}
	
	public void showListItemBottomLine(boolean show) {
		if(mLL_BottomLine_Container != null){
			if(show){
				mLL_BottomLine_Container.setVisibility(View.VISIBLE);
			}else{
				mLL_BottomLine_Container.setVisibility(View.GONE);
			}
		}
	}
	
	public void setListItemTopLineWidthSize(int size) {
		if(mList_Item_Top_Line_Width_Size != -1 && size <= 100 && size >= 1){
			mList_Item_Top_Line_Width_Size = size;
		}
	}
	
	public void setListItemBottomLineWidthSize(int size) {
		if(mList_Item_Bottom_Line_Width_Size != -1 && size <= 100 && size >= 1){
			mList_Item_Bottom_Line_Width_Size = size;
		}
	}
	
	public void setListItemTopLineHeightSize(int size) {
		if(mList_Item_Top_Line_Height_Size != -1){
			//mTv_LeftText.setTextSize(size);
		}
	}
	
	public void setListItemBottomLineHeightSize(int size) {
		if(mList_Item_Bottom_Line_Height_Size != -1){
			//mTv_LeftText.setTextSize(size);
		}
	}

	public void setLeftTextFontColor(int color) {
		mTv_LeftText.setTextColor(color);
	}

	public String getRightText(){
		return mRightText;
	}
	
	public TextView getRightTextView() {
		return mTv_RightText;
	}
	
	public void setRightTextMaxEms(int value){
		if(value != -1){
			mTv_RightText.setMaxEms(value);
			mTv_RightText.setEllipsize(TruncateAt.END);
		}
	}
	
	/**
	 * 设置 Ems ...
	 * @param value Ems值
	 * @param singleLine 是否折行
	 */
	public void setRightTextMaxEms(int value,boolean singleLine){
		if(value != -1){
			mTv_RightText.setMaxEms(value);
			mTv_RightText.setSingleLine(true);
			mTv_RightText.setEllipsize(TruncateAt.END);
		}
	}
	
	public void setRightText(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            mRightText = title.toString();
            mTv_RightText.setText(title);
        }
	}

	public void setRightTextFontSize(float size) {
		if(mRightTextFontSize != -1){
			mTv_RightText.setTextSize(size);
		}
	}

	public void setRightTextFontColor(int color) {
		if(mRightTextFontSize != -1){
			mTv_RightText.setTextColor(color);
		}
	}
	
	public String getRightText2(){
		return mRightText2;
	}
	
	public TextView getRightText2View() {
		return mTv_RightText2;
	}
	
	public void setRightText2MaxEms(int value){
		if(value != -1){
			mTv_RightText2.setMaxEms(value);
			mTv_RightText2.setEllipsize(TruncateAt.END);
		}
	}
	
	/**
	 * 设置 Ems ...
	 * @param value Ems值
	 * @param singleLine 是否折行
	 */
	public void setRightText2MaxEms(int value,boolean singleLine){
		if(value != -1){
			mTv_RightText2.setMaxEms(value);
			mTv_RightText2.setSingleLine(true);
			mTv_RightText2.setEllipsize(TruncateAt.END);
		}
	}
	
	public void setRightText2(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            mRightText2 = title.toString();
            mTv_RightText2.setText(title);
        }
	}

	public void setRightText2FontSize(float size) {
		if(mRightText2FontSize != -1){
			mTv_RightText2.setTextSize(size);
		}
	}

	public void setRightText2FontColor(int color) {
		if(mRightText2FontSize != -1){
			mTv_RightText2.setTextColor(color);
		}
	}

    public String getRightText3(){
        return mRightText3;
    }

    public TextView getRightText3View() {
        return mTv_RightText3;
    }

    public void setRightText3MaxEms(int value){
        if(value != -1){
            mTv_RightText3.setMaxEms(value);
            mTv_RightText3.setEllipsize(TruncateAt.END);
        }
    }

    /**
     * 设置 Ems ...
     * @param value Ems值
     * @param singleLine 是否折行
     */
    public void setRightText3MaxEms(int value,boolean singleLine){
        if(value != -1){
            mTv_RightText3.setMaxEms(value);
            mTv_RightText3.setSingleLine(true);
            mTv_RightText3.setEllipsize(TruncateAt.END);
        }
    }

    public void setRightText3(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            mRightText3 = title.toString();
            mTv_RightText3.setText(title);
        }
    }

    public void setRightText3FontSize(float size) {
        if(mRightText3FontSize != -1){
            mTv_RightText3.setTextSize(size);
        }
    }

    public void setRightText3FontColor(int color) {
        if(mRightText3FontSize != -1){
            mTv_RightText3.setTextColor(color);
        }
    }
	
	public String getBottomText(){
		return mBottomText;
	}
	
	public TextView getBottomTextView() {
		return mTv_BottomText;
	}
	
	public void setBottomTextMaxEms(int value){
		if(value != -1){
			mTv_BottomText.setMaxEms(value);
			mTv_BottomText.setEllipsize(TruncateAt.END);
		}
	}
	
	/**
	 * 设置 Ems ...
	 * @param value Ems值
	 * @param singleLine 是否折行
	 */
	public void setBottomTextMaxEms(int value,boolean singleLine){
		if(value != -1){
			mTv_BottomText.setMaxEms(value);
			mTv_BottomText.setSingleLine(true);
			mTv_BottomText.setEllipsize(TruncateAt.END);
		}
	}
	public void setBottomText(CharSequence title) {
		if (title != null && !TextUtils.isEmpty(title)) {
			mTv_BottomText.setVisibility(View.VISIBLE);
			mBottomText = title.toString();
			mTv_BottomText.setText(title);
		} else {
			mTv_BottomText.setVisibility(View.GONE);
		}
	}
	
	public void setBottomTextFontSize(float size) {
		if (mBottomTextFontSize != -1) {
			mTv_BottomText.setTextSize(size);
		}
	}
	
	public void setBottomTextFontColor(int color) {
		if (mBottomTextFontColor != -1) {
			mTv_BottomText.setTextColor(color);
		}
	}
	
	public void setLeftText_MarginLeft(int value) {
		if (value != -1) {
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTv_LeftText.getLayoutParams();
			params.setMargins(ScreenUtil.dp2px(App.getAppContext(),value), params.topMargin, params.rightMargin, params.bottomMargin);// 通过自定义坐标来放置你的控件
			mTv_LeftText.setLayoutParams(params);
		}
	}

    public void setLeftText_MarginRight(int value) {
        if (value != -1) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTv_LeftText.getLayoutParams();
            params.setMargins(params.leftMargin, params.topMargin, ScreenUtil.dp2px(App.getAppContext(),value), params.bottomMargin);// 通过自定义坐标来放置你的控件
            mTv_LeftText.setLayoutParams(params);
        }
    }
    
	public void setLeftTextIcon(int drawable_res) {
		Drawable drawable = getResources().getDrawable(drawable_res);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		mTv_LeftText.setCompoundDrawablePadding(10);
		mTv_LeftText.setCompoundDrawables(drawable, null, null, null);
	}
	public int  getLeftText_MarginLeft(){
		return ((RelativeLayout.LayoutParams) mTv_LeftText.getLayoutParams()).leftMargin;
	}
	public void setRightText_MarginRight(int value) {
		if(value != -1){
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mTv_RightText.getLayoutParams();
			params.setMargins(params.leftMargin, params.topMargin, ScreenUtil.dp2px(App.getAppContext(),value), params.bottomMargin);// 通过自定义坐标来放置你的控件
			mTv_RightText .setLayoutParams(params);
		}
	}
	
	public void setObject(Object object){
		mObject = object;
	}
	
	public void setClickable(boolean value){
		mLL_Item.setClickable(value);
		mLL_Item.setFocusable(value);
	}
	
	public String getMessageNumberText(){
		return mMessageNumberText;
	}
	
	public void setMessageNumberText(CharSequence title) {
		mMessageNumberText = title.toString();
		mTv_MessageNumber.setVisibility(View.VISIBLE);
		mTv_MessageNumber.setText(title);
	}
	public void hideNewMessageNumber(){
		mTv_MessageNumber.setVisibility(View.GONE);
	}
    
    public void showNewMsg() {
        mTv_NewMsg.setVisibility(View.VISIBLE);
    }
    public void hideNewMsg() {
        mTv_NewMsg.setVisibility(View.GONE);
    }
    public boolean isNewMsgShowing() {
        return mTv_NewMsg.getVisibility() == View.VISIBLE;
    }
    
	public void setLineShowModel(int model) {
		mListItem_Line_Show_Model = model;
		switch(mListItem_Line_Show_Model){
			case LINE_SHOW_MODEL_NO_LINE:
				if(mLL_TopLine_Container != null && mView_TopLine != null){
					mLL_TopLine_Container.setVisibility(View.INVISIBLE);
				}
				
				if(mLL_BottomLine_Container != null && mView_TopLine != null){
					mLL_BottomLine_Container.setVisibility(View.INVISIBLE);
				}
				break;
			case LINE_SHOW_MODEL_TOP_LINE:
				if(mLL_TopLine_Container != null && mView_TopLine != null){
					mLL_TopLine_Container.setVisibility(View.VISIBLE);
				}
				if(mLL_BottomLine_Container != null && mView_TopLine != null){
					mLL_BottomLine_Container.setVisibility(View.INVISIBLE);
				}
				break;
			case LINE_SHOW_MODEL_BOTTOM_LINE:
				if(mLL_TopLine_Container != null && mView_TopLine != null){
					mLL_TopLine_Container.setVisibility(View.INVISIBLE);
				}
				if(mLL_BottomLine_Container != null && mView_BottomLine != null){
					mLL_BottomLine_Container.setVisibility(View.VISIBLE);
				}
				break;
			case LINE_SHOW_MODEL_TOP_AND_BOTTOM_LINE:
				if(mLL_TopLine_Container != null && mView_TopLine != null){
					mLL_TopLine_Container.setVisibility(View.VISIBLE);
				}
				if(mLL_BottomLine_Container != null && mView_BottomLine != null){
					mLL_BottomLine_Container.setVisibility(View.VISIBLE);
				}
				break;
			default:
				break;
		}
		
		if(mLL_TopLine_Container != null && mView_TopLine != null){
			mView_TopLine.setMinimumWidth((int) ((mWidth)* mList_Item_Top_Line_Width_Size / 100));
		}

		if(mLL_BottomLine_Container != null && mView_BottomLine != null){
			mView_BottomLine.setMinimumWidth((int) ((mWidth) * mList_Item_Bottom_Line_Width_Size / 100));
		}

	}
	
	public void setOnLinearLayoutListItemClickListener(OnLinearLayoutListItemClickListener onLinearLayoutListItemClickListener) {
		mOnLinearLayoutListItemClickListener = onLinearLayoutListItemClickListener;
	}
	
	public void setOnRightTextClickListener(OnClickListener onClickListener) {
		mOnClickListener = onClickListener;
	}

	public boolean isNomalClickFlag() {
		return nomalClickFlag;
	}

	public void setNomalClickFlag(boolean nomalClickFlag) {
		this.nomalClickFlag = nomalClickFlag;
	}
    
    public void setRightTextMaxLine(int maxLine) {
        if (null != mTv_RightText) {
            mTv_RightText.setSingleLine(false);
            mTv_RightText.setMaxLines(maxLine);
        }
    }
}