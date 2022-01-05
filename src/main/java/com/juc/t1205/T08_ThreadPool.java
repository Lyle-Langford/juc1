package com.juc.t1205;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 先使用核心线程，
 * 核心线程满了之后再申请的线程放到队列
 * 队列满了之后再申请的线程放到最大线程(扩容)
 * 最大线程也满了开始拒绝策略，java自带了4种拒绝策略，根据实际情况可以自定义拒绝策略，比如多的申请放到中间件
 * Abort: 抛异常
 * Discard: 扔掉新申请的，不抛异常
 * DiscardOldest: 扔掉最老的，把新的塞进去
 * CallerRuns: 由调用者(主线程)自己执行，不再异步
 *
 */
public class T08_ThreadPool {

    static class Task implements Runnable{
        private int i;

        public Task(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " task " + i);
            try {
                System.in.read();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Task{" +
                    "i=" + i +
                    '}';
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,4, //核心2， 最大4
                60, TimeUnit.SECONDS, //空闲维持60秒销毁线程对象
                new ArrayBlockingQueue<>(4), // 队列长度4
                Executors.defaultThreadFactory(), //默认线程工厂
                new ThreadPoolExecutor.CallerRunsPolicy()); //拒绝策略

        for (int i=0; i<8; i++){
            executor.execute(new Task(i));
        }

        System.out.println(executor.getQueue());
        executor.execute(new Task(9));
        System.out.println(executor.getQueue());
    }

    static void jdkThreadPool(){
        Executors.newSingleThreadExecutor();
        Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(8);
        Executors.newWorkStealingPool(); //实现是ForkJoinPool
    }
}
