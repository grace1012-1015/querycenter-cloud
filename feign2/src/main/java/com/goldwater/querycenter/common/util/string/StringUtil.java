package com.goldwater.querycenter.common.util.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 字段串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().equals("") || str.length() < 0);
    }

    /**
     * 字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        for (int i = 0; i < str.length(); i++){
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * 提取字符串中的数字
     * @param str
     * @return
     */
    public static String getNumber(String str){
        String regEx="[^a-z^A-Z^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
