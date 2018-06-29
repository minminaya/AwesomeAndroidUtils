package com.minminaya.aau.utils;


import android.content.Context;

import java.io.File;

/**
 * 清除缓存的工具类
 *
 * @author minminaya
 * @email minminaya@gmail.com
 * @time Created by 2018/6/29 13:44
 */
public class CacheCleanUtil {
    /**
     * <p>清除内部的缓存，默认情况是Application的包名的默认Cache目录{@link Context#getCacheDir()}</p>
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * <p>清除整个目录下的文件</p>
     *
     * @param directory 目录
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }
}
