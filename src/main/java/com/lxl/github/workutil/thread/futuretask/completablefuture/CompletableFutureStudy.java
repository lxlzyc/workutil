package com.lxl.github.workutil.thread.futuretask.completablefuture;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @program: leetcode-hz
 * @description:
 * @author: lxl
 * @create: 2021-02-24 21:44
 **/
public class CompletableFutureStudy {

    private static final ThreadPoolExecutor bizPoolExecutor = new ThreadPoolExecutor(8, 8, 1, TimeUnit.MINUTES,
            new LinkedBlockingDeque<>(10));

    public static void supplyAsyncWithBizExecutor() throws ExecutionException, InterruptedException {
        //创建异步任务，并返回future
        CompletableFuture future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get(){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello ";
            }
        }, bizPoolExecutor);

        System.out.println(future.get());
    }

    //线程1完成后 执行线程2 thenrun拿不到线程1返回值
    public static void thenRun() throws ExecutionException, InterruptedException {
        //创建异步任务，并返回future
        CompletableFuture future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get(){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("future doing");
                return "hello ";
            }
        }, bizPoolExecutor);
        //future计算完成后回调 并返回新的future
        CompletableFuture future2 = future.thenRun(new Runnable() {
            @Override
            public void run(){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
                System.out.println("future2 doing");
            }
        });
        System.out.println(future2.get());
    }
    //线程1完成后 执行线程2 thenAccept能拿到1执行结果
    public static void thenAccept() throws ExecutionException, InterruptedException {
        //创建异步任务，并返回future
        CompletableFuture future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get(){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("future doing");
                return "hello ";
            }
        }, bizPoolExecutor);
        //future计算完成后回调 并返回新的future
        CompletableFuture future2 = future.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String t){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
                System.out.println("future2 doing======"+t);
            }
        });
        System.out.println(future2.get());
    }

    //线程1完成后 执行线程2  thenApply能拿到1执行结果 且能拿到2执行结果
    public static void thenApply() throws ExecutionException, InterruptedException {
        //创建异步任务，并返回future
        CompletableFuture future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get(){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("future doing");
                return "hello ";
            }
        }, bizPoolExecutor);
        //future计算完成后回调 并返回新的future
        CompletableFuture future2 = future.thenApply(new Function<String, String>() {
            @Override
            public String apply(String t){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
                System.out.println("future2 doing======"+t);
                return "hello00000";
            }
        });
        System.out.println(future2.get());
    }

    //不会阻塞线程
    public static void whenComplete() throws ExecutionException, InterruptedException {
        //创建异步任务，并返回future
        CompletableFuture future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get(){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("future doing");
                return "hello ";
            }
        }, bizPoolExecutor);
        //添加回调函数
        CompletableFuture future2 = future.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String t, Throwable u){
                //如果没有异常 打印结果
                if(null == u){
                    System.out.println("------"+t);
                }else{
                    System.out.println(u.getLocalizedMessage());
                }
            }
        });
        //挂起当前线程等待异步执行完毕
        Thread.currentThread().join();
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFutureStudy.whenComplete();
        System.out.println("----------------------");
    }

}
