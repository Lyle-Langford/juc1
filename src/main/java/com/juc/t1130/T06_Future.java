package com.juc.t1130;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Fure + Runnable
 */
public class T06_Future {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(()->{
            TimeUnit.MILLISECONDS.sleep(500);
            return 1000;
        });

        new Thread(task).start();

        Long begin = System.currentTimeMillis();
        System.out.println("begin");

        System.out.println(task.get());

        Long end = System.currentTimeMillis();
        System.out.println("time:" + (end - begin));
    }
}
