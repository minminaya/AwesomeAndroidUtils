package com.minminaya.aau.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.minminaya.aau.AAUHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

/**
 * 关于状态栏，导航栏的工具类，不考虑Android4.4及以下版本，由于手头无小米魅族机器，暂时不考虑小米魅族比较特殊的ROM版本
 * <p>
 * <p>
 * Created by Niwa on 2018-03-18.
 */
public class BarsHelper {

    public BarsHelper() {
        throw new UnsupportedOperationException("请使用静态方法");
    }

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
     * <p>
     * <p> 布局中不要设置android:fitsSystemWindows="true"</p>
     * <p>默认主题是android:theme="@style/Theme.AppCompat.Light.NoActionBar"</p>
     * <p>假如是Navigation侧滑式菜单，NavigationView布局下加上 app:insetForeground="#00000000"消除Navigation上的半透明Bar</p>
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
     * <p>设置Drawer侧滑布局时ContentView沉浸式并且侧滑NavigationView表现为全屏沉浸式（状态栏在下面）</p>
     * <p>常用于Toolbar结合使用，无Toolbar式请直接使用{@code setStatusTransparentAndColor(Activity activity, @ColorInt int color, @FloatRange(from = 0f, to = 1.0f) float alpha)}透明度设置为全透明即可</p>
     * <p>
     * <p>思路如下：</p>
     * <p>1. 先设置为内容沉浸式进入状态栏</p>
     * <p>2. 将原来contentView从其父View移除</p>
     * <p>3. 将statusView构造后的父容器添加回2的父布局</p>
     *
     * @param activity
     * @param color     状态栏颜色
     * @param contentId 当前Content 的布局id，这个是必须的
     */
    @TargetApi(21)
    public static void setDrawerLayoutTransparent(Activity activity, @ColorInt int color, @IdRes int contentId) {
        //这里的颜色意义不大，因为这里是全透明
        setStatusTransparentAndColor(activity, color, 0.0f);
        //在内容布局增加状态栏，这样侧滑菜单上就不会有了
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        //DrawerLayout 则需要在第一个子视图即内容试图中添加padding
        View parentView = contentView.getChildAt(0);

        //新建的这个线性布局将会作为新的内容布局
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        View statusBarView = new View(activity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                BarsHelper.getStatusBarOrNavigationHeight(BarsHelper.STATUS_BAR));
        //设置颜色
        statusBarView.setBackgroundColor(color);
        //添加占位状态栏到线性布局中
        linearLayout.addView(statusBarView, lp);

        //侧滑菜单
        DrawerLayout mDrawerLayout = (DrawerLayout) parentView;
        //本来的内容视图，这里是传进来的content_navigation_drawer的布局id
        View content = activity.findViewById(contentId);
        //从DrawerLayout中移除本来的内容视图
        mDrawerLayout.removeView(content);
        //添加新的内容视图到容器LinearLayout
        linearLayout.addView(content, content.getLayoutParams());
        //将容器LinearLayout设置给 DrawerLayout，这样就换了原来的无状态栏的content_navigation_drawer布局
        mDrawerLayout.addView(linearLayout, 0);
    }

    /**
     * <p>设置沉浸式布局，并且使状态栏颜色为指定，默认为不透明</p>
     * <p>表现为布局内容全沉浸，内容顶到状态栏里边，完全不透明的StatusBar条View</p>
     * <p> 布局中不要设置android:fitsSystemWindows="true"</p>
     * <p>假如是Navigation侧滑式菜单，NavigationView布局下加上 app:insetForeground="#00000000"消除Navigation上的半透明Bar</p>
     * <p>默认主题是android:theme="@style/Theme.AppCompat.Light.NoActionBar"</p>
     *
     * @param activity
     * @param color    指定颜色值，16进制颜色值
     */
    @TargetApi(21)
    public static void setStatusTransparentAndColor(Activity activity, @ColorInt int color) {
        setStatusTransparentAndColor(activity, color, 1.0f);
    }

    /**
     * <p>设置沉浸式状态栏，并且使状态栏颜色为指定，指定透明度</p>
     * <p>表现为布局内容全沉浸，内容顶到状态栏里边</p>
     * <p> 布局中不要设置android:fitsSystemWindows="true"</p>
     * <p>默认主题是android:theme="@style/Theme.AppCompat.Light.NoActionBar"</p>
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
     * <p>设置当前的导航栏颜色</p>
     *
     * @param activity
     * @param color    16进制颜色值
     */
    public static void setNavigationBarColor(Activity activity, @ColorInt int color) {
        activity.getWindow().setNavigationBarColor(color);
    }

    /**
     * <p>粘性沉浸式模式</p>
     * <p>状态栏和导航栏会自动隐藏，适用于视频和图像播放界面</p>
     */
    public static void setNavigationImmersive(Activity activity) {
        View mDecorView = activity.getWindow().getDecorView();
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide 导航栏
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide 状态栏
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
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
     * 隐藏导航栏
     */
    public static void hideNavigationBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int options = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(options);
    }

    /**
     * 设置导航栏是否可见，效果表现为，透明背景沉浸式导航栏，上滑不会顶开内容
     *
     * @param activity  activity
     * @param isVisible {@code true}: 可见<br>{@code false}: 不可见
     */
    public static void setNavBarVisibility(final Activity activity, boolean isVisible) {
        if (isVisible) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            View decorView = activity.getWindow().getDecorView();
            int visibility = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(visibility & ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    /**
     * 显示导航栏
     */
    public static void showNavigationBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int options = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(options);
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
     * <p>设置当前状态栏文字UI为黑色或者白色，Android6.0默认为白色</p>
     * <p>
     * <strong>Note:</strong>原生Android6.0及以上测试通过
     * <p>手头无小米，魅族机器，无法测试其适用性，谨慎适用</p>
     *
     * @param activity
     * @param isDarkStatusUI 是否黑色状态栏UI及文字，true为黑色
     */
    @TargetApi(23)
    public static void setAndroidtStatusBarUIAndTextColor(Activity activity, boolean isDarkStatusUI) {
        View decorView = activity.getWindow().getDecorView();
        if (isDarkStatusUI) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
    /**  很奇怪，这里测试时会保持一个boolean值。原因未明
     * <p>判断当前Activity的导航栏可见性</p>
     *
     * @param activity 需要判断的当前的Activity
     * @return {@code true} ：NavigationBar可见<br>{@code false} ：NavigationBar不可见
     */
   /* public static boolean isNavigationBarVisible(Activity activity) {
        //当前Window的LayoutParams的标签flag
        int flag = activity.getWindow().getAttributes().flags;
        return (flag & View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION) == 0;
    }*/

    /**
     * <p>判断手机是否支持导航栏</p>
     *
     * @return true 表示当前设备是否支持导航栏
     */
    public static boolean checkDeviceSupportNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

    /**
     * <p>作为状态栏和导航栏的标记</p>
     */
    @IntDef({STATUS_BAR, NAVIGATION_BAR, NO_STATUS_AND_NO_NAVIGATION})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BarType {
    }

}
