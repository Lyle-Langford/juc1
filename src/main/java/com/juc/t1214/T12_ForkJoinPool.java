package com.juc.t1214;

import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class T12_ForkJoinPool {

    static  long[] nums = new long[100000000];
    static final int MAX_NUM = 50000;
    static Random r = new Random();

    static {
        for (int i=0; i<nums.length; i++){
            nums[i] = r.nextInt(10000);
        }
        StopWatch sw = new StopWatch();
        sw.start("static");
        long result = Arrays.stream(nums).sum();
        sw.stop();
        System.out.printf("s_sum:%s, time:%s\n", result, sw.getLastTaskTimeMillis());
    }


    public static void main(String[] args) throws IOException {
        ForkJoinPool fjp = new ForkJoinPool();
//        AddTask task = new AddTask(0, nums.length);
//        fjp.execute(task);
        //System.in.read();

        //带返回的情况
        StopWatch sw = new StopWatch();
        sw.start("main");

        AddTaskReturn task = new AddTaskReturn(0, nums.length);
        fjp.execute(task);
        long result = task.join();

        sw.stop();
        System.out.printf("t_sum:%s, time:%s\n", result, sw.getLastTaskTimeMillis());



    }



    static class AddTask extends RecursiveAction{

        int start, end;

        AddTask(int s, int e){
            start = s;
            end = e;
        }

        @Override
        protected void compute() {
            //数量小于5万就进行计算
            if (end - start <= MAX_NUM){
                long sum = 0L;
                for (int i=start; i<end; i++){
                    sum += nums[i];
                }

                System.out.printf("form:%s to:%s = %s .\n", start, end, sum);

            }else{
                //否则继续分片
                int middle = start + (end - start) / 2;

                AddTask subTask1 = new AddTask(start, middle);
                AddTask subTask2 = new AddTask(middle, end);
                subTask1.fork();
                subTask2.fork();
            }
        }
    }

    static class AddTaskReturn extends RecursiveTask<Long> {
        int start, end;

        AddTaskReturn(int s, int e){
            start = s;
            end = e;
        }
        @Override
        protected Long compute() {
            if (end - start <= MAX_NUM){
                long sum = 0L;
                for (int i=start; i<end; i++){
                    sum += nums[i];
                }
                return sum;
            }else{
                //否则继续分片
                int middle = start + (end - start) / 2;

                AddTaskReturn subTask1 = new AddTaskReturn(start, middle);
                AddTaskReturn subTask2 = new AddTaskReturn(middle, end);
                subTask1.fork();
                subTask2.fork();
                return subTask1.join() + subTask2.join();
            }

        }
    }
}
