package com.lxl.github.workutil.thread.futuretask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author lxl
 * @program workutil
 * @description: JDK中的FutureTask的异步
 * @date 2021/2/22 13:52
 * @Version 1.0
 */
public class AsyncFurureExample {

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
        Thread thread = new Thread(futureTask,"threadA");
        thread.start();
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