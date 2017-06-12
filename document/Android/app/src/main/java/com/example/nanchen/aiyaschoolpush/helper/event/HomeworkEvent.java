package com.example.nanchen.aiyaschoolpush.helper.event;

import com.example.nanchen.aiyaschoolpush.model.info.InfoModel;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush
 * @date 2016/11/22  13:47
 */

public class HomeworkEvent {
    private InfoModel mInfoModel;

    public HomeworkEvent(InfoModel infoModel){
        this.mInfoModel = infoModel;
    }

    public HomeworkEvent(){}

    public InfoModel getInfoModel() {
        return mInfoModel;
    }

    public void setInfoModel(InfoModel infoModel) {
        mInfoModel = infoModel;
    }
}
