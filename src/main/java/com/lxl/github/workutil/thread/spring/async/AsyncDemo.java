package com.lxl.github.workutil.thread.spring.async;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * @program: workutil
 * @description:
 * @author: lxl
 * @create: 2021-02-25 21:44
 **/
//注入spring容器
@Component
//开启异步执行
@EnableAsync
public class AsyncDemo {

    @Async
    public void printDemo() {
        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " msg " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //带返回值的异步
    @Async
    public CompletableFuture<String> printAndReturnDemo() {
        CompletableFuture<String> result = new CompletableFuture<String>();
        try {
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + " msg ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.complete("done");
        return result;
    }

    //处理异常 AsyncUncaughtExceptionHandle


    //    配合spring注入demo
    public static void main(String[] args) {
        //创建上下文容器
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"beans.xml"});
        //获取实例并调用打印方法
        System.out.println(Thread.currentThread().getName() + " begin ");
        AsyncDemo asyncDemo = applicationContext.getBean(AsyncDemo.class);
        CompletableFuture<String> result = asyncDemo.printAndReturnDemo();
        result.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                if (null == throwable) {
                    System.out.println(Thread.currentThread().getName() + " whencomplete " + s);
                } else {
                    System.out.println(throwable.getLocalizedMessage());
                }
            }
        });

        System.out.println(Thread.currentThread().getName() + " end ");
    }
}
