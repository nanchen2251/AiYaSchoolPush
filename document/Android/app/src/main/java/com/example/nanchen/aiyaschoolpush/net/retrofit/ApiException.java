package com.example.nanchen.aiyaschoolpush.net.retrofit;

/**
 *
 * 自定义异常
 *
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.net
 * @date 2016/09/27  09:19
 */

public class ApiException extends RuntimeException {
    public int retCode;
    public String msg;

    public ApiException(int retCode,String msg){
        this.retCode = retCode;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getRetCode() {
        return retCode;
    }
}
