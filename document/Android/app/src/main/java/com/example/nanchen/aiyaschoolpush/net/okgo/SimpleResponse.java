package com.example.nanchen.aiyaschoolpush.net.okgo;

import java.io.Serializable;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.net.okgo
 * @date 2016/11/09  08:51
 */

public class SimpleResponse implements Serializable {

    public int code;
    public String msg;

    public LslResponse toResponse() {
        LslResponse LslResponse = new LslResponse();
        LslResponse.code = code;
        LslResponse.msg = msg;
        return LslResponse;
    }
}
