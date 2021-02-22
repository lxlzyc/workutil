package com.lxl.github.workutil.thread.futuretask.demo;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author lxl
 * @program workutil
 * @description: demo
 * https://www.cnblogs.com/javazhiyin/p/12365830.html
 * @date 2021/2/22 14:16
 * @Version 1.0
 */
public class QueryUserService {
    private Supplier<QueryUtils> queryUtilsSupplier = QueryUtils::new;
    /**
     * 使用 CompletableFuture 来优化查询
     *
     * @param userInfo
     * @return
     */
    public UserInfo converUserInfo(UserInfo userInfo) {
        QuerySuppiler querySuppiler1 = new QuerySuppiler(userInfo.getCarId(), "car", queryUtilsSupplier.get());
        CompletableFuture<String> getCarDesc = CompletableFuture.supplyAsync(querySuppiler1);
        getCarDesc.thenAccept(new Consumer<String>() {
//            --1
            @Override
            public void accept(String carDesc) {
                userInfo.setCarDes(carDesc);
            }
        });
        QuerySuppiler querySuppiler2 = new QuerySuppiler(userInfo.getHomeId(), "home", queryUtilsSupplier.get());
        CompletableFuture<String> getHomeDesc = CompletableFuture.supplyAsync(querySuppiler2);
        getHomeDesc.thenAccept(new Consumer<String>() {
//            --2
            @Override
            public void accept(String homeDesc) {
                userInfo.setHomeDes(homeDesc);
            }
        });
        QuerySuppiler querySuppiler3 = new QuerySuppiler(userInfo.getJobId(), "job", queryUtilsSupplier.get());
        CompletableFuture<String> getJobDesc = CompletableFuture.supplyAsync(querySuppiler3);
        getJobDesc.thenAccept(new Consumer<String>() {
//            --3
            @Override
            public void accept(String jobDesc) {
                userInfo.setJobDes(jobDesc);
            }
        });
        CompletableFuture<Void> getUserInfo = CompletableFuture.allOf(getCarDesc, getHomeDesc, getJobDesc);
        getUserInfo.thenAccept(new Consumer<Void>() {
            @Override
            public void accept(Void result) {
                System.out.println("全部完成查询" );
            }
        });
        getUserInfo.join();
//        --4
        return userInfo;
    }
    /**
     * 使用 FutureTask 来优化查询
     *
     * @param userInfo
     * @return
     */
    public  UserInfo converUserInfoV2(UserInfo userInfo) {
        Callable<String> homeCallable=new Callable() {
            @Override
            public Object call() throws Exception {
                return queryUtilsSupplier.get().queryHome(userInfo.getHomeId());
            }
        };
        FutureTask<String> getHomeDesc=new FutureTask<>(homeCallable);
        new Thread(getHomeDesc).start();
        Map<String, FutureTask<String>> futureMap = new HashMap<>();
        futureMap.put("homeCallable",getHomeDesc);
        Callable<String> carCallable=new Callable() {
            @Override
            public Object call() throws Exception {
                return queryUtilsSupplier.get().queryCar(userInfo.getCarId());
            }
        };
        FutureTask<String> getCarDesc=new FutureTask(carCallable);
        new Thread(getCarDesc).start();
        futureMap.put("carCallable",getCarDesc);
        Callable<String> jobCallable=new Callable() {
            @Override
            public Object call() throws Exception {
                return queryUtilsSupplier.get().queryCar(userInfo.getJobId());
            }
        };
        FutureTask<String> getJobDesc=new FutureTask<>(jobCallable);
        new Thread(getJobDesc).start();
        futureMap.put("jobCallable",getJobDesc);
        try {
            userInfo.setHomeDes((String) futureMap.get("homeCallable").get());
            userInfo.setCarDes((String)futureMap.get("carCallable").get());
            userInfo.setJobDes((String)futureMap.get("jobCallable").get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("该对象完成查询" );
        return userInfo;
    }

    public static void main(String[] args) {
        long begin= System.currentTimeMillis();
        //多线程环境需要注意线程安全问题
        List<UserInfo> userInfoList= Collections.synchronizedList(new ArrayList<>());
        for(int i=0;i<=20;i++){
            UserInfo userInfo=new UserInfo();
            userInfo.setId(i);
            userInfo.setName("username"+i);
            userInfo.setCarId(i);
            userInfo.setJobId(i);
            userInfo.setHomeId(i);
            userInfoList.add(userInfo);
        }
        //stream 查询一个用户花费3s  并行计算后一个用户1秒左右 查询21个用户花费21秒
        //parallelStream 速度更慢
        userInfoList.stream()
                .map(userInfo->{
                    QueryUserService queryUserService=new QueryUserService();
                    userInfo =queryUserService.converUserInfoV2(userInfo);
                    return userInfo;
                }).collect(Collectors.toList());
        System.out.println("=============");
        long end=System.currentTimeMillis();
        System.out.println(end-begin);
    }
}