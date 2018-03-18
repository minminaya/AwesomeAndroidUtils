package com.minminaya.aau;

import android.app.Application;

/**
 * Created by Niwa on 2018-03-18.
 */

public class AAUHelper {

    private static Application application = null;

    /**
     * <p>初始化Helper工具类</p>
     *
     * @param application APP的Application对象
     */
    public static void initial(Application application) {
        AAUHelper.application = application;
    }

    /**
     * <p>得到Application对象</p>
     *
     * @return Application对象
     */
    public static Application getApplication() {
        if (null != application) {
            return application;
        }
        throw new NullPointerException("还没调用initial()方法将Application传进来(you should call initial() for Application Context)");
    }
}
