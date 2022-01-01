package com.juc.t1124;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.UUID;

public class T01_HashTable {

    static int count = Constants.COUNT;
    static UUID[] keys = new UUID[count];
    static UUID[] values = new UUID[count];
    static final int THREAD_COUNT = Constants.THREAD_COUNT;

    static Hashtable<UUID, UUID> hashtable = new Hashtable<>();
    static HashMap<UUID, UUID> hashMap = new HashMap<>();

    static {
        for (int i=0; i<count; i++){
            keys[i] = UUID.randomUUID();
            values[i] = UUID.randomUUID();
        }
    }

    static class MyHashTableThread extends Thread{
        int start;
        int gap = count/THREAD_COUNT;
        public MyHashTableThread(int start){
            this.start = start;
        }
        @Override
        public void run() {
            for (int i=start;  i<start+gap; i++){
                hashtable.put(keys[i], values[i]);
            }
        }
    }

    static class MyHashMapThread extends Thread{
        int start;
        int gap = count/THREAD_COUNT;
        public MyHashMapThread(int start){
            this.start = start;
        }

        @Override
        public void run() {
            for (int i=start;  i<start+gap; i++){
                hashMap.put(keys[i], values[i]);
            }
        }
    }


    static void testHashTable(){
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[THREAD_COUNT];
        //创建100个线程并设置map的开始下标
        for (int i=0; i<threads.length; i++){
            threads[i] = new MyHashTableThread(i * (count/THREAD_COUNT));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("write hashtable time:" + (end - start)); //322
        System.out.println("hashtable size:" + hashtable.size());
    }

    static void testHashMap(){
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[THREAD_COUNT];
        //创建100个线程并设置map的开始下标
        for (int i=0; i<threads.length; i++){
            threads[i] = new MyHashMapThread(i * (count/THREAD_COUNT));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("write hashMap time:" + (end - start)); //322
        System.out.println("hashMap size:" + hashMap.size());
    }

    public static void main(String[] args) {
        testHashMap();
            testHashTable();





    }
}
