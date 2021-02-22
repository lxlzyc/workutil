package com.lxl.github.workutil.thread.executor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lxl
 * @program workutil
 * @description: 显示使用线程池实现异步编程
 * @date 2021/2/22 13:39
 * @Version 1.0
 */
public class ThreadExecutorPool {
    /**
     * 自定义线程池 设置核心线程个数为当前物理机的CPU个数；
     * */
    private final static int AVALIABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    /**
     * 核心线程个数为当前物理机的CPU个数,最大线程是为物理机CUP的两倍，一分钟；阻塞队列大小为5，决绝策略为CallerRunsPolicy
     * */
    private final  static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVALIABLE_PROCESSORS,AVALIABLE_PROCESSORS*2,
            1, TimeUnit.MINUTES,new LinkedBlockingQueue<>(5),new ThreadPoolExecutor.CallerRunsPolicy());

    public static void doSomethingA(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------doSomethingA--------");
    }
    public static void doSomethingB(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("----------doSomethingB------------");
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        //开启异步单元执行任务A
        POOL_EXECUTOR.execute(()->{
            try {
                doSomethingA();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //执行任务B
        POOL_EXECUTOR.execute(()->{
            try {
                doSomethingB();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //同步等待线程A运行结果
        System.out.println(System.currentTimeMillis()-start);
        //挂起当前线程
        System.out.println("----------end------------");
        POOL_EXECUTOR.shutdown();
    }
}