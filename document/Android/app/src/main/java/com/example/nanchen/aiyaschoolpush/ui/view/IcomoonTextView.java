package com.example.nanchen.aiyaschoolpush.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.nanchen.aiyaschoolpush.helper.FontCustomHelper;


/**
 * 引用特殊字体的 TextView
 *
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.view
 * @date 2016/09/08  16:21
 */
public class IcomoonTextView extends TextView {

	public IcomoonTextView(Context context) {
		super(context);
	}

	public IcomoonTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public IcomoonTextView(Context context, AttributeSet attrs, int defSyle) {
		super(context, attrs, defSyle);
	}
	
	
	@Override
	public Typeface getTypeface() {
		return FontCustomHelper.getInstance().getTypeface(getContext());
	}
	
	@Override
	public void setTypeface(Typeface tf) {
		super.setTypeface(FontCustomHelper.getInstance().getTypeface(getContext()));
	}
	
}
