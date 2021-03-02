package com.lxl.github.workutil.thread.reactor;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.lxl.github.workutil.thread.futuretask.completablefuture.StreamAndCompletableFutureStudy.rpcCall;

/**
 * @program: workutil
 * @description:
 * @author: lxl
 * @create: 2021-03-02 21:46
 **/
public class ReactorJavaRpcDemo {
    private final  static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(10,10,
            1, TimeUnit.MINUTES,new LinkedBlockingQueue<>(5),new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        List<String> ipList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ipList.add("192.168.0."+i);
        }
        Flux.fromArray(ipList.toArray(new String[0]))
                .flatMap(ip ->
                Flowable.just(ip)
                .subscribeOn(Schedulers.from(POOL_EXECUTOR)))
                .map(v -> rpcCall(v, v));
        System.out.println("end--------------"+(System.currentTimeMillis()-start));
    }


}
