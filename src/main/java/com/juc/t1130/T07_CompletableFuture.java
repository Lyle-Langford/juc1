package com.juc.t1130;

import org.springframework.util.StopWatch;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 本类功能：
 * 提供一个服务
 * 能查询各大电商网站同一类产品的价格并汇总展示
 */
public class T07_CompletableFuture {

    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch("stopwWatch");
        stopWatch.start("sw1"); //开始计时


        //异步执行
        CompletableFuture<Double> futureTM = CompletableFuture.supplyAsync(() -> priceOfTM());
        CompletableFuture<Double> futureTB = CompletableFuture.supplyAsync(() -> priceOfTB());
        CompletableFuture<Double> futureJD = CompletableFuture.supplyAsync(() -> priceOfJD());

        //批量管理
        CompletableFuture.allOf(futureTM, futureTB, futureJD).join();
        //CompletableFuture.anyOf(futureTM, futureTB, futureJD).get();

        //结果链式管理
        /*CompletableFuture.supplyAsync(()->priceOfTM())
                .thenApply(String::valueOf)
                .thenApply(str -> "price " +  str)
                .thenAccept(System.out::println);*/

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

    }




    private static double priceOfTM(){
        delay();
        return 1.00;
    }
    private static double priceOfTB(){
        delay();
        return 2.00;
    }
    private static double priceOfJD(){
        delay();
        return 3.00;
    }

    private static void delay(){
        int time = 200 + new Random().nextInt(500);
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("sleep %s!\n", time);
    }
}
