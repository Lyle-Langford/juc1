package com.juc.t1125;

import java.util.PriorityQueue;

/**
 * @author: Lyle
 * @date: 2022/1/1
 * @description:
 **/
public class T07_PriorityQueue {

    public static void main(String[] args) {
        PriorityQueue<String> q = new PriorityQueue<>();


        q.add("c");
        q.add("e");
        q.add("a");
        q.add("d");
        q.add("z");

        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());
    }
}
