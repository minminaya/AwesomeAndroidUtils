package com.minminaya.aau.utils;

import android.graphics.Color;

/**
 * 颜色的帮助类
 * Created by Niwa on 2018-03-26.
 */

public class ColorHelper {

    /**
     * <p>将给定的颜色值和透明度装换为新的颜色值</p>
     *
     * @param color 原始颜色值
     * @param alpha 原始透明度，一般为给定的百分比，比如60%值为0.6
     * @return 返回转换后的颜色值
     */
    public static int changeArg(int color, float alpha) {

        int destAlpha = (int) (alpha * 255);
        int red = (color & 0x00ff0000) >>> 16;
        int green = (color & 0x0000ff00) >> 8;
        int blue = (color & 0x000000ff);
        return Color.argb(destAlpha, red, green, blue);
    }
}
