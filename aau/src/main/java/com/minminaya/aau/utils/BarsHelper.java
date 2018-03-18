package com.minminaya.aau.utils;

import android.content.res.Resources;
import android.support.annotation.IntDef;

import com.minminaya.aau.AAUHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 关于状态栏，导航栏的工具类
 * Created by Niwa on 2018-03-18.
 */

public class BarsHelper {

    public final static int STATUS_BAR_HEIGHT = 0;
    public final static int NAVIGATION_BAR_HEIGHT = 1;

    @IntDef({STATUS_BAR_HEIGHT, NAVIGATION_BAR_HEIGHT})
    @Retention(RetentionPolicy.CLASS)
    public @interface barType {
    }

    /**
     * <p>通过反射获取当前状态栏或者导航栏的高度,单位为px</p>
     *
     * @param statusOrNavigationMark 状态栏或者导航栏的标记
     * @return 当前状态栏或者导航栏的高度，如果为0，说明获取失败
     */
    public static int getStatusBarOrNavigationHeight(@barType int statusOrNavigationMark) {
        int resultId;
        Resources resources = AAUHelper.getApplication().getResources();

        if (statusOrNavigationMark == STATUS_BAR_HEIGHT) {
            //如果是状态栏
            resultId = resources.getIdentifier("status_bar_height", "dimen", "android");
        } else {
            resultId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        }
        //如果不等于0，说明找到了指定的属性值，那么返回当前的高度
        return resultId > 0 ? resources.getDimensionPixelSize(resultId) : 0;
    }
}
