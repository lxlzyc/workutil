package com.lxl.github.workutil.thread.futuretask;

import java.util.concurrent.*;

/**
 * @author lxl
 * @program workutil
 * @description: JDK中的FutureTask的异步-线程池执行futuretask
 * @date 2021/2/22 13:52
 * @Version 1.0
 */
public class AsyncFurureExecutorExample {

    /**
     * 自定义线程池 设置核心线程个数为当前物理机的CPU个数；
     * */
    private final static int AVALIABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    /**
     * 核心线程个数为当前物理机的CPU个数,最大线程是为物理机CUP的两倍，一分钟；阻塞队列大小为5，决绝策略为CallerRunsPolicy
     * */
    private final  static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVALIABLE_PROCESSORS,AVALIABLE_PROCESSORS*2,
            1, TimeUnit.MINUTES,new LinkedBlockingQueue<>(5),new ThreadPoolExecutor.CallerRunsPolicy());


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        //创建future任务
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            String result = null;
            try {
                result = doSomethingA();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        });
        //2.开启异步单元执行
        POOL_EXECUTOR.execute(futureTask);

        //执行任务B
        String s = doSomethingB();
        //同步等待线程A结束
        String s1 = futureTask.get();
        System.out.println("执行A所得："+s1+", 执行B所得："+s);
        //打印两个得时间
        System.out.println(System.currentTimeMillis()-start);
    }
    public static String doSomethingA(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------doSomethingA--------");
        return "A";
    }

    public static String doSomethingB(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("----------doSomethingB------------");
        return "B";
    }
}