package com.juc.t0927;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 弱引用遇到gc就会回收
 */
public class T03_WeakReference {

    public static void main(String[] args) {
        m1();
    }

    static void m1(){
        WeakReference<M> m = new WeakReference<>(new M());

        try {
            System.out.println(m.get());
            Thread.sleep(100);
            System.gc();
            Thread.sleep(100);
            System.out.println(m.get());


            Thread.sleep(100);
            ThreadLocal<M> tl = new ThreadLocal<>();
            tl.set(new M());
            //假设线程永久存在, 线程里的ThreadLocalMap的key为tl, value为new M(), 其中tl为弱引用，当tl = null，然后map里的key被回收时，new M()就会永远被访问不到，无法回收，发生内存泄漏
            //当ThreadLocal里的对象不再被使用时，一定要remove
            tl.remove();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




    static void m2(){
        String a = "a";
        String b = "b";

        Map map = new HashMap();
        map.put(a, "aVal");
        //map.put(b, "bVal");

        Map weakMap = new WeakHashMap();
        //weakMap.put(a, "aVal");
        weakMap.put(b, "bVal");

        a = null;
        b = null;

        System.gc();

        Iterator i = map.entrySet().iterator();
        if (i.hasNext()){
            Map.Entry en = (Map.Entry) i.next();
            System.out.println("map:" + en.getKey() + " :" + en.getValue());
        }


        Iterator j = weakMap.entrySet().iterator();
        if (j.hasNext()){
            Map.Entry jn = (Map.Entry) j.next();
            System.out.println("weakmap:" + jn.getKey() + " :" + jn.getValue());
        }
    }

    static void m3(){
        String a = new String("a");
        String b = new String("b");

        Map map = new HashMap();
        map.put(a, "aVal");
        map.put(b, "bVal");

        Map weakMap = new WeakHashMap();
        weakMap.put(a, "aVal");
        weakMap.put(b, "bVal");

        map.remove(a);
        a = null;
        b = null;

        System.gc();

        Iterator i = map.entrySet().iterator();
        if (i.hasNext()){
            Map.Entry en = (Map.Entry) i.next();
            System.out.println("map:" + en.getKey() + " :" + en.getValue());
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Iterator j = weakMap.entrySet().iterator();
        if (j.hasNext()){
            Map.Entry jn = (Map.Entry) j.next();
            System.out.println("weakmap:" + jn.getKey() + " :" + jn.getValue());
        }
    }
}
