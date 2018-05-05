package com.minminaya.aau.utils;

import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.util.SimpleArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.minminaya.aau.AAUHelper;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>Created by LGM on 2018-03-31 23:15</p>
 * <p>Email:minminaya@gmail.com</p>
 */
public class CacheHelper {

    private static final long DEFAULT_MAX_SIZE = Long.MAX_VALUE;
    private static final int DEFAULT_MAX_COUNT = Integer.MAX_VALUE;

    public static final int SEC = 1;
    public static final int MIN = 60;
    public static final int HOUR = 3600;
    public static final int DAY = 86400;

    private CacheManager mCacheManager;
    private static final SimpleArrayMap<String, CacheHelper> CACHE_MAP = new SimpleArrayMap<>();


    private CacheHelper(File cacheDirFile, long maxSize, int maxCount) {
        if (!cacheDirFile.exists() && !cacheDirFile.mkdir()) {
            throw new RuntimeException("can't establish dir in " + cacheDirFile.getAbsolutePath());
        }
        mCacheManager = new CacheManager(cacheDirFile, maxSize, maxCount);
    }


    public static CacheHelper getInstance() {
        return getInstance("", DEFAULT_MAX_SIZE, DEFAULT_MAX_COUNT);

    }

    public static CacheHelper getInstance(String cacheName) {
        return getInstance(cacheName, DEFAULT_MAX_SIZE, DEFAULT_MAX_COUNT);
    }

    public static CacheHelper getInstance(long maxSize, int maxCount) {
        return getInstance("", maxSize, maxCount);
    }

    public static CacheHelper getInstance(String cacheName, long maxSize, int maxCount) {
        if (cacheName.isEmpty()) {
            cacheName = "CacheHelperFile";
        }
        File cacheDirFile = new File(AAUHelper.getApplication().getCacheDir(), cacheName);
        return getInstance(cacheDirFile, maxSize, maxCount);
    }

    public static CacheHelper getInstance(File cacheDirFile, long maxSize, int maxCount) {

        String cacheHelperKey = "_" + cacheDirFile.getAbsolutePath() + "_" + Process.myPid();
        CacheHelper cacheHelper = CACHE_MAP.get(cacheHelperKey);
        if (cacheHelper == null) {
            cacheHelper = new CacheHelper(cacheDirFile, maxSize, maxCount);
            CACHE_MAP.put(cacheHelperKey, cacheHelper);
        }
        return cacheHelper;
    }

    public void put(@NonNull String key, @NonNull byte[] value, int saveTime) {
        if(value.length<=0) {
            return;
        }
        if(saveTime>=0){
            //todo 将日期封装到byte数组前面
        }
        
    }


    /**
     * 数据转换类
     */
    public static class DataTransfer {

    }

    /**
     * <p>用来管理Cache的增删改查变化，以及File的大小，修改时间</p>
     */
    private class CacheManager {
        private long mMaxSize;
        private int mMaxCount;
        private File mCacheFile;
        private AtomicLong mCacheSize;
        private AtomicInteger mCacheCount;

        //File为键，修改时间为值
        private final ConcurrentHashMap<File, Long> mLastUsageDates = new ConcurrentHashMap<>();


        public CacheManager(File cacheFile, long maxSize, int maxCount) {
            this.mCacheFile = cacheFile;
            this.mMaxSize = maxSize;
            this.mMaxCount = maxCount;

            mCacheCount = new AtomicInteger();
            mCacheSize = new AtomicLong();
            caculateCacheSizeAndCacheCountAndModifedTime();
        }

        /**
         * <p>计算FIle的长度，修改次数和时间</p>
         */
        private void caculateCacheSizeAndCacheCountAndModifedTime() {
            ExecutorService singleThread = Executors.newSingleThreadExecutor();
            singleThread.execute(new Runnable() {
                @Override
                public void run() {
                    int size = 0;
                    int count = 0;
                    File[] cacheFiles = mCacheFile.listFiles();
                    if (cacheFiles != null) {
                        for (File file :
                                cacheFiles) {
                            size += file.length();
                            count += 1;
                            mLastUsageDates.put(file, file.lastModified());
                        }
                        mCacheSize.getAndAdd(size);
                        mCacheCount.getAndAdd(count);
                    }
                }
            });
        }


    }
}
