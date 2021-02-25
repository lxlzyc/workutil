package com.lxl.github.workutil.thread.spring.taskexecutor;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.TaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: workutil
 * @description: demo
 * @author: lxl
 * @create: 2021-02-25 21:35
 **/
public class AsyncExecutorExample {
    private class MessagePrintTask implements Runnable {
        private String msg;

        public MessagePrintTask(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " " + msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private TaskExecutor taskExecutor;

    public void printMsg() {
        for (int i = 0; i < 6; i++) {
            taskExecutor.execute(new MessagePrintTask(("Message" + i)));
        }
    }

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void shutdown(){
        if(taskExecutor instanceof ThreadPoolExecutor){
            ((ThreadPoolExecutor) taskExecutor).shutdown();
        }
    }
    //    配合spring注入demo
    public static void main(String[] args) {
        //创建上下文容器
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"beans.xml"});
        //获取实例并调用打印方法
        System.out.println(Thread.currentThread().getName() + " begin ");
        AsyncExecutorExample asyncExecutorExample = applicationContext.getBean(AsyncExecutorExample.class);
        asyncExecutorExample.printMsg();
        asyncExecutorExample.shutdown();
        System.out.println(Thread.currentThread().getName() + " end ");
    }

}
