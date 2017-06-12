package com.example.nanchen.aiyaschoolpush.net.retrofit;


import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 处理Retrofit回调 并调用ApiCallback返回
 *
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.net
 * @date 2016/09/23  16:59
 */
public class RetrofitCallback<T> implements Callback<T> {

    private ApiCallback<T> mCallback;
    private static final String TAG = "RetrofitCallback";

    public RetrofitCallback(ApiCallback<T> callback){
        this.mCallback = callback;
    }


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()){
            if (((BaseRetData)response.body()).retCode == BaseRetData.RET_OK){
                mCallback.onSuccess(response.body());
            }else {
                BaseRetData baseRetData = (BaseRetData) response.body();
                mCallback.onError(baseRetData.retCode, baseRetData.msg);
            }
        }else {
            mCallback.onFailure();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e(TAG,"api failure,throw="+t.getMessage());
        t.printStackTrace();
        mCallback.onFailure();
    }
}
