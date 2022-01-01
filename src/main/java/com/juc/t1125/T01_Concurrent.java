package com.juc.t1125;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class T01_Concurrent {

    /**
     * 一些高并发类
     * ConcurrentLinkedQueue
     * ConcurrentHashMap
     * ConcurrentSkipListMap 高并发且排序， 跳表结构
     *
     * 跳表的多层
     * 1 9
     * 1 5 9
     * 1 3 5 7 9 底部链表
     *
     *
     * CopyOnWriteArrayList 写时复制 新增时复制一个新的容量加1的数组，然后直接替换掉内存引用，带复制的操作有锁，读取没有锁
     * 适用于读多写少的情况
     */


}
