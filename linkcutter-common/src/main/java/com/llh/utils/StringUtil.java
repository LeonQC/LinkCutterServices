package com.llh.utils;

import com.llh.enums.PhoneCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

//    private static final String URL_REGEX = "^((https?|ftp)://)?([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)?"
//            + "((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" // IP地址
//            + "|([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,})" // 域名
//            + "(:[0-9]+)?(/.*)?$";
//
//    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
    private static final String SIMPLE_URL_REGEX = "^(https?|ftp)://[a-zA-Z0-9.-]+(\\.[a-zA-Z]{2,})?(/.*)?$";
    private static final Pattern URL_PATTERN = Pattern.compile(SIMPLE_URL_REGEX);

    public static Boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static Boolean isValidPassword(String str) {
        if(isBlank(str)){
            return false;
        }
        String regex = "^[a-zA-Z0-9!?]+$";
        return str.matches(regex);
    }

    public static Boolean isValidUsername(String str) {
        if(isBlank(str)){
            return false;
        }
        String regex = "^[a-zA-Z0-9_]+$";
        return str.matches(regex);
    }

    public static Boolean isValidNickname(String str) {
        if (isBlank(str)) {
            return false;
        }
        // 允许字母、数字、下划线和任意 Unicode 字符，但不允许其他特殊符号
        String regex = "^[\\p{L}\\p{N}_]+$";
        return str.matches(regex);
    }

    public static boolean isValidEmail(String email) {
        // 定义邮箱格式的正则表达式
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(emailRegex);

        // 如果邮箱为空，直接返回false
        if (isBlank(email)) {
            return false;
        }

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber, String prefix) {
        // 正则表达式: ^ 表示开始，\\d+ 表示一到多个数字，$ 表示结束
        if(phoneNumber == null || phoneNumber.trim().length() == 0){
            return false;
        }
        return phoneNumber.matches("\\d+")
                && PhoneCode.checkTypeValid(prefix)
                && PhoneCode.getNumberLengthByPrefix(prefix) == phoneNumber.length();
    }

    public static boolean isValidURL(String url) {
        if(url == null || isBlank(url)){
            return false;
        }
        String formattedUrl = httpAppender(url);
        Matcher matcher = URL_PATTERN.matcher(formattedUrl);
        return matcher.matches();
    }

    private static String httpAppender(String url){
        if(url.startsWith("http://") || url.startsWith("https://")){
            return url;
        }
        url = "http://" + url;
        return url;

    }


}
