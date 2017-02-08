package com.example.nanchen.aiyaschoolpush.model;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.api
 * @date 2016/11/09  11:22
 */

public class LoginUser extends User{

    private static LoginUser loginUser;

    private LoginUser(){
    }

    public static LoginUser getInstance(){
        if (loginUser == null){
            loginUser = new LoginUser();
        }
        return loginUser;
    }




}
