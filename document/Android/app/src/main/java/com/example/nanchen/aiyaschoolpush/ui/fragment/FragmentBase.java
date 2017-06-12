package com.example.nanchen.aiyaschoolpush.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.fragment
 * @date 2016/10/08  09:00
 */

public class FragmentBase extends Fragment{

//    private Dialog mDialog;

    @Override
    public void onAttach(Context context) {
        Log.i(this.getClass().getSimpleName(),"onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(this.getClass().getSimpleName(),"onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        Log.i(this.getClass().getSimpleName(),"onDestroy");
        super.onDestroy();
    }

    @Override
    public void onResume() {
        Log.i(this.getClass().getSimpleName(),"onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(this.getClass().getSimpleName(),"onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(this.getClass().getSimpleName(),"onStop");
        super.onStop();
    }

    /* 以下方法的stopLoading 不起作用，以后都用强转到Activity做处理*/
//    public void showLoading(Context context, String text, OnReturnListener listener){
//        Loading loading = new Loading();
//        loading.showLoading(context,text,listener,Loading.LOGOSTYLE);
//    }
//
//    public void showLoading(Context context,String text){
//        Loading loading = new Loading();
//        loading.showLoading(context,text,null,Loading.LOGOSTYLE);
//    }
//
//    public void showLoading(Context context){
//        Loading loading = new Loading();
//        loading.showLoading(context,null,null,Loading.LOGOSTYLE);
//    }
//
//    public void stopLoading(){
//        Loading loading = new Loading();
//        loading.dialogDismiss(mDialog);
//    }
}
