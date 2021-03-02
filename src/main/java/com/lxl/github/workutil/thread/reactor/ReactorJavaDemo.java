package com.lxl.github.workutil.thread.reactor;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @program: workutil
 * @description:
 * @author: lxl
 * @create: 2021-03-02 21:15
 **/
public class ReactorJavaDemo {


    public List<Person> makePersonList(int count){
        Random random = new Random();
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            people.add(new Person(random.nextInt(99),"姓名"+i));
        }
        return people;
    }
    public static void main(String[] args) {
        ReactorJavaDemo rxJavaDemo = new ReactorJavaDemo();
        List<Person> people = rxJavaDemo.makePersonList(20);
        //rxjava过滤输出
        Flux.fromArray(people.toArray(new Person[0])) //转换列表为Flowable流对象
                .filter(person -> person.getAge() >= 10) //过滤
                .map(person -> person.getName())//映射转换
                .subscribe(System.out::println);//订阅输出
    }
}
