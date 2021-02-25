## Spring Executor

#### 1.TaskExecutor
等价 juc里的 Executor
Spring 内置TaskExecutor
1. SimpleAsyncTaskExecutor 
每个请求新创建一个线程运行
默认不限制并发数量
通过setConcurrencyLimit限制，超出后阻止调用
2. SyncTaskExecutor
同步
3. ConcurrentTaskExecutor
对java Executor的包装 
4. SimpleThreadPoolTaskExecutor
监听spring的生命周期回调
5. ThreadPoolTaskExecutor
只能在java5中使用
6. TimerTaskExecutor
使用单个Timer对象作为其内部异步线程来执行任务

#### 2.使用TaskExecutor实现异步执行

xml向spring容器中注入ThreadPoolTaskExecutor实例
``` xml
<bean id="taskExecutor" class="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor">
    <!-- 1.核心线程数 -->
    <property name="corePoolSize" value="5"/>
    <!-- 2.最大线程数 -->
    <property name="maxPoolSize" value="10"/>
    <!-- 3.超过核心线程个数的线程空闲多久被回收 -->
    <property name="keepAliveSeconds" value="60"/>
    <!-- 4.缓存队列大小 -->
    <property name="queueCapacity" value="20"/>
    <!-- 5.拒绝策略 -->
    <property name="rejectedExecutionHandler">
        <bean class="java.util.concurrent.ThreadPoolExecutor$CallerRunsPolicy" />
    </property>
    <!-- 6.调用shutdown时等待任务执行完成再退出 -->
    <property name="waitForTaskToCompleteOnShutdown" value="true"/>
</bean>
```
spring容器中使用，属性注入
``` xml
<bean id="asyncExecutorExample" class="com.lxl.demo.AsyncExecutorExample">
    <property name="taskExecutor" ref="taskExecutor"/>
</bean>
```
代码示例
``` java
public class AsyncExecutorExample {
    private class MessagePrintTask implements Runnable {
        private String msg;

        public MessagePrintTask(String msg){
            this.msg = msg;
        }

        @Override
        public void run(){
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() +" "+ msg);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private TaskExecutor taskExecutor;

    public void printMsg(){
        for (int i = 0; i < 6; i++) {
            taskExecutor.execute(new MessagePrintTask(("Message" +i)));
        }
    }

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }
}
```

#### 2.使用@Async实现异步执行
加在方法上，两种方式开启
1. 基于xml配置bean时加入配置
``` xml
<task:annotation-driven />
```
2. 基于注解时可以添加另一个注解启动异步
@EnableAsync

3. 注解遇到异常时处理
``` java
public class MyHandle implements AsyncUncaughtExceptionHandle {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params){
        //handle exception
    }
}
```
xml配置
``` xml
<task:annotation-driven exception-handler="myHandle" />
<bean id="myHandle" class="com.....MyHandle"></bean>
```

4. 原理
对注解类做了代理，使用Proxy
外部包了一个CompleteableFuture.supplyAsync执行具体方法，默认使用了SimpleAsyncTaskExecutor作为异步处理线程
默认使用cglib对注解进行处理，具体拦截器为 AnnotationAsyncExecutionInterceptor


