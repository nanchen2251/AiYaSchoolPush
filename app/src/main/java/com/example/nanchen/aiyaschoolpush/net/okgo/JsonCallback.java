package com.example.nanchen.aiyaschoolpush.net.okgo;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.net.okgo
 * @date 2016/11/09  08:49
 *
 * 默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 */

public abstract class JsonCallback<T> extends AbsCallback<T> {
    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        //主要用于在所有请求之前添加公共的请求头或请求参数，例如登录授权的 token,使用的设备信息等,可以随意添加,也可以什么都不传
        request.headers("header1", "HeaderValue1")//
                .params("params1", "ParamsValue1")//
                .params("token", "3215sdf13ad1f65asd4f3ads1f");
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertSuccess(okhttp3.Response response) throws Exception {

        //以下代码是通过泛型解析实际参数,泛型必须传
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();


        JsonConvert<T> convert = new JsonConvert<>();
        convert.setType(params[0]);
        T t = convert.convertSuccess(response);
        response.close();
        return t;
    }
}
