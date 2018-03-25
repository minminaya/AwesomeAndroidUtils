package com.minminaya.aau.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

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


    /**
     * <p>通过反射获取当前状态栏或者导航栏的高度,单位为px</p>
     *
     * @param statusOrNavigationMark 状态栏或者导航栏的标记
     * @return 当前状态栏或者导航栏的高度，如果为0，说明获取失败
     */
    public static int getStatusBarOrNavigationHeight(@BarType int statusOrNavigationMark) {
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

    @TargetApi(19)
    public static void addStatusBarView(Activity activity, @ColorInt int color, @FloatRange(from = 0.0f, to = 1.0f) float statusBarAlpha) {
        ViewGroup contentView = activity.findViewById(android.R.id.content);
        View statusView = new View(activity);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarOrNavigationHeight(STATUS_BAR_HEIGHT));

        statusView.setBackgroundColor(color);
        statusView.setAlpha(statusBarAlpha);

        contentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        contentView.addView(statusView, layoutParams);
    }

    /**
     * <p>判断当前Activity的可见性</p>
     * <p>策略是，先获取当前Activity的Window的flag，判断flag是否是全屏，如果和 {@link WindowManager.LayoutParams.FLAG_FULLSCREEN}与运算后相等说明当前是状态栏是可见的</p>
     *
     * @param activity 需要判断的当前的Activity
     * @return {@code true} ：StatuBar可见<br>{@code false} ：StatuBar不可见
     */
    public static boolean isStatusBarVisible(Activity activity) {
        //当前Window的LayoutParams的标签flag
        int flag = activity.getWindow().getAttributes().flags;
        return (flag & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0;
    }


    /**
     * <p>作为状态栏和导航栏的标记</p>
     */
    @IntDef({STATUS_BAR_HEIGHT, NAVIGATION_BAR_HEIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BarType {
    }
}
