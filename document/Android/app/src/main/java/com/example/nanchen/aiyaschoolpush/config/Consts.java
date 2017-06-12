package com.example.nanchen.aiyaschoolpush.config;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.config
 * @date 2016/11/09  11:34
 *
 * 系统常量定义类，此类仅放常量定义，不放任何逻辑代码
 *
 */

public final class Consts {
    /**
     * 内网API接口主机名
     */
//    public final static String API_SERVICE_HOST = "http://10.1.1.119:80/AiYaSchoolPush";
    /**
     * 外网API接口主机名
     */
    public final static String API_SERVICE_HOST = "http://azhinj.ticp.io:10277/AiYaSchoolPush";

    /**
     * 用户是教师
     */
    public final static int USER_TYPE_TEACHER = 2;
    /**
     * 用户是学生或家长
     */
    public final static int USER_TYPE_STUDENT = 1;
}
