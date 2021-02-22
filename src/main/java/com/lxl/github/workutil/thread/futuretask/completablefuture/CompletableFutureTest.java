package com.lxl.github.workutil.thread.futuretask.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author lxl
 * @program workutil
 * @description: CompletableFuture完成异步完成任务
 * @date 2021/2/22 13:55
 * @Version 1.0
 */
public class CompletableFutureTest {
//    基于runAsnc无返回值系列方法实现无返回值的异步计算：当执行一个任务时，不需要任务的实现结果是可以使用该方法，如打印异步日志，异步做消息通知等；
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String name = "号";
        try {
            runAsync(name);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        doSomethingB();
        System.out.println(System.currentTimeMillis()-start);
    }

    public static void runAsync(String name) throws ExecutionException, InterruptedException {

        CompletableFuture future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    doSomethingA(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("over");
            }
        });
        //同步等待消息执行结果
        System.out.println("get"+future.get());
    }

    public static void doSomethingA(String name){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------doSomethingA--------"+name);
    }
    public static void doSomethingB(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("----------doSomethingB------------");
    }
}