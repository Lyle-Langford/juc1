package com.juc.t1214;

import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 并行流
 */
public class T13_ParallelStreamApi {

    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        Random random = new Random();
        for (int i=0; i<100000; i++){
            nums.add(1000000 + random.nextInt(1000000));
        }


        nums.forEach(v->isPrime(v));



        nums.parallelStream().forEach(v->isPrime(v));


    }


    static boolean isPrime(int num){
        for (int i=2; i<num/2; i++){
            if (num % i == 0) return false;
        }
        return true;
    }

}
