package com.lxl.github.workutil.thread.futuretask.completablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * @program: leetcode-hz
 * @description: 多个CompletableFuture组合
 * @author: lxl
 * @create: 2021-02-24 21:44
 **/
public class CompletableFuturesStudy {

    private static final ThreadPoolExecutor bizPoolExecutor = new ThreadPoolExecutor(8, 8, 1, TimeUnit.MINUTES,
            new LinkedBlockingDeque<>(10));


    //一个 CompletableFuture 执行后 执行另一个 CompletableFuture(1返回结果当做参数传给2)
    public static void thenCompose() throws ExecutionException, InterruptedException {
        //doSomeThingOne 执行完后 执行 two
        CompletableFuture result = doSomeThingOne("123").thenCompose(id -> doSomeThingTwo(id));
        System.out.println(result.get());
    }

    //两个 CompletableFuture 并行执行后 合并执行第三个 CompletableFuture(1返回结果当做参数传给2)
    public static void thenCombine() throws ExecutionException, InterruptedException {
        //doSomeThingOne 执行完后 执行 two
        CompletableFuture result = doSomeThingOne("123").thenCombine(doSomeThingTwo("456"),  (one, two) -> {
            return one+two;
        });
        System.out.println(result.get());
    }

    //allOf 等待多个CompletableFuture执行完毕
    public static void allOf() throws ExecutionException, InterruptedException {
        List<CompletableFuture<String>> list = new ArrayList<>();
        list.add(doSomeThingOne("1"));
        list.add(doSomeThingOne("2"));
        list.add(doSomeThingOne("3"));
        list.add(doSomeThingOne("4"));
        //转换多个future为一个
        CompletableFuture<Void> result = CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]));
        //等待都执行完毕
        System.out.println(result.get());
    }

    //anyOf 等多个并行的其中一个执行完毕就返回
    public static void anyOf() throws ExecutionException, InterruptedException {
        List<CompletableFuture<String>> list = new ArrayList<>();
        list.add(doSomeThingOne("1"));
        list.add(doSomeThingOne("2"));
        list.add(doSomeThingOne("3"));
        list.add(doSomeThingOne("4"));
        //转换多个future为一个
        CompletableFuture<Object> result = CompletableFuture.anyOf(list.toArray(new CompletableFuture[list.size()]));
        //等待某一个执行完毕
        System.out.println(result.get());
    }

    public static CompletableFuture<String> doSomeThingOne(String id) {
        return CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get(){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("future doing");
                return "hello1 doing "+id;
            }
        }, bizPoolExecutor);
    }

    public static CompletableFuture<String> doSomeThingTwo(String id)  {
        return CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get(){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("future doing");
                return "hello2 doing "+id;
            }
        }, bizPoolExecutor);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuturesStudy.anyOf();
        System.out.println("----------------------");
    }

}
