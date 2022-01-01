package com.juc.t0927;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

public class T04_PhantomReference {
    
    private static final List<Object> LIST = new LinkedList<>();
    private static final ReferenceQueue<M> QUEUE = new ReferenceQueue<>();

    public static void main(String[] args) {
        //虚引用被回收时会装到队列里，队列用来通知你该虚引用被回收了
        PhantomReference<M> phantomReference = new PhantomReference<>(new M(), QUEUE);

        //开启一个线程，一直加内存，触发垃圾回收
        new Thread(()->{
            while (true){
                LIST.add(new byte[1204*1024]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }

                //虚引用的值无法被get到，无法拿到里面的值
                System.out.println(phantomReference.get());
            }
        }).start();

        //开启另一个线程，一直监听队列
        new Thread(()->{
            Reference<? extends M> poll = QUEUE.poll();
            if (poll != null){
                System.out.println("虚引用对象被jvm回收" + poll);
            }
        }).start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    
}
