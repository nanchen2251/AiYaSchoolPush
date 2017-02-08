package com.example.nanchen.aiyaschoolpush.helper.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.receiver
 * @date 2016/11/11  11:40
 *
 * 头像改变的广播接收器，防止用户在个人信息更改图片后主页面头像未更新的问题
 */

public class AvatarReceiver extends BroadcastReceiver {

    private static final String TAG = "AvatarReceiver";

    public static final String AVATAR_ACTION = "com.nanchen.android.AVATAR_ACTION";


    private AvatarCallback mAvatarCallback;

    public AvatarReceiver(AvatarCallback avatarCallback){
        mAvatarCallback = avatarCallback;
    }

    public AvatarReceiver(){}


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e(TAG,action);
        // 如果是正确的action
        if (AVATAR_ACTION.equals(action)){
            if (mAvatarCallback != null){
                mAvatarCallback.onAvatarChanged();
            }
        }
    }

    public interface AvatarCallback{
        /**
         * 头像更改时调用
         */
        void onAvatarChanged();
    }
}
