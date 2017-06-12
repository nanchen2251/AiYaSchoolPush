package com.example.nanchen.aiyaschoolpush.ui.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nanchen.aiyaschoolpush.R;

import java.util.List;


/**
 * dialog
 * 
 */
public class SelectDialog extends Dialog implements OnClickListener,OnItemClickListener {
	private SelectDialogListener mListener;
	private Activity mActivity;
	private Button mMBtn_Cancel;
	private TextView mTv_Title;
	private List<String> mName;
	private String mTitle;
	private boolean mUseCustomColor = false;
	private int mFirstItemColor;
	private int mOtherItemColor;
	
	public interface SelectDialogListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id);
	}

	
	/**
	 * 取消事件监听接口
	 * 
	 * @author 吴立富
	 * @date 2015-03-17 11:00
	 */
	private SelectDialogCancelListener mCancelListener;
	
	public interface SelectDialogCancelListener {
		public void onCancelClick(View v);
	}

	public SelectDialog(Activity activity, int theme,
			SelectDialogListener listener,List<String> names) {
		super(activity, theme);
		mActivity = activity;
		mListener = listener;
		this.mName=names;
		
		/**吴立富  2015-03-17 11:05 修改*/
		// 设置是否点击外围解散
		setCanceledOnTouchOutside(true);
	}
	
	/**
	 * @param activity 调用弹出菜单的activity
	 * @param theme 主题
	 * @param listener 菜单项单击事件
	 * @param cancelListener 取消事件
	 * @param names 菜单项名称
	 * 
	 * @author 吴立富
	 * @date 2015-03-17 11:06
	 */
	public SelectDialog(Activity activity, int theme,SelectDialogListener listener,SelectDialogCancelListener cancelListener ,List<String> names) {
		super(activity, theme);
		mActivity = activity;
		mListener = listener;
		mCancelListener = cancelListener;
		this.mName=names;
		
		// 设置是否点击外围不解散
		setCanceledOnTouchOutside(false);
	}
	
	/**
	 * @param activity 调用弹出菜单的activity
	 * @param theme 主题
	 * @param listener 菜单项单击事件
	 * @param names 菜单项名称
	 * @param title 菜单标题文字
	 * 
	 * @author 吴立富
	 * @date 2015-03-20 16:33
	 */
	public SelectDialog(Activity activity, int theme,SelectDialogListener listener,List<String> names,String title) {
		super(activity, theme);
		mActivity = activity;
		mListener = listener;
		this.mName=names;
		mTitle = title;
		
		// 设置是否点击外围可解散
		setCanceledOnTouchOutside(true);
	}

	public SelectDialog(Activity activity, int theme,SelectDialogListener listener,SelectDialogCancelListener cancelListener,List<String> names,String title) {
		super(activity, theme);
		mActivity = activity;
		mListener = listener;
		mCancelListener = cancelListener;
		this.mName=names;
		mTitle = title;
		
		// 设置是否点击外围可解散
		setCanceledOnTouchOutside(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = getLayoutInflater().inflate(R.layout.view_dialog_select,
				null);
		setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		Window window = getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = mActivity.getWindowManager().getDefaultDisplay().getHeight();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = LayoutParams.MATCH_PARENT;
		wl.height = LayoutParams.WRAP_CONTENT;

		// 设置显示位置
		onWindowAttributesChanged(wl);
		
		/**吴立富  2015-03-17 11:08 修改:为解决只能通过取消按钮隐藏菜单项
			// 设置点击外围解散
			//setCanceledOnTouchOutside(false);
		 */
		initViews();
	}

	private void initViews() {
		DialogAdapter dialogAdapter=new DialogAdapter(mName);
		ListView dialogList=(ListView) findViewById(R.id.dialog_list);
		dialogList.setOnItemClickListener(this);
		dialogList.setAdapter(dialogAdapter);
		mMBtn_Cancel = (Button) findViewById(R.id.mBtn_Cancel);
		mTv_Title = (TextView) findViewById(R.id.mTv_Title);
		
		/**吴立富  2015-03-17 11:08 修改:为解决只能通过取消按钮隐藏菜单项
			//mMBtn_Cancel.setOnClickListener(this);
		*/

		/**吴立富  2015-03-17 11:10 调用取消事件*/
		mMBtn_Cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mCancelListener != null){
					mCancelListener.onCancelClick(v);	
				}
				dismiss();
			}
		});
		
		/**吴立富  2015-03-20 16:33 用于显示菜单的标题*/
		if(!TextUtils.isEmpty(mTitle) && mTv_Title != null){
			mTv_Title.setVisibility(View.VISIBLE);
			mTv_Title.setText(mTitle);
		}else{
			mTv_Title.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		dismiss();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		mListener.onItemClick(parent, view, position, id);
		dismiss();
	}
	private class DialogAdapter extends BaseAdapter {
		private List<String> mStrings;
		private Viewholder viewholder;
		private LayoutInflater layoutInflater;
		public DialogAdapter(List<String> strings) {
			this.mStrings = strings;
			this.layoutInflater=mActivity.getLayoutInflater();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mStrings.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mStrings.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (null == convertView) {
				viewholder=new Viewholder();
				convertView=layoutInflater.inflate(R.layout.view_dialog_item, null);
				viewholder.dialogItemButton=(TextView) convertView.findViewById(R.id.dialog_item_bt);
				convertView.setTag(viewholder);
			}else{
				viewholder=(Viewholder) convertView.getTag();
			}
			viewholder.dialogItemButton.setText(mStrings.get(position));
			if (!mUseCustomColor) {
				mFirstItemColor = mActivity.getResources().getColor(R.color.dialog_blue);
				mOtherItemColor = mActivity.getResources().getColor(R.color.dialog_blue);
			}
			if (1 == mStrings.size()) {
				viewholder.dialogItemButton.setTextColor(mFirstItemColor);
				viewholder.dialogItemButton.setBackgroundResource(R.drawable.dialog_item_bg_only);
			} else if (position == 0) {
				viewholder.dialogItemButton.setTextColor(mFirstItemColor);
				viewholder.dialogItemButton.setBackgroundResource(R.drawable.hkcomm_dialog_item_bg_top);
			} else if (position == mStrings.size() - 1) {
				viewholder.dialogItemButton.setTextColor(mOtherItemColor);
				viewholder.dialogItemButton.setBackgroundResource(R.drawable.hkcomm_dialog_item_bg_buttom);
			} else {
				viewholder.dialogItemButton.setTextColor(mOtherItemColor);
				viewholder.dialogItemButton.setBackgroundResource(R.drawable.hkcomm_dialog_item_bg_center);
			}
			return convertView;
		}

	}

	public static class Viewholder {
		public TextView dialogItemButton;
	}

	/**
	 * 设置列表项的文本颜色
	 */
	public void setItemColor(int firstItemColor, int otherItemColor) {
		mFirstItemColor = firstItemColor;
		mOtherItemColor = otherItemColor;
		mUseCustomColor = true;
	}
	
}
