package com.example.nanchen.aiyaschoolpush.model.info;

import com.example.nanchen.aiyaschoolpush.model.IJsonModel;

import java.io.Serializable;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.model.info
 * @date 2016/12/15  15:54
 */

public class VideoModel implements Serializable,IJsonModel {
    public int picid;       //缩略图id
    public int videoid;     //视频id
    public String videoUrl; //视频地址

    @Override
    public String toString() {
        return "VideoModel{" +
                "picid=" + picid +
                ", videoid=" + videoid +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }
}
