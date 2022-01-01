package com.juc.t1125;

import java.util.concurrent.LinkedTransferQueue;

/**
 * @author: Lyle
 * @date: 2022/1/1
 * @description:
 * 更好用的线程之间传递数据的queue
 *
 **/
public class T09_TransferQueue {

    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue<String> tq = new LinkedTransferQueue<>();


        new Thread(()->{
            try {
                System.out.println(tq.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //装完阻塞，直到被取走
        tq.transfer("aaa");

//        new Thread(()->{
//            try {
//                System.out.println(tq.take());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }
}
