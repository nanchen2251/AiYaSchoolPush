package com.example.nanchen.aiyaschoolpush.net.retrofit;

import android.os.AsyncTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.net
 * @date 2016/09/23  16:50
 */
public class BaseApi {

    private static final String MOB_BASE_URL = "http://apicloud.mob.com/";

    protected Retrofit mRetrofit;

    private static final String TAG = "BaseApi";


    public BaseApi(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(MOB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public BaseApi(String baseUrl){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public void downloadFile(final String fileUrl, final String filePath, FileDownloadCallback callback){
        final  ApiStore apiStore = mRetrofit.create(ApiStore.class);

        new AsyncTask<Void,Long,Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                Call<ResponseBody> call = apiStore.downloadFile(fileUrl);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            new AsyncTask<Void,Void,Void>(){

                                private boolean mWrittenToDisk;

                                @Override
                                protected Void doInBackground(Void... params) {

                                    return null;
                                }
                            };
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                return null;
            }
        };
    }


}
