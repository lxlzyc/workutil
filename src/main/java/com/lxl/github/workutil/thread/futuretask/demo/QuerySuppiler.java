package com.lxl.github.workutil.thread.futuretask.demo;

import java.util.function.Supplier;

/**
 * @author lxl
 * @program workutil
 * @description: demo
 * @date 2021/2/22 14:14
 * @Version 1.0
 */
public class QuerySuppiler implements Supplier<String> {
    private Integer id;
    private String type;
    private QueryUtils queryUtils;
    public QuerySuppiler(Integer id, String type,QueryUtils queryUtils) {
        this.id = id;
        this.type = type;
        this.queryUtils=queryUtils;
    }
    @Override
    public String get() {
        if("home".equals(type)){
            return queryUtils.queryHome(id);
        }else if ("job".equals(type)){
            return queryUtils.queryJob(id);
        }else if ("car".equals(type)){
            return queryUtils.queryCar(id);
        }
        return null;
    }
}