package com.juc.t1125;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列
 * 并且按时间排序，谁的时间到了谁先出来
 * 其本质是PriorityQueue
 */
public class T07_DelayQueue {

    static BlockingQueue<MyTask> tasks = new DelayQueue<>();

    static Random r = new Random();

    /**
     * 定义一个延迟对象
     * 并实现其时间方法
     */
    static class MyTask implements Delayed{
        String name;
        long runningTime;

        public MyTask(String name, long runningTime) {
            this.name = name;
            this.runningTime = runningTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(runningTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)){
                return -1;
            }else if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)){
                return 1;
            }else{
                return 0;
            }
        }

        @Override
        public String toString() {
            return name + " " + runningTime;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long now = System.currentTimeMillis();
        MyTask t1 = new MyTask("t1", now + 10000);
        MyTask t2 = new MyTask("t2", now + 20000);
        MyTask t3 = new MyTask("t3", now + 15000);
        MyTask t4 = new MyTask("t4", now + 5000);

        tasks.put(t1);
        tasks.put(t2);
        tasks.put(t3);
        tasks.put(t4);

        for (int i=0; i<4; i++){
            System.out.println(tasks.take());
        }
    }

    static void m2(){

    }
}
