package com.lxl.github.workutil.thread.futuretask.demo;

import java.util.concurrent.TimeUnit;

/**
 * @author lxl
 * @program workutil
 * @description: demo
 * @date 2021/2/22 14:14
 * @Version 1.0
 */
public class QueryUtils {
    public String queryCar(Integer carId){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "car_desc";
    }
    public String queryJob(Integer jobId){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "job_desc";
    }
    public String queryHome(Integer homeId){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "home_desc";
    }
}