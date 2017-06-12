package com.example.nanchen.aiyaschoolpush.helper;

import android.content.Context;
import android.graphics.Typeface;

/**
 * 统一 字体加载类 
 * 
 * 第一次加载在MainActivity 中调用
 * （字体加载为相对耗时操作 所以做成单例）
 */
public class FontCustomHelper {
	
	private Typeface typeface;
	private static final String FONT_URL = "fonts/icomoon.ttf";
	private static class Holder{
		private static FontCustomHelper helper = new FontCustomHelper();
	}
	
	public static FontCustomHelper getInstance(){
		return Holder.helper;
	}
	
	
	public void init(Context mContext){
		typeface = Typeface.createFromAsset(mContext.getAssets(), FONT_URL);
	}
	
	public Typeface getTypeface(Context mContext) {
		if (null==typeface) {
			init(mContext);
		}
		return typeface;
	}

	public void setTypeface(Typeface typeface) {
		this.typeface = typeface;
	}
}
