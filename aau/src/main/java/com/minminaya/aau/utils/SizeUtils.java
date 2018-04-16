package com.minminaya.aau.utils;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
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
     * <p>知道一个线段，它的定点，线段旋转角度(线段起始角度为Android坐标系90°)求旋转后的终点坐标</p>
     * <p><strong>NOTICE:</strong>初始角度为90度</p>
     * <p>
     * <p>
     * <p>
     * 根据极坐标系原理 x = pcog(a), y = psin(a)
     *
     * @param startPoint 起点
     * @param length     长度
     * @param angle      旋转角度
     * @return 计算结果点
     */
    public static PointF calculatPoint(PointF startPoint, float length, float angle) {
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
    public static DisplayMetrics getScreenParams() {
        WindowManager wm = (WindowManager) AAUHelper.getApplication().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }


    /**
     * 测量View的尺寸，模仿ViewGroup测量过程
     *
     * @param view
     * @return 返回测量之后的view宽度和高度的数组
     */
    public static int[] measureView(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            //初始化lp为宽最大，高为适应内容
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        //最大宽度模式直接测量
        int widthMeasureSpec = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);

        int lpHeight = layoutParams.height;
        int heightMeasureSpec;
        if (lpHeight > 0) {
            //如果高度定义
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }


    /*-----------------------------MATH计算-------------------------------------*/


    /**
     * sin正弦函数
     *
     * @param angle 角度制
     */
    public static float sin(int angle) {
        return (float) Math.sin(Math.toRadians(angle));
    }

    /**
     * cos余弦函数
     *
     * @param angle 角度制
     * @return 返回余弦值
     */
    public static float cos(int angle) {
        return (float) Math.cos(Math.toRadians(angle));
    }

}
