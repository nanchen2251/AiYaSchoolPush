package com.example.nanchen.aiyaschoolpush.helper.event;

import com.example.nanchen.aiyaschoolpush.model.info.InfoModel;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush
 * @date 2016/11/22  09:58
 */

public class NoticeEvent {
    private InfoModel mInfoModel;
    private int mCommentCount;
    public NoticeEvent(InfoModel infoModel,int commentCount){
        this(infoModel);
        this.mCommentCount = commentCount;
    }

    public NoticeEvent(InfoModel infoModel){
        this.mInfoModel = infoModel;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public void setCommentCount(int commentCount) {
        mCommentCount = commentCount;
    }

    public InfoModel getInfoModel() {
        return mInfoModel;
    }

    public void setInfoModel(InfoModel infoModel) {
        mInfoModel = infoModel;
    }
}
