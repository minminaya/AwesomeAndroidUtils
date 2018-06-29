package com.minminaya.aau.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * <p><Strong>日志管理的类</p></Strong>
 * <p>
 * <p>开发阶段设置LOGLEVEL = 6</p>
 * <p>发布阶段LOGLEVEL = -1</p>
 *
 * @author minminaya
 * @email minminaya@gmail.com
 * @time Created by 2018/6/29 13:50
 */
public class Logger {

    private static int LOGLEVEL = 6;
    private static int VERBOSE = 1;
    private static int DEBUG = 2;
    private static int INFO = 3;
    private static int WARN = 4;
    private static int ERROR = 5;

    public static void setDevelopMode(boolean flag) {
        if (flag) {
            LOGLEVEL = 6;
        } else {
            LOGLEVEL = -1;
        }
    }

    public static void v(String tag, String msg) {
        if (LOGLEVEL > VERBOSE && !TextUtils.isEmpty(msg)) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LOGLEVEL > DEBUG && !TextUtils.isEmpty(msg)) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LOGLEVEL > INFO && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOGLEVEL > WARN && !TextUtils.isEmpty(msg)) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOGLEVEL > ERROR && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }
}
