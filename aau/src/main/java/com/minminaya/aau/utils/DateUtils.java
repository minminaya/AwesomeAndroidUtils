package com.minminaya.aau.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间的工具类
 *
 * @author minminaya
 * @email minminaya@gmail.com
 * @time Created by 2018/6/29 13:50
 */
public class DateUtils {

    private final static String DEFAULT_DATE_STR = "yyyy年MM月dd日HH:mm:ss";

    /**
     * 构建以时间统一的命名
     *
     * @param pattern 时间的格式，具体请查看{@link SimpleDateFormat}JDK文档
     */
    public static String getTiemStr(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
