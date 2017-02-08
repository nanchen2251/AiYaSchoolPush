package com.example.nanchen.aiyaschoolpush;

import com.example.nanchen.aiyaschoolpush.utils.UIUtil;

/**
 * 自定义异常类
 *
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush
 * @date 2016/12/15  17:07
 */

public class VideoException extends RuntimeException {

    public VideoException(String desc) {
        UIUtil.showToast(desc);

    }
}
