package com.example.nanchen.aiyaschoolpush.helper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.example.nanchen.aiyaschoolpush.R;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.msg.MsgService;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnicornImageLoader;
import com.qiyukf.unicorn.api.YSFOptions;
import com.qiyukf.unicorn.api.YSFUserInfo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;


/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.helper
 * @date 2016/11/04  15:47
 * <p>
 * 七鱼云客服相关帮助类
 */

public class QiYuCloudServerHelper {


    private static Context mContext;
    private static YSFOptions mOptins;

    private final static String APP_KEY = "2c170d44ffed6896868d56a13a09f63c";


    /**
     * 初始化七鱼云客服
     */
    public static void initCloudServer(Application app) {
        mContext = app;
        Unicorn.init(app, APP_KEY, getServerOptions(), new PicassoImageLoader());
    }

    private static YSFOptions getServerOptions() {
        if (mOptins == null) {
            YSFOptions options = new YSFOptions();
            // 通知栏提醒
            options.statusBarNotificationConfig = new StatusBarNotificationConfig();
            options.statusBarNotificationConfig.notificationSmallIconId = R.mipmap.icon_notify;
            // UI定制  头像版本更新，不再支持drawable://格式
            UICustomization customization = new UICustomization();
//            customization.msgBackgroundColor = mContext.getResources().getColor(R.color.main_bg_color);
//            customization.tipsTextColor = mContext.getResources().getColor(R.color.gray3);
//            customization.msgListViewDividerHeight = 40;
//            customization.leftAvatar = "file://" + R.drawable.service;
//            customization.tipsTextSize = 12;
//            customization.msgItemBackgroundLeft = R.drawable.qiyu_message_item_selector;
//            customization.msgItemBackgroundRight = R.drawable.qiyu_message_item_selector;
//            customization.textMsgColorLeft = mContext.getResources().getColor(R.color.srs_text);
//            customization.textMsgColorRight = mContext.getResources().getColor(R.color.srs_text);
//            customization.titleBackgroundColor = mContext.getResources().getColor(R.color.main_bg_color1);
//            customization.textMsgSize = 18;
//            options.uiCustomization = customization;
//            options.savePowerConfig = new SavePowerConfig();//省电策略
            mOptins = options;
        }
        return mOptins;
    }


    /**
     * 设置客服消息提醒的跳转入口
     */
    private static void setNotifyActivity(Activity activity) {
        mOptins.statusBarNotificationConfig.notificationEntrance = activity.getClass();
    }

    /**
     * 启用通知
     *
     * @param activity 通知跳转入口
     */
    public static void enableNotify(Activity activity) {
        setNotifyActivity(activity);
        NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE, SessionTypeEnum.None);
    }

    /**
     * 禁用通知
     */
    public static void disableNotify() {
        NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_ALL, SessionTypeEnum.None);
    }

    /**
     * 设置用户信息
     *
     * @param login 当前用户是否处于登录状态
     */
    public static void setUserInfo(boolean login) {
        Unicorn.setUserInfo(getCurrentUserInfo(login));
    }


    /**
     * 设置当前登录用户信息
     *
     * @param login 当前用户是否处于登录状态
     * @return
     */
    private static YSFUserInfo getCurrentUserInfo(boolean login) {
        YSFUserInfo userInfo = new YSFUserInfo();
        userInfo.data = userInfoData(login).toString();
        if (login) {
            userInfo.userId = DemoHelper.getInstance().getCurrentUserName();
        }
        return userInfo;
    }


    private static JSONArray userInfoData(boolean login) {
        String userName = DemoHelper.getInstance().getCurrentUserName();
        String platform = null;
        JSONArray array = new JSONArray();
        if (login){
            array.add(userInfoDataItem("real_name", userName, false, -1, null, null));
        }
        array.add(userInfoDataItem("app_version", platform + "-" + Environment2.getPackageVersionName(), false, 0, "应用版本", null));
        array.add(userInfoDataItem("system_version", android.os.Build.VERSION.RELEASE, false, 1, "系统版本", null));
        array.add(userInfoDataItem("device_manufacturer", Build.MANUFACTURER, false, 2, "机型", null));
        array.add(userInfoDataItem("device_model", Build.MODEL, false, 3, "模块", null));
        return array;
    }

    private static JSONObject userInfoDataItem(String key, Object value, boolean hidden, int index, String label, String href) {
        JSONObject item = null;
        try {
            item = new JSONObject();
            item.put("key", key);
            item.put("value", value);
            if (hidden) {
                item.put("hidden", true);
            }
            if (index >= 0) {
                item.put("index", index);
            }
            if (!TextUtils.isEmpty(label)) {
                item.put("label", label);
            }
            if (!TextUtils.isEmpty(href)) {
                item.put("href", href);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    private static class PicassoImageLoader implements UnicornImageLoader {

        private final Set<Target> protectedFromGarbageCollectorTargets = new HashSet<>();

        @Nullable
        @Override
        public Bitmap loadImageSync(String uri, int width, int height) {
            return null;
        }

        @Override
        public void loadImage(final String uri, final int width, final int height, final ImageLoaderListener listener) {
            final Target mTarget = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    if (listener != null) {
                        listener.onLoadComplete(bitmap);
                        protectedFromGarbageCollectorTargets.remove(this);
                    }
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    if (listener != null) {
                        listener.onLoadFailed(null);
                        protectedFromGarbageCollectorTargets.remove(this);
                    }
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RequestCreator requestCreator = Picasso.with(mContext).load(uri).config(Bitmap.Config.RGB_565);
                    if (width > 0 && height > 0) {
                        requestCreator = requestCreator.resize(width, height);
                    }
                    protectedFromGarbageCollectorTargets.add(mTarget);
                    requestCreator.into(mTarget);
                }
            });
        }
    }


}
