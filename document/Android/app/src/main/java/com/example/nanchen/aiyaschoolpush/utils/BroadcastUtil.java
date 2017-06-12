package com.example.nanchen.aiyaschoolpush.utils;

import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import com.example.nanchen.aiyaschoolpush.App;

/**
 * 广播工具类
 *
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.utils
 * @date 2016/09/12  16:34
 */
public class BroadcastUtil {
    /**
     * 获取本地广播实例
     */
    public static LocalBroadcastManager getLocalBroadcastInstance() {
        return LocalBroadcastManager.getInstance(App.getAppContext());
    }

    public static LocalBroadcastManager getLocalBroadcastInstance(Context ctx) {
        return LocalBroadcastManager.getInstance(ctx);
    }
}
