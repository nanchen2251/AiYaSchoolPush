package com.example.nanchen.aiyaschoolpush.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.utils
 * @date 2016/10/08  13:45
 */

public class TimeUtils {

    /**
     * 时间戳转换为字符串时间
     * @param longTime
     * @return
     */
    public static String longToDateTime(long longTime){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(longTime*1000));
    }

    /**
     * 字符串时间转换为时间戳
     * @param strDate
     * @return
     */
    public static String strToLongDate(String strDate){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            long longDate = sdf.parse(strDate).getTime();
            return longDate+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
