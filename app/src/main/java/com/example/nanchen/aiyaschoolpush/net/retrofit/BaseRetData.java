package com.example.nanchen.aiyaschoolpush.net.retrofit;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.net
 * @date 2016/09/23  17:08
 */
public class BaseRetData<T> {
    public int retCode;
    public String msg;
    public T data;

    public static final int RET_OK = 200;
}
