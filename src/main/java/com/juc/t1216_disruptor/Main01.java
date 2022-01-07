package com.juc.t1216_disruptor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.concurrent.Executors;

public class Main01 {

    public static void main(String[] args) {
        m1();

    }

    /**
     * 官方示例
     */
    static void m1(){
        //事件工厂
        LongEventFactory factory = new LongEventFactory();
        int bufferSize = 1024;
        //disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, Executors.defaultThreadFactory());
        //事件处理者
        disruptor.handleEventsWith(new LongEventHandler());
        //开始
        disruptor.start();
        //获取环形buffer
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        //发布流程
        //先获取一个sequence
        long sequence = ringBuffer.next();
        try {
            //通过序列得到对象，并给对象属性赋值
            LongEvent event = ringBuffer.get(sequence);
            event.setValue(8888L);
        } finally {
            //发布该序列的对象，防止finally确保一定会执行
            ringBuffer.publish(sequence);
        }
        //结束
        disruptor.shutdown();
    }

    static void m11(){
        //事件工厂
        LongEventFactory factory = new LongEventFactory();
        int bufferSize = 1024;
        //disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, Executors.defaultThreadFactory());
        //事件处理者
        disruptor.handleEventsWith(new LongEventHandler());
        //开始
        disruptor.start();
        //获取环形buffer
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        //发布
        long sequence1 = ringBuffer.next();
        System.out.println("sequence1:" + sequence1);

        long sequence2 = ringBuffer.next();
        System.out.println("sequence2:" + sequence2);

        try {
            LongEvent event1 = ringBuffer.get(sequence1);
            event1.setValue(8888L);
        } finally {
            ringBuffer.publish(sequence1);
        }


        try {
            LongEvent event2 = ringBuffer.get(sequence2);
            event2.setValue(8882L);
        } finally {
            ringBuffer.publish(sequence2);
        }
        disruptor.shutdown();

    }

    /**
     * translator
     */
    static void m2(){
        LongEventFactory factory = new LongEventFactory();
        int bufferSize = 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        //EventTranslator
        EventTranslator<LongEvent> translator1 = new EventTranslator<LongEvent>() {
            @Override
            public void translateTo(LongEvent event, long sequence) {
                event.setValue(1L);
            }
        };
        ringBuffer.publishEvent(translator1);

        //EventTranslator lambda表达式用法
        EventTranslator<LongEvent> translator2 = (event, sequence) -> event.setValue(2L);
        ringBuffer.publishEvent(translator2);

        //EventTranslator arg 参数转移到外部
        EventTranslatorOneArg<LongEvent, Long> translator3 = (event, sequence, arg0) -> event.setValue(arg0);
        ringBuffer.publishEvent(translator3, 3L);

    }

    /**
     * lambda表达式，不需要创建Factory类和Handler类
     */
    static void m3(){
        int bufferSize = 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> System.out.println("[" + Thread.currentThread().getContextClassLoader() + "] event:" + event.getValue() + " 序号:" + sequence));
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent((event, sequence) -> event.setValue(1000L));
        //参数转移到外部
        ringBuffer.publishEvent((event, sequence, l1, l2) -> event.setValue(l1 + l2), 1001L, 1002L);
    }

}
