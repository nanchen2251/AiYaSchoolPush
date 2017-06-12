package com.example.nanchen.aiyaschoolpush.model.info;

import com.example.nanchen.aiyaschoolpush.model.IJsonModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.model.info
 * @date 2016/11/16  14:03
 */

public class InfoModel implements Serializable,IJsonModel{
    /**
     * 主贴id
     */
    public int mainid;
    /**
     * 班级id,可见性
     */
    public int classid;
    /**
     * 用户名，用于获取用户资料信息
     */
    public String username;
    /**
     * 发起时间戳
     */
    public long time;
    /**
     * 信息类型，1为公告，2为作业，3为动态
     */
    public int infotype;
    /**
     * 信息内容
     */
    public String content;
    /**
     * 发布人信息
     */
    public UserModel user;
    /**
     * 赞的数目
     */
    public int praiseCount;
    /**
     * 评论的数目
     */
    public int commentCount;
    /**
     * 我是否赞了该条信息
     */
    public boolean isIPraised;
    /**
     * 评论信息
     */
    public List<CommentInfoModel> commentInfo;
    /**
     * 最后获取的一条信息的id
     */
    public int lastid;

    /**
     * 社区图片Url组
     */
    public List<PicModel> picUrls;

    /**
     * 社区视频Url
     */
    public List<VideoModel> videoUrl;

}
