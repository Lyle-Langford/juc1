package com.juc.t1214;

import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class T09_FixedThreadPool {


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        StopWatch sw = new StopWatch();
        sw.start("a"); //开始计时

        getPrime(600001, 800000);
        sw.stop();
        System.out.println(sw.getLastTaskTimeMillis());

        sw.start("b");

        final int coreNum = 4;
        //FixedThreadPool 固定几个线程，利用cpu并行运行的线程池
        ExecutorService service = Executors.newFixedThreadPool(coreNum);

        MyTask t1 = new MyTask(1, 200000);
        MyTask t2 = new MyTask(200001, 400000);
        MyTask t3 = new MyTask(400001, 600000);
        MyTask t4 = new MyTask(600001, 800000);


        Future<List<Integer>> f1 = service.submit(t1);
        Future<List<Integer>> f2 = service.submit(t2);
        Future<List<Integer>> f3 = service.submit(t3);
        Future<List<Integer>> f4 = service.submit(t4);

        f1.get();
        f2.get();
        f3.get();
        f4.get();

        sw.stop();
        System.out.println(sw.getLastTaskTimeMillis());


    }


    /**
     * 是否是质数
     */
    static boolean isPrime(int num){
        for (int i=2; i<num/2; i++){
            if (num % i == 0) return false;
        }
        return true;
    }

    static List<Integer> getPrime(int start, int end){
        List<Integer> results = new ArrayList<>();
        for (int i=start; i<= end; i++){
            if (isPrime(i)){
                results.add(i);
            }
        }
        return results;
    }

    static class MyTask implements Callable<List<Integer>>{
        private int begin;
        private int end;

        public MyTask(int begin, int end){
            this.begin = begin;
            this.end = end;
        }

        @Override
        public List<Integer> call() {
            return getPrime(begin, end);
        }
    }
}
