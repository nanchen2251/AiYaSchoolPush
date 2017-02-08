package com.example.nanchen.aiyaschoolpush.model.info;

import com.example.nanchen.aiyaschoolpush.model.IJsonModel;

import java.io.Serializable;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.model.info
 * @date 2016/11/16  14:49
 */

public class UserModel implements IJsonModel,Serializable {
    public String username;
    public String avatar;
    public String nickname;
}
