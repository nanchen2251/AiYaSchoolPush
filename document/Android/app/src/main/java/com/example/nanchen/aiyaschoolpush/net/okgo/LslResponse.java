package com.example.nanchen.aiyaschoolpush.net.okgo;

import java.io.Serializable;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.net.okgo
 * @date 2016/11/09  08:48
 */

public class LslResponse<T> implements Serializable {

    public int code;
    public String msg;
    public T data;

    /**
     * 请求编码返回正确
     */
    public final static int RESPONSE_OK = 0;


    /**
     * 请求编码返回错误
     */
    public final static int RESPONSE_ERROR = -1;

}
