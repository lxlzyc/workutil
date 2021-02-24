package com.lxl.github.workutil.thread.futuretask.completablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @program: leetcode-hz
 * @description: Stream 结合 CompletableFuture
 * @author: lxl
 * @create: 2021-02-24 21:44
 **/
public class StreamAndCompletableFutureStudy {

    public static String rpcCall(String ip, String param){
        System.out.println("rpcCall:"+ip+"?"+param);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ip+":"+param;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        List<String> ipList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ipList.add("192.168.0."+i);
        }
        //并发调用
        List<CompletableFuture<String>> futureList = ipList.stream().map(ip -> CompletableFuture.supplyAsync(() -> rpcCall(ip,ip)))
                .collect(Collectors.toList());
        //等待所有调用完毕
        List<String> resultList = futureList.stream().map(future -> future.join()).collect(Collectors.toList());
        //输出
        resultList.stream().forEach(r -> System.out.println(r));
        System.out.println("end--------------"+(System.currentTimeMillis()-start));
    }

}
