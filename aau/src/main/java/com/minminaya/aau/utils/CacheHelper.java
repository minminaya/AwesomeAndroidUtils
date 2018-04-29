package com.minminaya.aau.utils;

import android.app.Notification;
import android.util.ArrayMap;

/**
 * <p>Created by LGM on 2018-03-31 23:15</p>
 * <p>Email:minminaya@gmail.com</p>
 */
public class CacheHelper {

    private String mCacheName;
    private long mMaxSize;
    private long mMaxCount;

    private static volatile CacheHelper defaultInstance;

    private CacheHelper() {
        throw new UnsupportedOperationException("不要实例化");
    }

    /**
     * <p>这里设计成全局单例(DCL单例模式保证线程安全)</p>
     */
    public static CacheHelper getINSTANCE() {
        CacheHelper cacheHelper = defaultInstance;
        if (cacheHelper == null) {
            synchronized (CacheHelper.class) {
                if (cacheHelper == null) {
                    cacheHelper = CacheHelper.defaultInstance = new CacheHelper();
                }
            }
        }
        return cacheHelper;
    }

    /**
     * 设计为Builder模式，方便参数的设置
     */
    public class CacheHelperBuilder {
        public CacheHelperBuilder() {

        }

        public CacheHelperBuilder setCacheName(String cacheName) {
            defaultInstance.mCacheName = cacheName;
            return this;
        }
    }

    /**
     * 数据转换类
     */
    public static class DataTransfer {

    }
}
