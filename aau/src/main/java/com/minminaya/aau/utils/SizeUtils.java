package com.minminaya.aau.utils;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.WindowManager;

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


    /**
     * 输入起点、长度、旋转角度计算终点
     * <p>
     * 知道一个线段，一个定点，线段旋转角度求终点坐标
     * 根据极坐标系原理 x = pcog(a), y = psin(a)
     *
     * @param startPoint 起点
     * @param length     长度
     * @param angle      旋转角度
     * @return 计算结果点
     */
    private static PointF calculatPoint(PointF startPoint, float length, float angle) {
        float deltaX = (float) Math.cos(Math.toRadians(angle)) * length;
        //符合Android坐标的y轴朝下的标准，和y轴有关的统一减180度
        float deltaY = (float) Math.sin(Math.toRadians(angle - 180)) * length;
        return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);
    }


    /**
     * 通过调用
     * <p>
     * mScreenWidth = displayMetrics.widthPixels;
     * <p>
     * mScreenHeight = displayMetrics.heightPixels;
     * <p>来分别获取屏幕宽高
     *
     * @return DisplayMetrics
     */
    private static DisplayMetrics getScreenParams() {
        WindowManager wm = (WindowManager) AAUHelper.getApplication().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
}
