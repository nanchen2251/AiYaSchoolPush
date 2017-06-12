package com.example.nanchen.aiyaschoolpush.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本处理工具类
 *
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.utils
 * @date 2016/09/12  15:25
 */
public class TextUtil {

    /**
     * 判断是否是手机号码
     *
     * @param mobile 手机号码
     */
    public static boolean isMobile(String mobile) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 虚拟:170... 总结起来就是第一位必定为1，第二位必定为3或5或7或8，其他位置的可以为0-9
         */
        String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return !TextUtils.isEmpty(mobile) && mobile.matches(telRegex);
    }

    /**
     * 判断是否是纯中文字符串
     * @param name  需要检测
     * @return
     */
    public static boolean isChinese(String name) {
        boolean result = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChineseChar(cTemp[i])) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 判断是否是中文字符
     */
    public static boolean isChineseChar(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (	ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS ||
                ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS ||
                ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A ||
                ub == Character.UnicodeBlock.GENERAL_PUNCTUATION ||
                ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION ||
                ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS	) {
            return true;
        }

        return false;
    }

    /**
     * 检测是否包含中文
     */
    public static boolean isContainChinese(String str) {
        String regEx = "[\\u4E00-\\u9FA5]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否只包含英文字母和数字
     */
    public static boolean isLetterOrDigitOnly(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }
}
