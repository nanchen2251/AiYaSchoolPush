package com.example.nanchen.aiyaschoolpush.net.okgo;

import com.google.gson.stream.JsonReader;
import com.lzy.okgo.convert.Converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.net.okgo
 * @date 2016/11/09  08:50
 */

public class JsonConvert<T> implements Converter<T> {
    private Type type;

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public T convertSuccess(okhttp3.Response response) throws Exception {
        JsonReader jsonReader = new JsonReader(response.body().charStream());

        if (type == null) {
            //以下代码是通过泛型解析实际参数,泛型必须传
            Type genType = getClass().getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            type = params[0];
        }
        if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
        Type rawType = ((ParameterizedType) type).getRawType();

        //无数据类型
        if (rawType == Void.class) {
            SimpleResponse baseWbgResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
            //noinspection unchecked
            return (T) baseWbgResponse.toResponse();
        }

        //有数据类型
        if (rawType == LslResponse.class) {
            LslResponse lzyLslResponse = Convert.fromJson(jsonReader, type);
            int code = lzyLslResponse.code;
            if (code == 0 || code == -1) {
                //noinspection unchecked
                return (T) lzyLslResponse;
            } else if (code == 104) {
                //比如：用户授权信息无效，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw new IllegalStateException("用户授权信息无效");
            } else if (code == 105) {
                //比如：用户收取信息已过期，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw new IllegalStateException("用户收取信息已过期");
            } else if (code == 106) {
                //比如：用户账户被禁用，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw new IllegalStateException("用户账户被禁用");
            } else if (code == 300) {
                //比如：其他乱七八糟的等，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                throw new IllegalStateException("其他乱七八糟的等");
            } else {
                throw new IllegalStateException("错误代码：" + code + "，错误信息：" + lzyLslResponse.msg);
            }
        }
        throw new IllegalStateException("基类错误无法解析!");
    }
}
