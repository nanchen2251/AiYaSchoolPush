package com.example.nanchen.aiyaschoolpush.model;

import java.io.Serializable;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.model
 * @date 2016/10/24  16:16
 */

public class CommentModel implements IJsonModel,Serializable {
    /**
     * 评论ID
     */
    public long id;

    /**
     * 评论发起者
     */
    public User sender;

    /**
     * 要回复的用户信息，非回复类型为null
     */
    public User replyto;

    /**
     * 评论内容
     */
    public String content;

    /**
     * 评论时间戳
     */
    public long createTs;
}
