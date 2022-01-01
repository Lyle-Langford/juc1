package com.juc.t0927;

import java.lang.ref.SoftReference;

/**
 * 软引用
 * 当一个对象被软引用指向，系统内存不够用时，才会被回收
 */
public class T02_SoftReference {

    public static void main(String[] args) {
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024*1024*10]);

        //m = null;

        System.out.println(m.get());
        System.gc();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(m.get());

        // 设置JVM参数 -Xms20M -Xmx20M 堆内存初始20M 最大20M
        // 再分配一个数组，heap降装不下，然后自动系统回垃圾回收，把软引用干掉
        byte[] b = new byte[1024*1024*13];
        System.out.println(m.get());
    }
}
