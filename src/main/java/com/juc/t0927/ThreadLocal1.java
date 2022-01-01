package com.juc.t0927;

import java.util.concurrent.TimeUnit;

/**
 * 线程本地的资源
 */
public class ThreadLocal1 {

    volatile static Person p = new Person();
    static ThreadLocal<Person> tl = new ThreadLocal<>();


    public static void main(String[] args) {
        m2();
    }

    static void m1(){
        System.out.println("begin");
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(p.name);
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            p.name = "lisi";
        }).start();


    }

    static void m2(){
        System.out.println("begin");
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(tl.get().name);
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tl.set(new Person());
        }).start();


    }
}
class Person{
    String name = "zhangsan";
}
