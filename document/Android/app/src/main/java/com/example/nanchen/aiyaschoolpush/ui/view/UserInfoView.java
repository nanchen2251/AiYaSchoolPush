package com.example.nanchen.aiyaschoolpush.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class UserInfoView extends LinearLayout implements OnClickListener {
	private static final int MODEL_MY_SELF = 0; // 我
	private static final int MODEL_PERSONAL_INFO = 1; // 个人资料
	private static final int MODEL_MY_MEMORIES = 2; // 我的记忆
	private static final int MODEL_PERSONAL_INFO_LITE = 3; // 个人资料(他人)

	private static long mLong_LastClickTime;

	// ListItem样式
	private int mModel;

	private RelativeLayout mRL_Item;
	private RippleView mRV_Item;

	/** 模式1: 我 */
	// 姓名
	private TextView mTv_UserName;
	// 个姓签名
	private TextView mTv_UserSignature;
	// 头像
	private RoundImageView mRIV_HeadImage;
	// 右侧个人资料链接 RelativeLayout
	private RelativeLayout mRL_Personal_Info;
	// 个人资料 链接文字
	private TextView mTv_Personal_Info;
	private ImageView mTv_Vip;
    private IcomoonTextView mItv_Vip;
	private TextView mTv_Level;
	private TextView mTv_Favorite;
	private RelativeLayout mRL_Favorite;

	// 右侧个人资料链接 箭头
	private ImageView mIv_RightArrow;
	/** 模式1: 我 */

	private int mInt_HeadImage;
	private int mInt_RightArrow;

	private String mStr_UserName;
	private String mStr_UserSignature;
	private String mStr_Personal_Info;
	private String mStr_Level;
	private String mStr_Favorite;

	private int mUserNameFontSize;
	private int mUserSignatureFontSize;
	private int mPersonalInfoFontSize;
	private int mLevelFontSize;
	private int mFavoriteFontSize;

	private int mUserNameFontColor;
	private int mUserSignatureFontColor;
	private int mPersonalInfoFontColor;
	private int mLevelFontColor;
	private int mFavoriteFontColor;

	private int mWidth;

	private boolean mItemClickable = false;
	private boolean mHeadImageClickable = false;
	private boolean mPersonalInfoClickable = false;

	private Context mContext;

	public UserInfoView(Context context) {
		super(context);
		mContext = context;

	}

	public UserInfoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView(context, attrs);
	}

	@SuppressLint("NewApi")
	public UserInfoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initView(context, attrs);
	}

	@SuppressLint("NewApi")
	private void initView(Context context, AttributeSet attrs) {
		// if (isInEditMode())
		// return;

		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		mWidth = wm.getDefaultDisplay().getWidth();
		View viewRoot = null;
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.UserInfoViewStyle);
		mModel = array.getInt(R.styleable.UserInfoViewStyle_user_info_view_model, 0);

		switch (mModel) {
		case MODEL_MY_SELF:
			viewRoot = LayoutInflater.from(context).inflate(R.layout.view_user_info, null);
			addView(viewRoot, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			mRL_Item = (RelativeLayout) viewRoot.findViewById(R.id.mRL_Item);
			mRV_Item = (RippleView) viewRoot.findViewById(R.id.mRV_Item);
			mRIV_HeadImage = (RoundImageView) viewRoot.findViewById(R.id.mRIV_HeadImage);
			mTv_UserName = (TextView) viewRoot.findViewById(R.id.mTv_UserName);
			mTv_UserSignature = (TextView) viewRoot.findViewById(R.id.mTv_UserSignature);

			mTv_UserSignature.setMovementMethod(ScrollingMovementMethod.getInstance());

			mRL_Personal_Info = (RelativeLayout) viewRoot.findViewById(R.id.mRL_Personal_Info);
			mTv_Personal_Info = (TextView) viewRoot.findViewById(R.id.mTv_Personal_Info);
			mIv_RightArrow = (ImageView) viewRoot.findViewById(R.id.mIv_RightArrow);
			mTv_Vip = (ImageView) viewRoot.findViewById(R.id.mTv_Vip);
			mInt_HeadImage = array.getResourceId(R.styleable.UserInfoViewStyle_image_head_image, -1);
			mInt_RightArrow = array.getResourceId(R.styleable.UserInfoViewStyle_image_right_arrow, -1);

			mUserNameFontColor = array.getColor(R.styleable.UserInfoViewStyle_fontcolor_user_name, -1);
			mUserSignatureFontColor = array.getColor(R.styleable.UserInfoViewStyle_fontcolor_user_signature, -1);
			mPersonalInfoFontColor = array.getColor(R.styleable.UserInfoViewStyle_fontcolor_personal_info, -1);

			mUserNameFontSize = array.getInt(R.styleable.UserInfoViewStyle_fontsize_user_name, -1);
			mUserSignatureFontSize = array.getInt(R.styleable.UserInfoViewStyle_fontsize_user_signature_fontsize, -1);
			mPersonalInfoFontSize = array.getInt(R.styleable.UserInfoViewStyle_fontsize_personal_info, -1);

			mStr_UserName = array.getString(R.styleable.UserInfoViewStyle_text_user_name);
			mStr_UserSignature = array.getString(R.styleable.UserInfoViewStyle_text_user_signature);
			mStr_Personal_Info = array.getString(R.styleable.UserInfoViewStyle_text_personal_info);

			mItemClickable = array.getBoolean(R.styleable.UserInfoViewStyle_clickable_item, false);
			mHeadImageClickable = array.getBoolean(R.styleable.UserInfoViewStyle_clickable_head_image, false);
			mPersonalInfoClickable = array.getBoolean(R.styleable.UserInfoViewStyle_clickable_personal_info, false);

			if (mStr_UserName != null) {
				mTv_UserName.setText(mStr_UserName);
			}

			if (mStr_UserSignature != null) {
				mTv_UserSignature.setText(mStr_UserSignature);
			}

			if (mStr_Personal_Info != null) {
				mTv_Personal_Info.setText(mStr_Personal_Info);
			}

			// if(mRL_Item != null){
			// mRL_Item.setClickable(mItemClickable);
			// mRL_Item.setFocusable(mItemClickable);
			// mRL_Item.setOnClickListener(this);
			// }

			if (mRV_Item != null) {
				mRV_Item.setClickable(mItemClickable);
				mRV_Item.setFocusable(mItemClickable);
				mRV_Item.setOnClickListener(this);
			}

			if (mRIV_HeadImage != null) {
				if (mInt_HeadImage != -1) {
					mRIV_HeadImage.setImageResource(mInt_HeadImage);
				}

				if (mHeadImageClickable) {
					mRIV_HeadImage.setClickable(mHeadImageClickable);
					mRIV_HeadImage.setFocusable(mHeadImageClickable);
					mRIV_HeadImage.setOnClickListener(this);
					mRIV_HeadImage.setOnTouchListener(onTouchListener);
				}
			}

			if (mRL_Personal_Info != null && mPersonalInfoClickable) {
				mRL_Personal_Info.setClickable(mPersonalInfoClickable);
				mRL_Personal_Info.setFocusable(mPersonalInfoClickable);
				mRL_Personal_Info.setOnClickListener(this);
			}

			if (mIv_RightArrow != null) {
				mIv_RightArrow.setImageResource(mInt_RightArrow);
			}

			if (mTv_UserName != null && mUserNameFontColor != -1) {
				mTv_UserName.setTextColor(mUserNameFontColor);
			}

			if (mTv_UserSignature != null && mUserSignatureFontColor != -1) {
				mTv_UserSignature.setTextColor(mUserSignatureFontColor);
			}

			if (mTv_Personal_Info != null && mPersonalInfoFontColor != -1) {
				mTv_Personal_Info.setTextColor(mPersonalInfoFontColor);
			}

			break;
		case MODEL_PERSONAL_INFO:
			viewRoot = LayoutInflater.from(context).inflate(R.layout.view_user_info_personal_info_settings, null);
			addView(viewRoot, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
			mRL_Item = (RelativeLayout) viewRoot.findViewById(R.id.mRL_Item);
			mRV_Item = (RippleView) viewRoot.findViewById(R.id.mRV_Item);
			mRIV_HeadImage = (RoundImageView) viewRoot.findViewById(R.id.mRIV_HeadImage);
			mTv_UserName = (TextView) viewRoot.findViewById(R.id.mTv_UserName);
			
			mStr_UserName = array.getString(R.styleable.UserInfoViewStyle_text_user_name);
			mInt_HeadImage = array.getResourceId(R.styleable.UserInfoViewStyle_image_head_image, -1);

			mItemClickable = array.getBoolean(R.styleable.UserInfoViewStyle_clickable_item, false);
			mHeadImageClickable = array.getBoolean(R.styleable.UserInfoViewStyle_clickable_head_image, false);

			if (mStr_UserName != null) {
				mTv_UserName.setText(mStr_UserName);
			}

			if (mStr_UserSignature != null) {
				mTv_UserSignature.setText(mStr_UserSignature);
			}

			if (mStr_Personal_Info != null) {
				mTv_Personal_Info.setText(mStr_Personal_Info);
			}

			// if(mRL_Item != null){
			// mRL_Item.setClickable(mItemClickable);
			// mRL_Item.setFocusable(mItemClickable);
			// mRL_Item.setOnClickListener(this);
			// }

			if (mRV_Item != null) {
				mRV_Item.setClickable(mItemClickable);
				mRV_Item.setFocusable(mItemClickable);
				mRV_Item.setOnClickListener(this);
			}

			if (mRIV_HeadImage != null) {
				if (mInt_HeadImage != -1) {
					mRIV_HeadImage.setImageResource(mInt_HeadImage);
				}

				if (mHeadImageClickable) {
					mRIV_HeadImage.setClickable(mHeadImageClickable);
					mRIV_HeadImage.setFocusable(mHeadImageClickable);
					mRIV_HeadImage.setOnClickListener(this);
					mRIV_HeadImage.setOnTouchListener(onTouchListener);
				}
			}

			break;
		case MODEL_MY_MEMORIES:
			viewRoot = LayoutInflater.from(context).inflate(R.layout.view_user_info_my_memories, null);
			addView(viewRoot, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			mRL_Item = (RelativeLayout) viewRoot.findViewById(R.id.mRL_Item);
			mRV_Item = (RippleView) viewRoot.findViewById(R.id.mRV_Item);
			mRIV_HeadImage = (RoundImageView) viewRoot.findViewById(R.id.mRIV_HeadImage);
			mTv_UserName = (TextView) viewRoot.findViewById(R.id.mTv_UserName);
			mTv_UserSignature = (TextView) viewRoot.findViewById(R.id.mTv_UserSignature);
			mTv_Level = (TextView) viewRoot.findViewById(R.id.mTv_Level);
            mItv_Vip = (IcomoonTextView) viewRoot.findViewById(R.id.mItv_Vip);
			mRL_Favorite = (RelativeLayout) viewRoot.findViewById(R.id.mRL_Favorite);
			mTv_Favorite = (TextView) viewRoot.findViewById(R.id.mTv_Favorite);

			mInt_HeadImage = array.getResourceId(R.styleable.UserInfoViewStyle_image_head_image, -1);

			mUserNameFontColor = array.getColor(R.styleable.UserInfoViewStyle_fontcolor_user_name, -1);
			mUserSignatureFontColor = array.getColor(R.styleable.UserInfoViewStyle_fontcolor_user_signature, -1);
			mLevelFontColor = array.getColor(R.styleable.UserInfoViewStyle_fontcolor_user_signature, -1);
			mFavoriteFontColor = array.getColor(R.styleable.UserInfoViewStyle_fontcolor_user_signature, -1);

			mUserNameFontSize = array.getInt(R.styleable.UserInfoViewStyle_fontsize_user_name, -1);
			mUserSignatureFontSize = array.getInt(R.styleable.UserInfoViewStyle_fontsize_user_signature_fontsize, -1);
			mLevelFontSize = array.getInt(R.styleable.UserInfoViewStyle_fontsize_level, -1);
			mFavoriteFontSize = array.getInt(R.styleable.UserInfoViewStyle_fontsize_favorite, -1);

			mStr_UserName = array.getString(R.styleable.UserInfoViewStyle_text_user_name);
			mStr_UserSignature = array.getString(R.styleable.UserInfoViewStyle_text_user_signature);
			mStr_Level = array.getString(R.styleable.UserInfoViewStyle_text_level);
			mStr_Favorite = array.getString(R.styleable.UserInfoViewStyle_text_favorite);

			mItemClickable = array.getBoolean(R.styleable.UserInfoViewStyle_clickable_item, false);
			mHeadImageClickable = array.getBoolean(R.styleable.UserInfoViewStyle_clickable_head_image, false);

			if (mStr_UserName != null) {
				mTv_UserName.setText(mStr_UserName);
			}

			if (mStr_UserSignature != null) {
				mTv_UserSignature.setText(mStr_UserSignature);
			}

			// if(mRL_Item != null){
			// mRL_Item.setClickable(mItemClickable);
			// mRL_Item.setFocusable(mItemClickable);
			// mRL_Item.setOnClickListener(this);
			// }

			if (mRV_Item != null) {
				mRV_Item.setClickable(mItemClickable);
				mRV_Item.setFocusable(mItemClickable);
				mRV_Item.setOnClickListener(this);
			}

			if (mRIV_HeadImage != null) {
				if (mInt_HeadImage != -1) {
					mRIV_HeadImage.setImageResource(mInt_HeadImage);
				}

				if (mHeadImageClickable) {
					mRIV_HeadImage.setClickable(mHeadImageClickable);
					mRIV_HeadImage.setFocusable(mHeadImageClickable);
					mRIV_HeadImage.setOnClickListener(this);
					mRIV_HeadImage.setOnTouchListener(onTouchListener);
				}
			}

			if (mTv_UserName != null && mUserNameFontColor != -1) {
				mTv_UserName.setTextColor(mUserNameFontColor);
			}

			if (mTv_UserSignature != null && mUserSignatureFontColor != -1) {
				mTv_UserSignature.setTextColor(mUserSignatureFontColor);
			}

			if (mTv_Level != null && mLevelFontColor != -1) {
				mTv_Level.setTextColor(mLevelFontColor);
			}

			if (mTv_Favorite != null && mFavoriteFontColor != -1) {
				mTv_Favorite.setTextColor(mFavoriteFontColor);
			}

			// if(mRL_Favorite != null){
			// mRL_Favorite.setAlpha(70);
			// }
			break;
		case MODEL_PERSONAL_INFO_LITE:
			viewRoot = LayoutInflater.from(context).inflate(R.layout.view_other_user_info, null);
			addView(viewRoot, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			mRV_Item = (RippleView) viewRoot.findViewById(R.id.mRV_Item);
			mRIV_HeadImage = (RoundImageView) viewRoot.findViewById(R.id.mRIV_HeadImage);
			mTv_UserName = (TextView) viewRoot.findViewById(R.id.mTv_UserName);
			mTv_UserSignature = (TextView) viewRoot.findViewById(R.id.mTv_UserSignature);
            mItv_Vip = (IcomoonTextView) viewRoot.findViewById(R.id.mItv_Vip);
			mInt_HeadImage = array.getResourceId(R.styleable.UserInfoViewStyle_image_head_image, -1);

            // Fix BUG 4572 - 
            mTv_UserSignature.setSingleLine(false);
            mTv_UserSignature.setMaxLines(2);
            
			mUserNameFontColor = array.getColor(R.styleable.UserInfoViewStyle_fontcolor_user_name, -1);
			mUserSignatureFontColor = array.getColor(R.styleable.UserInfoViewStyle_fontcolor_user_signature, -1);

			mUserNameFontSize = array.getInt(R.styleable.UserInfoViewStyle_fontsize_user_name, -1);
			mUserSignatureFontSize = array.getInt(R.styleable.UserInfoViewStyle_fontsize_user_signature_fontsize, -1);

			mStr_UserName = array.getString(R.styleable.UserInfoViewStyle_text_user_name);
			mStr_UserSignature = array.getString(R.styleable.UserInfoViewStyle_text_user_signature);

			mItemClickable = array.getBoolean(R.styleable.UserInfoViewStyle_clickable_item, false);
			mHeadImageClickable = array.getBoolean(R.styleable.UserInfoViewStyle_clickable_head_image, false);

			if (mStr_UserName != null) {
				mTv_UserName.setText(mStr_UserName);
			}

			if (mStr_UserSignature != null) {
				mTv_UserSignature.setText(mStr_UserSignature);
			}

			if (mStr_Personal_Info != null) {
				mTv_Personal_Info.setText(mStr_Personal_Info);
			}

			// if(mRL_Item != null){
			// mRL_Item.setClickable(mItemClickable);
			// mRL_Item.setFocusable(mItemClickable);
			// mRL_Item.setOnClickListener(this);
			// }

			if (mRV_Item != null) {
				mRV_Item.setClickable(mItemClickable);
				mRV_Item.setFocusable(mItemClickable);
				mRV_Item.setOnClickListener(this);
			}

			if (mRIV_HeadImage != null) {
				if (mInt_HeadImage != -1) {
					mRIV_HeadImage.setImageResource(mInt_HeadImage);
				}

				if (mHeadImageClickable) {
					mRIV_HeadImage.setClickable(mHeadImageClickable);
					mRIV_HeadImage.setFocusable(mHeadImageClickable);
					mRIV_HeadImage.setOnClickListener(this);
					mRIV_HeadImage.setOnTouchListener(onTouchListener);
				}
			}

			if (mTv_UserName != null && mUserNameFontColor != -1) {
				mTv_UserName.setTextColor(mUserNameFontColor);
			}

			if (mTv_UserSignature != null && mUserSignatureFontColor != -1) {
				mTv_UserSignature.setTextColor(mUserSignatureFontColor);
			}

			break;
		default:
			break;
		}

	}

	public OnTouchListener onTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				changeLight((ImageView) view, 0);
				break;
			case MotionEvent.ACTION_DOWN:
				changeLight((ImageView) view, -60);
				break;
			case MotionEvent.ACTION_MOVE:
				// changeLight(view, 0);
				break;
			case MotionEvent.ACTION_CANCEL:
				changeLight((ImageView) view, 0);
				break;
			default:
				break;
			}
			return false;
		}
	};

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public void onClick(View v) {
		// if (R.id.mRL_Item == v.getId() && mOnItemClickListener != null) {
		// mOnItemClickListener.onItemClick();
		// }
		if (isFastClick()) {
			return;
		}

		if (R.id.mRV_Item == v.getId() && mOnItemClickListener != null) {
			new Handler().postDelayed(new Runnable() {
				public void run() {
					mOnItemClickListener.onItemClick();
				}
			}, 260);
		}

		if (R.id.mRIV_HeadImage == v.getId() && mOnHeadImageClickListener != null) {
			mOnHeadImageClickListener.onHeadImageClick();
		}

		if (R.id.mRL_Personal_Info == v.getId() && mOnRightArrowClickListener != null) {
			mOnRightArrowClickListener.onRightArrowClick();
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
	
	public void setHeight(int height){
		if (mRL_Item != null) {
			LayoutParams params = (LayoutParams)mRL_Item.getLayoutParams();
			params.height = ScreenUtil.dp2px(App.getAppContext(),height);
			mRL_Item .setLayoutParams(params);
		}
	}
	

	
	public void setBackgroundImage(int icon) {
		if (mRL_Item != null) {
			mRL_Item.setBackgroundResource(icon);
		}
	}

	@SuppressWarnings("deprecation")
	public void setBackgroundImage(Drawable drawable) {
		if (mRL_Item != null) {
			mRL_Item.setBackgroundDrawable(drawable);
		}
	}

	public void setHeadImage(int icon) {
		if (mRIV_HeadImage != null) {
			mInt_HeadImage = icon;
			mRIV_HeadImage.setVisibility(View.VISIBLE);
			mRIV_HeadImage.setImageResource(mInt_HeadImage);
		}
	}

	public void setHeadImage(Bitmap bitmap) {
		if (mRIV_HeadImage != null) {
			mRIV_HeadImage.setVisibility(View.VISIBLE);
			mRIV_HeadImage.setImageBitmap(bitmap);
		}
	}

	public void setHeadImage(String url) {
		if (mRIV_HeadImage != null) {
			mRIV_HeadImage.setVisibility(View.VISIBLE);
//			mRIV_HeadImage.setImageBitmap(bitmap);
			Picasso.with(App.getAppContext()).load(url)
					.networkPolicy(NetworkPolicy.NO_CACHE)
					.memoryPolicy(MemoryPolicy.NO_CACHE)
					.error(getResources().getDrawable(R.drawable.default_avatar))
					.into(mRIV_HeadImage);
		}
	}

	public void setHeadImage(Drawable drawable) {
		if (mRIV_HeadImage != null) {
			mRIV_HeadImage.setVisibility(View.VISIBLE);
			mRIV_HeadImage.setImageDrawable(drawable);
		}
	}
	
	public void setHeadImageSize(int size){
		if(mRIV_HeadImage != null && size != -1){
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mRIV_HeadImage.getLayoutParams();
			params.width = ScreenUtil.dp2px(App.getAppContext(),size);
			params.height = ScreenUtil.dp2px(App.getAppContext(),size);
			mRIV_HeadImage .setLayoutParams(params);
		}
	}

	public RoundImageView getHeadImageView() {
		if (mRIV_HeadImage != null) {
			mRIV_HeadImage.setVisibility(View.VISIBLE);
			return mRIV_HeadImage;
		} else {
			return null;
		}
	}

	public void setRightArrowIcon(int icon) {
		mInt_RightArrow = icon;
		mIv_RightArrow.setImageResource(mInt_RightArrow);
	}

	public void setRightArrowIcon(Bitmap bitmap) {
		if (mIv_RightArrow != null) {
			mIv_RightArrow.setVisibility(View.VISIBLE);
			mIv_RightArrow.setImageBitmap(bitmap);
		}
	}

	public void setRightArrowIcon(Drawable drawable) {
		if (mIv_RightArrow != null) {
			mIv_RightArrow.setVisibility(View.VISIBLE);
			mIv_RightArrow.setImageDrawable(drawable);
		}
	}

	public String getUserName() {
		return mStr_UserName;
	}

	public void setUserName(CharSequence title) {
		if (!TextUtils.isEmpty(title) && mTv_UserName != null) {
			mStr_UserName = title.toString();
			mTv_UserName.setText(title);
		}
	}

	public String getUserSignature() {
		return mStr_UserSignature;
	}

	public void setUserSignature(CharSequence title) {
		if (!TextUtils.isEmpty(title) && mTv_UserSignature != null) {
			mStr_UserSignature = title.toString();
			mTv_UserSignature.setText(title);
		}
	}

	public String getPersonalInfo() {
		return mStr_Personal_Info;
	}

	public void setPersonalInfo(CharSequence title) {
		if (!TextUtils.isEmpty(title) && mTv_Personal_Info != null) {
			mStr_Personal_Info = title.toString();
			mTv_Personal_Info.setText(title);
		}
	}

	public String getLevel() {
		return mStr_Level;
	}

	public void setLevel(CharSequence title) {
		if (!TextUtils.isEmpty(title) && mTv_Level != null) {
			mStr_Level = title.toString();
			mTv_Level.setText(mContext.getResources().getString(R.string.common_text_level, title));
		}
	}

	public String getFavorite() {
		return mStr_Favorite;
	}

	public void setFavorite(CharSequence title) {
		if (!TextUtils.isEmpty(title) && mTv_Favorite != null) {
			mStr_Favorite = title.toString();
			mTv_Favorite.setText(title);
		}
	}
	
	public void setUserNameText(String value){
		if(mTv_UserName != null){
			mTv_UserName.setText(value);
		}
	}

	public void setUserNameTextFontSize(float size) {
		if (mTv_UserName != null) {
			mTv_UserName.setTextSize(size);
		}
	}
	
	public void setUserNameTextFontSize(int unit,float size) {
		if (mTv_UserName != null) {
			mTv_UserName.setTextSize(unit, size);
		}
	}

	public void setUserSignatureTextFontSize(float size) {
		if (mUserSignatureFontSize != -1 && mTv_UserSignature != null) {
			mTv_UserSignature.setTextSize(size);
		}
	}

	public void setPersonalInfoTextFontSize(float size) {
		if (mPersonalInfoFontSize != -1 && mTv_Personal_Info != null) {
			mTv_Personal_Info.setTextSize(size);
		}
	}

	public void setUseNameTextFontColor(int color) {
		if (mUserNameFontSize != -1 && mTv_UserName != null) {
			mTv_UserName.setTextColor(color);
		}
	}

	public void setUseSignatureTextFontColor(int color) {
		if (mUserSignatureFontSize != -1 && mTv_UserSignature != null) {
			mTv_UserSignature.setTextColor(color);
		}
	}

	public void setPersonalInfoTextFontColor(int color) {
		if (mPersonalInfoFontSize != -1 && mTv_Personal_Info != null) {
			mTv_Personal_Info.setTextColor(color);
		}
	}

	public void setItemClickable(boolean value) {
		// if(mRL_Item != null){
		// mRL_Item.setClickable(value);
		// mRL_Item.setFocusable(value);
		// }

		if (mRV_Item != null) {
			mRV_Item.setClickable(mItemClickable);
			mRV_Item.setFocusable(mItemClickable);
			mRV_Item.setOnClickListener(this);
		}
	}

	public void setHeadImageClickable(boolean value) {
		if (mRIV_HeadImage != null) {
			mRIV_HeadImage.setClickable(value);
			mRIV_HeadImage.setFocusable(value);
		}
	}

	public void setPersonalInfoClickable(boolean value) {
		if (mRL_Personal_Info != null) {
			mRL_Personal_Info.setClickable(value);
			mRL_Personal_Info.setFocusable(value);
		}
	}
	
	public void setVipImage(int imagRes) {
		if (null != mTv_Vip) {
			mTv_Vip.setImageResource(imagRes);
		}
	}
    
	/**
	 * 显示vip 图标 
	 */
	public void showVip() {
		if (null != mTv_Vip) {
			mTv_Vip.setVisibility(View.VISIBLE);
		}

	}
	/**
	 * 隐藏vip 图标 
	 */
	public void hideVip() {
		if (null != mTv_Vip) {
			mTv_Vip.setVisibility(View.GONE);
		}
	}

    public void setVipResource(String res) {
        if (null != mItv_Vip) {
            mItv_Vip.setText(res);
        }
    }
    
    public void setVipColor(int colorRes) {
        if (null != mItv_Vip) {
            mItv_Vip.setTextColor(colorRes);
        }
    }

    /**
     * 显示 vip 字体文件
     */
    public void showVipIcon() {
        if (null != mItv_Vip) {
            mItv_Vip.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏 vip 字体文件
     */
    public void hideVipIcon() {
        if (null != mItv_Vip) {
            mItv_Vip.setVisibility(View.GONE);
        }
    }

	public interface OnItemClickListener {
		public void onItemClick();
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		mOnItemClickListener = onItemClickListener;
	}

	public interface OnHeadImageClickListener {
		public void onHeadImageClick();
	}

	private OnHeadImageClickListener mOnHeadImageClickListener;

	public void setOnHeadImageClickListener(OnHeadImageClickListener onHeadImageClickListener) {
		mOnHeadImageClickListener = onHeadImageClickListener;
	}

	public interface OnRightArrowClickListener {
		public void onRightArrowClick();
	}

	private OnRightArrowClickListener mOnRightArrowClickListener;

	public void setOnRightArrowClickListener(OnRightArrowClickListener onRightArrowClickListener) {
		mOnRightArrowClickListener = onRightArrowClickListener;
	}

	private void changeLight(ImageView imageview, int brightness) {
		ColorMatrix matrix = new ColorMatrix();
		matrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
		imageview.setColorFilter(new ColorMatrixColorFilter(matrix));
	}
}
