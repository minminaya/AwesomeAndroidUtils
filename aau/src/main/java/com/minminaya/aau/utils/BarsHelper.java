package com.minminaya.aau.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.minminaya.aau.AAUHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 关于状态栏，导航栏的工具类，不考虑Android4.4及以下版本
 * Created by Niwa on 2018-03-18.
 */

public class BarsHelper {

    public final static int STATUS_BAR_HEIGHT = 0;
    public final static int NAVIGATION_BAR_HEIGHT = 1;


    /**
     * <p>沉浸式状态栏一：</p>
     * <p>1.把屏幕全屏化，剩下状态栏</p>
     * <p>2.新建一个指定颜色和透明度的状态栏大小的View在放置在DecorView中，将contentView向下偏移状态栏高度</p>
     *
     * @param activity       所在Activity
     * @param color          十六进制颜色值
     * @param statusBarAlpha 透明度，设置为不透明，与contentView可融为一体
     */
    @TargetApi(21)
    public static void addStatusBarViewAtDecorView(Activity activity, @ColorInt int color, @FloatRange(from = 0.0f, to = 1.0f) float statusBarAlpha) {
        Window window = activity.getWindow();
        //两个flag结合使用，表现为应用的主体内容占用系统状态栏的空间
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        //设置透明背景Bars
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //将DecorView的ContentView向下偏移状态栏高度
        activity.getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, getStatusBarOrNavigationHeight(STATUS_BAR_HEIGHT), 0, 0);
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        //新建一个状态栏大小的View
        View statusView = new View(activity);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarOrNavigationHeight(STATUS_BAR_HEIGHT));
        statusView.setBackgroundColor(color);
        statusView.setAlpha(statusBarAlpha);
        //在DecorView中添加statusView
        decorView.addView(statusView, layoutParams);
        //去除透明罩罩
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }


    /**
     * <p>设置沉浸式布局，并且使状态栏颜色为指定</p>
     */
    @TargetApi(21)
    public static void setStatusTransparent(Activity activity, @ColorInt int color) {
        Window window = activity.getWindow();
        //两个flag结合使用，表现为应用的主体内容占用系统状态栏的空间
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        //设置透明背景Bars
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ColorHelper.changeArg(color, 0.5f));

    }


    /**
     * <p>通过反射获取当前状态栏或者导航栏的高度,单位为px</p>
     *
     * @param statusOrNavigationMark 状态栏或者导航栏的标记
     * @return 当前状态栏或者导航栏的高度，如果为-1，说明获取失败
     */
    public static int getStatusBarOrNavigationHeight(@BarType int statusOrNavigationMark) {
        int resultId = -1;
        Resources resources = AAUHelper.getApplication().getResources();
        if (statusOrNavigationMark == STATUS_BAR_HEIGHT) {
            //如果是状态栏
            resultId = resources.getIdentifier("status_bar_height", "dimen", "android");
        } else {
            resultId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        }
        //如果不等于0，说明找到了指定的属性值，那么返回当前的高度
        return resultId > 0 ? resources.getDimensionPixelSize(resultId) : -1;
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
