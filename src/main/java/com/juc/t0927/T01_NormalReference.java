package com.juc.t0927;

import java.io.IOException;

/**
 * 强引用，普通饮用
 */
public class T01_NormalReference {
    public static void main(String[] args) {
        M m = new M();

        //被引用时不被回收，不被引用时才被回收
        //m = null;

        //调用垃圾回收机制，如果对象被回收就会触发finalize方法
        System.gc();

        //让线程不要立马结束
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
