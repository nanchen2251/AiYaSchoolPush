package com.example.nanchen.aiyaschoolpush.ui.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nanchen.aiyaschoolpush.R;

import java.lang.ref.WeakReference;

/**
 * 自定义网络加载对话框
 *
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.view
 * @date 2016/09/08  14:29
 */
public class Loading {
    public static final String LOGOSTYLE = "logo";
    public static final String CIRCLESTYLE = "circle";

    // 控制填满速度
    public static final int SLEEPTIME = 160;
    // 控制进度增长
    private boolean isLoadingShow = true;

    private Dialog mDialog;
    private ProgressBar mProgressBar;
    private TextView mLoadingText;

    public interface OnReturnListener {
        void back();
    }


    public void destroyLoading(){
        if (mProgressBar != null){
            mProgressBar = null;
        }
        if (mDialog != null){
            mDialog.cancel();
            mDialog = null;
        }
    }


    /**
     * 显示网络加载对话框
     *
     * @param context   上下文对象
     * @param text      按返回之后等待框消失之前progressBar下面需要显示的文字提示（可以为空，为空显示默认文字）
     * @param listenter 按返回之后回调监听函数
     * @param style     对话框风格
     * @return          返回一个自定义对话框
     */
    public Dialog showLoading(Context context, final String text, final OnReturnListener listenter, String style){
        if (mDialog != null && mDialog.isShowing()){
            return mDialog;
        }
        // 使用弱引用，防止内存泄漏
        WeakReference<Context> contextWeakReference = new WeakReference<Context>(context);// 把context设置为弱引用
        LayoutInflater inflater = LayoutInflater.from(contextWeakReference.get());
        mDialog = new Dialog(contextWeakReference.get(), R.style.loading_style);
        mDialog.setCanceledOnTouchOutside(false);// 设置点击对话框以外不可取消
        mDialog.setCancelable(true);
        View loadingView = null;
        if (style.equals(LOGOSTYLE)){
            loadingView = inflater.inflate(R.layout.view_loading_logo,null,false);
            mProgressBar = (ProgressBar) loadingView.findViewById(R.id.view_loading);
            startProgress();
        }else if (style.equals(CIRCLESTYLE)){
            loadingView = inflater.inflate(R.layout.view_loading_circle,null,false);
        }
        mLoadingText = (TextView) loadingView.findViewById(R.id.view_loading_text);
        setListener(listenter,text);
        mDialog.setContentView(loadingView);
        mDialog.show();
        isLoadingShow = true;
        return mDialog;
    }

    /**
     * 设置Dialog的监听器
     * @param listener 按返回之后回调监听函数
     * @param text      按返回之后等待框消失前progressBar下面需要显示的文字
     */
    private void setListener(final OnReturnListener listener, final String text) {
        mDialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK){
                    if (!TextUtils.isEmpty(text)){
                        setDialogText(text);
                    }
                    if (listener != null){
                        listener.back();
                    }
                }
                return false;
            }
        });

        mDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isLoadingShow = false;
                mProgressBar = null;
                dialog = null;
                mLoadingText = null;
                // 清空handler队列，防止内存泄漏
                handler.removeCallbacksAndMessages(null);
            }
        });
    }

    /**
     * 对话框销毁
     */
    public void dialogDismiss(Dialog dialog){
        isLoadingShow = false;
        if ( dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    /**
     * 设置文字内容
     * @param text  文字内容
     */
    private void setDialogText(String text) {
        if (mLoadingText != null){
            mLoadingText.setText(text);
        }
    }

    /**
     * 开启控制progressBar线程
     */
    private void startProgress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isLoadingShow){
                    try {
                        handler.sendEmptyMessage(0x123);
                        Thread.sleep(SLEEPTIME);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x123:
                    if (mProgressBar != null){
                        if (mProgressBar.getProgress() >= 100){
                            mProgressBar.setProgress(0);
                        }
                        mProgressBar.incrementProgressBy(4);
                    }
                    break;
            }

        }
    };
}
