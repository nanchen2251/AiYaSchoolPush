package com.example.nanchen.aiyaschoolpush.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.model
 * @date 2016/10/26  10:48
 *
 * 社区话题附件
 */

public class Attach implements IJsonModel,Serializable {
    public static final int ATTACH_TYPE_IMAGE = 0;
    public static final int ATTACH_TYPE_VIDEO = 1;

    public List<Image> mImages;
    public List<Video> mVideos;


    private static class Image implements Serializable{
        public String url;
        /**
         * 图片宽度
         */
        public int width;
        /**
         * 图片高度
         */
        public int height;
    }

    public static class Video implements Serializable {
        public String url;
        public String name;
        /**
         * 媒体时长
         */
        public int duration;
        /**
         * 视频宽度
         */
        public int width;
        /**
         * 视频高度
         */
        public int height;
    }
}
