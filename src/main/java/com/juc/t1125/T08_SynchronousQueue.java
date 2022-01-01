package com.juc.t1125;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author: Lyle
 * @date: 2022/1/1
 * @description:
 * 容量为0的队列，不是用来装元素的，而是用来给另外一个线程传递东西的
 **/
public class T08_SynchronousQueue {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> q = new SynchronousQueue<>();

        new Thread(()->{
            try {
                //一个线程阻塞等着取出数据
                System.out.println(q.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //另一个传递数据
        q.put("aaa"); //没有线程take，这里会阻塞
        System.out.println(q.size());
    }
}
