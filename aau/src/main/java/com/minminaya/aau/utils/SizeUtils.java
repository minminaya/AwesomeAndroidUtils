package com.minminaya.aau.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.minminaya.aau.AAUHelper;

/**
 * <p>Created by LGM on 2018-04-10 23:00</p>
 * <p>Email:minminaya@gmail.com</p>
 */
public class SizeUtils {

    /**
     * <p>DP转为PX</p>
     * 加入0,5是为了保证转为int类型后四舍五入结果正确
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(float dpValue) {
        DisplayMetrics displayMetrics = AAUHelper.getApplication().getResources().getDisplayMetrics();
        float scale = displayMetrics.density;
        //加入0,5是为了保证转为int类型后四舍五入结果正确
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * <p>px转为dp</p>
     * 加入0,5是为了保证转为int类型后四舍五入结果正确
     *
     * @param pxValue dp值
     * @return px值
     */
    public static int px2dp(float pxValue) {
        DisplayMetrics displayMetrics = AAUHelper.getApplication().getResources().getDisplayMetrics();
        float scale = displayMetrics.density;
        //加入0,5是为了保证转为int类型后四舍五入结果正确
        return (int) (pxValue / scale + 0.5f);
    }

}
