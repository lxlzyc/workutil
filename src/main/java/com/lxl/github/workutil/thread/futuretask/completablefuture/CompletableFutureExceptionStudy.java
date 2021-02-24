package com.lxl.github.workutil.thread.futuretask.completablefuture;

import java.util.concurrent.*;

/**
 * @program: leetcode-hz
 * @description: CompletableFuture 异常
 * @author: lxl
 * @create: 2021-02-24 21:44
 **/
public class CompletableFutureExceptionStudy {

    private static final ThreadPoolExecutor bizPoolExecutor = new ThreadPoolExecutor(8, 8, 1, TimeUnit.MINUTES,
            new LinkedBlockingDeque<>(10));

    public static void completeException() throws ExecutionException, InterruptedException {
        //创建异步任务，并返回future
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                if(1 ==1 ){
                    throw new Exception("exception test");
                }
                //设置正常结束
                future.complete("ok");
            } catch (Exception e) {
                //设置异常
                future.completeExceptionally(e);
            }
            System.out.println("-------"+Thread.currentThread().getName() +" set future result --------");
        },"thread-1").start();
        //异常返回默认值
        System.out.println(future.exceptionally(t -> "default").get());
        //System.out.println(future.get());
        System.out.println("-------------");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFutureExceptionStudy.completeException();
        System.out.println("----------------------");
    }

}
