package com.lxl.github.workutil.thread.reactor;

/**
 * @program: workutil
 * @description: demo
 * @author: lxl
 * @create: 2021-03-02 21:16
 **/
public class Person {

    private int age;
    private String name;

    public Person() {
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
