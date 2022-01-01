package com.juc.t0927;

/**
 * java四种级别引用，强软弱虚
 * 强 -> 普通引用 Object o = new Object(); 一个有对象引用指着的实例，该实例有对象引用指着的时候一定不会被垃圾回收器回收
 * 虚引用管理堆外内存
 */
public class M {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
    }
}
