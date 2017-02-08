package com.example.nanchen.aiyaschoolpush.helper;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

import com.example.nanchen.aiyaschoolpush.App;

/**
 * 表示当前应用及系统的环境信息
 * 
 * */
public class Environment2 {
	/**
	 * 获取Application Context
	 * */
	public static Context getAppContext() {
		return App.getAppContext();
	}

	public static int getPackageVersionCode() {
		try {
			Context ctx = App.getAppContext();
			return ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 1;
		}
	}

	public static String getPackageVersionName() {
		try {
			Context ctx = App.getAppContext();
			return ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "1.0";
		}
	}
}
