package com.minminaya.aau.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.FontRes;
import android.support.annotation.IdRes;
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

    public final static int STATUS_BAR = 0;
    public final static int NAVIGATION_BAR = 1;
    /**
     * 表示不起作用
     */
    public final static int NO_STATUS_AND_NO_NAVIGATION = -1;

    /**
     * <p>添加沉浸式状态栏，表现为普通同一颜色式（最常用）和渐变式状态栏（和Toolbar搭配使用【设置渐变背景】起到渐变式的额头效果）</p>
     * <p>
     * <p>沉浸式状态栏一：</p>
     * <p>1.把屏幕全屏化，剩下状态栏</p>
     * <p>2.新建一个指定颜色和透明度的状态栏大小的View在放置在DecorView中，将contentView向下偏移状态栏高度</p>
     *
     * @param activity            所在Activity
     * @param isNormalStatusBar   true代表当前是普通的状态栏，false代表当前是渐变式状态栏
     * @param gradientDrawableRes 渐变式状态栏的渐变文件，当isNormalStatusBar为false时起作用，不使用时可调-1
     * @param normalStatusColor   十六进制颜色值，当isNormalStatusBar为true时起作用不使用该值时可调-1
     * @param statusBarAlpha      透明度，设置为不透明，与contentView可融为一体
     */
    @TargetApi(21)
    public static void addGradientOrNormalStatusBarViewAtDecorView(Activity activity, boolean isNormalStatusBar, @IdRes int gradientDrawableRes, @ColorInt int normalStatusColor, @FloatRange(from = 0.0f, to = 1.0f) float statusBarAlpha) {
        Window window = activity.getWindow();
        //两个flag结合使用，表现为应用的主体内容占用系统状态栏的空间
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        //设置透明背景Bars
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //将DecorView的ContentView向下偏移状态栏高度
        activity.getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, getStatusBarOrNavigationHeight(STATUS_BAR), 0, 0);
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        //新建一个状态栏大小的View
        View statusView = new View(activity);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarOrNavigationHeight(STATUS_BAR));
        if (!isNormalStatusBar) {
            //如果是渐变式状态栏，生成一个Drawable
            @SuppressLint("ResourceType") Drawable drawable = activity.getResources().getDrawable(gradientDrawableRes, null);
            statusView.setBackground(drawable);
        } else {
            statusView.setBackgroundColor(normalStatusColor);
        }
        statusView.setAlpha(statusBarAlpha);
        //在DecorView中添加statusView
        decorView.addView(statusView, layoutParams);
        //去除透明罩罩
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * <p>设置状态栏或者导航栏颜色</p>
     *
     * @param activity
     * @param color                  指定颜色值，16进制颜色值
     * @param alpha                  指定透明度，百分比形式，例如百分之60，为0.6
     * @param isDoubleSetting        是否同时设置状态栏颜色和导航栏颜色，true代表同时设置
     * @param statusOrNavigationMark 导航栏和状态栏标记，当isDoubleSetting为false时才起作用
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int color, @FloatRange(from = 0f, to = 1.0f) float alpha, boolean isDoubleSetting, @BarType int statusOrNavigationMark) {
        Window window = activity.getWindow();
        int endColor = ColorHelper.changeArg(color, alpha);
        if (isDoubleSetting) {
            window.setStatusBarColor(endColor);
            window.setNavigationBarColor(endColor);
        } else if (statusOrNavigationMark == BarsHelper.NAVIGATION_BAR) {
            window.setNavigationBarColor(endColor);
        } else if (statusOrNavigationMark == BarsHelper.STATUS_BAR) {
            window.setStatusBarColor(endColor);
        }
    }

    /**
     * <p>设置沉浸式状态栏，并且使状态栏颜色为指定，指定透明度</p>
     * <p>表现为布局内容全沉浸，内容顶到状态栏里边</p>
     *
     * @param activity
     * @param color    指定颜色值，16进制颜色值
     * @param alpha    指定透明度，百分比形式，例如百分之60，为0.6
     */
    @TargetApi(21)
    public static void setStatusTransparentAndColor(Activity activity, @ColorInt int color, @FloatRange(from = 0f, to = 1.0f) float alpha) {
        Window window = activity.getWindow();
        //两个flag结合使用，表现为应用的主体内容占用系统状态栏的空间
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        //设置透明背景Bars
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ColorHelper.changeArg(color, alpha));
    }

    /**
     * <p>设置沉浸式布局，并且使状态栏颜色为指定，默认为不透明</p>
     * <p>表现为布局内容全沉浸，内容顶到状态栏里边，完全不透明的StatusBar条View</p>
     *
     * @param activity
     * @param color    指定颜色值，16进制颜色值
     */
    @TargetApi(21)
    public static void setStatusTransparentAndColor(Activity activity, @ColorInt int color) {
        setStatusTransparentAndColor(activity, color, 1.0f);
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
        if (statusOrNavigationMark == STATUS_BAR) {
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
    @IntDef({STATUS_BAR, NAVIGATION_BAR, NO_STATUS_AND_NO_NAVIGATION})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BarType {
    }

}
