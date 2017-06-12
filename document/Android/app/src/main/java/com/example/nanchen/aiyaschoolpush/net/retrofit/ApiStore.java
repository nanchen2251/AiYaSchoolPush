package com.example.nanchen.aiyaschoolpush.net.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.net
 * @date 2016/09/23  17:20
 */
public interface ApiStore {

    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
