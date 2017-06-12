package com.example.nanchen.aiyaschoolpush.helper.event;

import com.example.nanchen.aiyaschoolpush.model.info.InfoModel;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush
 * @date 2016/11/23  13:46
 */

public class CommunityEvent {
    private InfoModel mInfoModel;

    public CommunityEvent(InfoModel infoModel) {
        mInfoModel = infoModel;
    }
    public CommunityEvent(){}

    public InfoModel getInfoModel() {
        return mInfoModel;
    }

    public void setInfoModel(InfoModel infoModel) {
        mInfoModel = infoModel;
    }
}
