package com.example.nanchen.aiyaschoolpush.net.retrofit;

/**
 * api调用回调
 *
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.net
 * @date 2016/09/23  17:01
 */
public interface ApiCallback<T> {

    /**
     * 返回数据成功时回调
     * @param retCode
     */
    void onSuccess(T retCode);

    /**
     * 返回数据失败时回调
     */
    void onError(int err_code,String err_string);

    /**
     * 网络请求失败
     */
    void onFailure();

}
