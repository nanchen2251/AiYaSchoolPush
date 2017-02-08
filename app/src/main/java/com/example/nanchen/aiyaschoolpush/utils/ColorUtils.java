package com.example.nanchen.aiyaschoolpush.utils;

import android.graphics.Color;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.utils
 * @date 2016/12/08  09:09
 */

public class ColorUtils {
    public static int getCourseBgColor(int colorFlag) {
        switch (colorFlag) {
            case 0:
                return Color.parseColor("#95e987");
            case 1:
                return Color.parseColor("#ffb67e");
            case 2:
                return Color.parseColor("#8cc7fe");
            case 3:
                return Color.parseColor("#7ba3eb");
            case 4:
                return Color.parseColor("#e3ade8");
            case 5:
                return Color.parseColor("#f9728b");
            case 6:
                return Color.parseColor("#85e9cd");
            case 7:
                return Color.parseColor("#f5a8cf");
            case 8:
                return Color.parseColor("#a9e2a0");
            case 9:
                return Color.parseColor("#70cec7");
            default:
                return Color.GRAY;
        }
    }
}
