## CompleteableFuture

#### 1.CompleteableFuture<Void> runAsync(Runnable runnable)
返回一个新的CompleteableFuture对象，其结果值会在给定的runnable行为 使用 ForkJoinPoll.commonPool()异步执行完毕后被设置为 null

#### 2.CompleteableFuture<U> supplyAsync(Supplier<U> supplier)
返回一个新的CompleteableFuture对象，其结果值作为入参 supplier行为执行结果，调用future的get方法获取到值
使用 ForkJoinPoll.commonPool()执行supplier

#### 3.CompleteableFuture<U> supplyAsync(Supplier<U> supplier, Executore executore)
同上 ，使用传进来的 Executore 执行supplier



