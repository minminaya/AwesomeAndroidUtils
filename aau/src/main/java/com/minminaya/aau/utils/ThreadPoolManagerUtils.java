package com.minminaya.aau.utils;

import android.support.v4.util.ArrayMap;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>线程池管理类</p>
 * <p></p>
 * <p><Strong>Notice：</Strong></p>
 * <p>
 * <p>1.可以申请多个线程池</p>
 * <p>2.每次执行线程需要指定特定的线程池执行</p>
 * <p>3.建议先指定线程的池的key值，避免创建多余的线程池</p>
 * <p>4.<Strong>一定要执行 {@link ThreadPoolManagerUtils#setNewBuilder(Builder)}</Strong>去初始化线程池的默认参数</p>
 *
 * @author minminaya
 * @email minminaya@gmail.com
 * @time Created by 2018/6/29 13:51
 */
public class ThreadPoolManagerUtils {

    //几个代表线程池的静态常量
    public final static String THREAD_POOL_ONE = "thread_pool_one";
    public final static String THREAD_POOL_TWO = "thread_pool_two";
    public final static String THREAD_POOL_THREE = "thread_pool_three";
    public final static String THREAD_POOL_FOUR = "thread_pool_four";

    //确保该类只有一个实例对象
    private static ThreadPoolManagerUtils sInstance;
    //线程池集合
    private static ArrayMap<String, ThreadPoolExecutor> mThreadPoolExecutorArrayMaps;

    //线程池的各个参数
    private int mCorePoolSize;
    private int mMaximumPoolSize;
    private long mKeepAliveTime;
    private TimeUnit mUnit;
    private BlockingQueue<Runnable> mWorkQueue;
    private ThreadFactory mThreadFactory;
    private RejectedExecutionHandler mHandler;

    public synchronized static ThreadPoolManagerUtils getsInstance() {
        if (sInstance == null) {
            sInstance = new ThreadPoolManagerUtils();
            mThreadPoolExecutorArrayMaps = new ArrayMap<>();
        }
        return sInstance;
    }

    /**
     * <p>给ThreadPoolManagerUtils类设置Builder，可重复设置用于添加多个线程池，一般情况下只会创建一个线程池</p>
     *
     * @param builder
     */
    public ThreadPoolManagerUtils setNewBuilder(Builder builder) {
        this.mCorePoolSize = builder.mCorePoolSize;
        this.mMaximumPoolSize = builder.mMaximumPoolSize;
        this.mKeepAliveTime = builder.mKeepAliveTime;
        this.mUnit = builder.mUnit;
        this.mWorkQueue = builder.mWorkQueue;
        this.mThreadFactory = builder.mThreadFactory;
        this.mHandler = builder.mHandler;
        return this;
    }

    /**
     * 使用线程池，线程池中线程的创建完全是由线程池自己来维护的，我们不需要创建任何的线程
     * <p>通过指定的线程池来执行线程</p>
     *
     * @param threadPoolKey 指定线程池的名字 ，如{@link ThreadPoolManagerUtils#THREAD_POOL_ONE,ThreadPoolManagerUtils#THREAD_POOL_TWO,ThreadPoolManagerUtils#THREAD_POOL_THREE,ThreadPoolManagerUtils#THREAD_POOL_FOUR}
     * @param runnable      在线程池里面运行的线程
     */
    public void executeRunnableBySpeciallyPool(String threadPoolKey, Runnable runnable) {
        ThreadPoolExecutor executor = mThreadPoolExecutorArrayMaps.get(threadPoolKey);
        if (executor == null) {
            executor = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize,
                    mKeepAliveTime,
                    mUnit,
                    mWorkQueue,
                    mThreadFactory,
                    mHandler);
            mThreadPoolExecutorArrayMaps.put(threadPoolKey, executor);
        }
        executor.execute(runnable);// 添加任务
    }

    /**
     * 移除指定的线程池的线程
     *
     * @param threadPoolKey 指定线程池的名字
     * @param runnable      指定的线程
     */
    public void removeSpeciallyPoolRunnable(String threadPoolKey, Runnable runnable) {
        ThreadPoolExecutor executor = mThreadPoolExecutorArrayMaps.get(threadPoolKey);
        if (executor != null && runnable != null) {
            executor.getQueue().remove(runnable);//移除任务
        }
    }

    /**
     * <p>用来存线程池参数的类</p>
     *
     * @author minminaya
     * @email minminaya@gmail.com
     * @time Created by 2018/6/29 15:05
     */
    public static class Builder {
        //CPU的数量
        private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
        //核心线程数
        private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
        //线程池最大线程数
        private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
        //非核心线程闲置时超时0s
        private static final long KEEP_ALIVE = 1L;

        int mCorePoolSize;
        int mMaximumPoolSize;
        long mKeepAliveTime;
        TimeUnit mUnit;
        BlockingQueue<Runnable> mWorkQueue;
        ThreadFactory mThreadFactory;
        RejectedExecutionHandler mHandler;

        public Builder() {
            //初始化默认值
            this.mCorePoolSize = CORE_POOL_SIZE;
            this.mMaximumPoolSize = MAXIMUM_POOL_SIZE;
            this.mKeepAliveTime = KEEP_ALIVE;
            this.mUnit = TimeUnit.SECONDS;
            this.mWorkQueue = new LinkedBlockingQueue<>();
            this.mThreadFactory = Executors.defaultThreadFactory();
            this.mHandler = new ThreadPoolExecutor.AbortPolicy();
        }

        public Builder setCorePoolSize(int corePoolSize) {
            this.mCorePoolSize = corePoolSize;
            return this;
        }

        public Builder setMaximumPoolSize(int maximumPoolSize) {
            this.mMaximumPoolSize = maximumPoolSize;
            return this;
        }

        public Builder setKeepAliveTime(long keepAliveTime) {
            this.mKeepAliveTime = keepAliveTime;
            return this;
        }

        public Builder setUnit(TimeUnit unit) {
            this.mUnit = unit;
            return this;
        }

        public Builder setWorkQueue(BlockingQueue<Runnable> workQueue) {
            this.mWorkQueue = workQueue;
            return this;
        }

        public Builder setThreadFactory(ThreadFactory threadFactory) {
            this.mThreadFactory = threadFactory;
            return this;
        }

        public Builder setHandler(RejectedExecutionHandler handler) {
            this.mHandler = handler;
            return this;
        }
    }
}