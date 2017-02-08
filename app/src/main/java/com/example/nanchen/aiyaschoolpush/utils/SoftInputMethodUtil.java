package com.example.nanchen.aiyaschoolpush.utils;

import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.nanchen.aiyaschoolpush.App;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.utils
 * @date 2016/10/24  14:37
 */

public class SoftInputMethodUtil {

    public static boolean isHideInput(View v, MotionEvent ev) {
        if (v != null ) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public static void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager imm = (InputMethodManager) App.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(token, 0);
        }
    }
}
