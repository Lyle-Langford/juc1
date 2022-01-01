package com.juc.t1216_disruptor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.concurrent.Executors;

public class Main01 {

    public static void main(String[] args) {
        m3();

    }

    /**
     * 官方示例
     */
    static void m1(){
        LongEventFactory factory = new LongEventFactory();
        int bufferSize = 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, bufferSize, Executors.defaultThreadFactory());
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();


        //发布
        long sequence = ringBuffer.next();
        try {
            LongEvent event = ringBuffer.get(sequence);
            event.setValue(8888L);
        } finally {
            ringBuffer.publish(sequence);
        }
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

        //EventTranslator lambda
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
