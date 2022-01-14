package com.juc.t1125;

import com.lmax.disruptor.dsl.Disruptor;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.*;

public class T04_Queue {

    /**
     * Deque双向队列
     *
     * ConcurrentQueue 线程安全队列
     *
     * BlockingQueue
     * 在concurrent基础上做了阻塞
     * take时没有元素就会阻塞，直到有元素进来
     * Array类型在创建时指定队列长度，塞满之后put方法也会阻塞
     *
     * 面试题List和Queue的区别
     * 主要在于一些对线程友好的api方法：offer peek pool, 阻塞put take
     *
     *
     */

    public static void main(String[] args) {
        testLinkedBlockingQueue();
    }

    static void testConcurrentLinkedQueue(){
        Queue<String> strQueue = new ConcurrentLinkedQueue<>();
        System.out.println(strQueue.poll());


        for (int i=0; i<10; i++){
            //strQueue.add("a"); // 在Array里添加失败会抛出异常
            strQueue.offer("a" + i); // offer 添加，返回成功或失败
        }

        System.out.println("strQueue:" + strQueue);
        System.out.println(strQueue.peek()); //窥视: 拿取一个元素，但是并没有remove操作
        System.out.println("size:" + strQueue.size());

        System.out.println(strQueue.poll()); //取出(含remove)
        System.out.println("size:" + strQueue.size());
    }

    static void testLinkedBlockingQueue(){
        BlockingQueue<String> strQueue = new LinkedBlockingQueue<>();

        //会阻塞住
//        try {
//            strQueue.take();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

            strQueue.offer("-1");


        new Thread(()->{
            for (int i=0; i<20; i++){
                try {
                    strQueue.put("a" + i);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        for (int i=0; i<5; i++){
            new Thread(()->{
                try {
                    for (;;){
                        System.out.println(Thread.currentThread().getName() + ":" + strQueue.take());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "t" + i).start();
        }

    }



}
