package com.minminaya.aau.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 范型单例
 * <p>Created by LGM on 2018/4/11 16:22</p>
 * <p>Email:minminaya@gmail.com</p>
 */
public class SingletonHelper {

    /**
     * <p>存储当前对象的同步集合，使用此集合，跟加了volatile的DCL单例模式有异曲同工之妙</p>
     */
    private static final ConcurrentHashMap<Class, Object> CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();

    /**
     * <p>获取单例对象</p>
     * <p>这里用同步集合来保证同步</p>
     *
     * @param clzss 需要实例化的类名
     * @return 返回实例化的对象
     */
    public static <T> T getSingleton(Class<T> clzss) {

        Object object = CONCURRENT_HASH_MAP.get(clzss);

        if (object == null) {

            //同步锁保证map安全操作
            synchronized (CONCURRENT_HASH_MAP) {

                //无构造方法的构造器
                try {
                    object = clzss.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                //存进map里
                CONCURRENT_HASH_MAP.put(clzss, object);

            }
        }
        return (T) object;
    }

    /**
     * <p>移除当前的同步集合中的单例对象</p>
     *
     * @param clzss 要移除的对象
     */
    public static <T> void removeSingleton(Class<T> clzss) {
        CONCURRENT_HASH_MAP.remove(clzss);
    }
}
