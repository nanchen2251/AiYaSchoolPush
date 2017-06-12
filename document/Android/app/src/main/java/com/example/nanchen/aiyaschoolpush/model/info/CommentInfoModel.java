package com.example.nanchen.aiyaschoolpush.model.info;

import com.example.nanchen.aiyaschoolpush.model.IJsonModel;

import java.io.Serializable;

/**
 * 评论信息
 *
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.model.info
 * @date 2016/11/16  16:01
 */

public class CommentInfoModel implements IJsonModel,Serializable {
    /**
     * 信息id
     */
    public int infoid;
    public int mainid;//条目主贴id
    public String username;//用户名
    public long time;//评论时间
    public String reply;// 回复的人用户名
    public String content;// 评论内容
    public UserModel commentUser;//评论人信息
    public UserModel replyUser;// 回复的人的信息


    @Override
    public String toString() {
        return "CommentInfoModel{" +
                "infoid=" + infoid +
                ", mainid=" + mainid +
                ", username='" + username + '\'' +
                ", time=" + time +
                ", reply='" + reply + '\'' +
                ", content='" + content + '\'' +
                ", commentUser=" + commentUser +
                ", replyUser=" + replyUser +
                '}';
    }
}
