package com.juc.t1125;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class T02_Queue {

    /**
     * ConcurrentLinkedQueue
     *
     *
     *
     *
     */

    public static void main(String[] args) {
        testConcurrentLinkedQueue();
    }

    static void testConcurrentLinkedQueue(){
        Queue<String> strQueue = new ConcurrentLinkedQueue<>();

        for (int i=0; i<10; i++){
            strQueue.offer("a" + i); // offer 添加，返回成功或失败
        }

        System.out.println("strQueue:" + strQueue);
        strQueue.peek(); //窥视: 拿取一个元素，但是并没有remove操作
        System.out.println("size:" + strQueue.size());

        strQueue.poll(); //取出(含remove)
        System.out.println("size:" + strQueue.size());


    }



}
