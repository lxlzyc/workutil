## 基于反应式编程实现异步编程

#### 1.反应式编程
涉及数据流和变化传播的异步编程范式
1. 即时响应
2. 回弹
3. 弹性
4. 消息驱动

#### 2.Reactive Streams规范
1. 目标
管理跨异步边界的流数据交换，考虑将元素从一个线程传递到另一个线程或线程池进行处理，同时确保接收方不会强制缓冲任意数量的数据。

处理潜在的无限数量的元素并按顺序处理
在组件之间异步传递元素
具有强制性的非阻塞回压

2. 引包
```xml
     <!-- reactive streams 反应式编程 -->
        <dependency>
            <groupId>org.reactivestreams</groupId>
            <artifactId>reactive-streams</artifactId>
            <version>1.0.3</version>
        </dependency>
        <!-- reactive streams 反应式编程 一致测试的标准测试套件-->
        <dependency>
            <groupId>org.reactivestreams</groupId>
            <artifactId>reactive-streams-tck</artifactId>
            <version>1.0.3</version>
            <scope>test</scope>
        </dependency>       
```

API包含
1. Publisher 发布者
2. Subscriber 订阅者
3. Subscription 订阅关系
4. Processor 处理器

#### 3.基于RxJava实现异步编程
RxJava是Reactive Extensions的JVM实现，用于通过使用可观察序列来编写异步和基于时间的程序

引包
```xml
        <dependency>
            <groupId>io.reactivex.rxjava2</groupId>
            <artifactId>rxjava</artifactId>
            <version>2.2.10</version>
        </dependency>
```

#### 3.基于Reactor实现异步编程

引包
```xml
          <dependencyManagement>
                <dependencies>
                    <dependency>
                        <groupId>io.projectreactor</groupId>
                        <artifactId>reactor-bom</artifactId>
                        <version>Dysprosium-SR10</version>
                        <type>pom</type>
                        <scope>import</scope>
                    </dependency>
                </dependencies>
            </dependencyManagement>
    
      <!-- reactor -->
            <dependency>
                <groupId>io.projectreactor</groupId>
                <artifactId>reactor-core</artifactId>
            </dependency>
            <dependency>
                <groupId>io.projectreactor</groupId>
                <artifactId>reactor-test</artifactId>
                <scope>test</scope>
            </dependency>
```


