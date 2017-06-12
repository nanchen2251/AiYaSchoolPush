package com.example.nanchen.aiyaschoolpush.model;

import java.io.Serializable;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.model
 * @date 2016/10/08  11:54
 */

public class NoticeModel implements IJsonModel,Serializable{
    public long id; // 通知id
    public String content; // 内容
    public User user; // 发送人
    public long time; // 发送时间
    public boolean isIPraised; // 我是否赞了该通知
    public int praiseCount; // 总赞数
    public int commentCount; //评论数
    public int tag; // 发送类型   1——公告  2——作业  3——动态


    @Override
    public String toString() {
        return "NoticeModel{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", time=" + time +
                ", isIPraised=" + isIPraised +
                ", praiseCount=" + praiseCount +
                ", commentCount=" + commentCount +
                ", tag=" + tag +
                '}';
    }
}
