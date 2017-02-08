package com.example.nanchen.aiyaschoolpush.net;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.example.nanchen.aiyaschoolpush.helper.event.NetStateEvent;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.greenrobot.eventbus.EventBus;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.net
 * @date 2016/12/01  13:55
 */

public class NetworkStateService extends Service {
    // 系统网络连接相关的操作管理类.
    private ConnectivityManager mManager;

    // 网络状态信息的实例
    private NetworkInfo mInfo;

    /**
     * 当前处于的网络
     * 0 ：null
     * 1 : 2G/3G/4G
     * 2 ：wifi
     */
    public static int networkStatus;

    /**
     * An action name
     */
    public static final String NETWORKSTATE = "com.text.android.network.state";

    /**
     * 广播实例
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // The action of this intent or null if none is specified.
            // action是行动的意思，也许是我水平问题无法理解为什么叫行动，我一直理解为标识（现在理解为意图）
            String action = intent.getAction(); //当前接受到的广播的标识(行动/意图)

            // 当当前接受到的广播的标识(意图)为网络状态的标识时做相应判断
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                // 获取网络连接管理器
                mManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                // 获取当前网络状态信息
                mInfo = mManager.getActiveNetworkInfo();

                if (mInfo != null && mInfo.isAvailable()) {

                    //当NetworkInfo不为空且是可用的情况下，获取当前网络的Type状态
                    //根据NetworkInfo.getTypeName()判断当前网络
                    String name = mInfo.getTypeName();

                    //更改NetworkStateService的静态变量，之后只要在Activity中进行判断就好了
                    if (name.equals("WIFI")) {
                        networkStatus = 2;
                    } else {
                        networkStatus = 1;
                    }

                } else {

                    // NetworkInfo为空或者是不可用的情况下
                    networkStatus = 0;

//                    UIUtil.showToast("当前网络不可用，请检查网络连接!");

                    EventBus.getDefault().post(new NetStateEvent());

                    Intent it = new Intent();
                    it.putExtra("networkStatus", networkStatus);
                    it.setAction(NETWORKSTATE);
//                    sendBroadcast(it); //发送无网络广播给注册了当前服务广播的Activity
                    /**
                     * 这里推荐使用本地广播的方式发送:
                     * LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                     */
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                }
            }
        }
    };

    private NiftyDialogBuilder mDialogBuilder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //注册网络状态的广播，绑定到mReceiver
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销接收
        unregisterReceiver(mReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        // 获取网络连接管理器
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // 获取当前网络状态信息
        NetworkInfo[] info = mgr.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }

        return false;
    }
}
