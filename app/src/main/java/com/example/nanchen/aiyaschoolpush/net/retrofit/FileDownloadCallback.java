package com.example.nanchen.aiyaschoolpush.net.retrofit;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.net
 * @date 2016/09/23  17:04
 */
public interface FileDownloadCallback {
    /**
     * 下载成功
     */
    void onSuccess();

    /**
     * 下载进度
     */
    void onProgress(long fileSizeDownloaded,long fileSize);

    /**
     * 网络请求失败
     */
    void onFailure();
}
