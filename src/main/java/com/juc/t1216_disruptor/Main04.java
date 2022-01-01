package com.juc.t1216_disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.concurrent.*;

public class Main04 {

    public static void main(String[] args) {
        multiConsumer();



    }

    static void main_producerType(){
        LongEventFactory factory = new LongEventFactory();
        int bufferSize = 1024;
        /**
         * 生产者类型
         * ProducerType 默认多线程，多线程对ringBuffer访问会带锁，单线程没有锁
         * 但是如果生产者只有一个的话，用单线程性能会更好
         */
        /**
         * WaitStrategy等待策略, 当ringbuffer满时的策略
         * BlockingWaitStrategy 线程阻塞，等待被唤醒
         * BusySpinWaitStrategy 自旋等待，可能比较耗cpu
         * TimeoutBlockingWaitStrategy 设置了等待时间
         * LiteBlockingWaitStrategy 线程阻塞进一步优化
         * LiteTimeoutBlockingWaitStrategy 设置了等待时间，超过时间抛异常
         * PhasedBackoffWaitStrategy
         * YieldingWaitStrategy 尝试100次，然后Thread.yield()让出cpu
         * SleepingWaitStrategy 睡眠等待, 睡好了继续
         */

        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
    }

    /**
     * 多个消费者如何指定
     */
    static void multiConsumer(){
        LongEventFactory factory = new LongEventFactory();
        int bufferSize = 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, Executors.defaultThreadFactory(), ProducerType.MULTI, new SleepingWaitStrategy());

        LongEventHandler h1 = new LongEventHandler();
        LongEventHandler h2 = new LongEventHandler();
        disruptor.handleEventsWith(h1, h2);

        //消费者的异常处理，避免一个消费者出异常导致别的消费者无法消费
        //disruptor.handleExceptionsFor()


        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        final int threadCount = 10;
        CyclicBarrier barrier = new CyclicBarrier(threadCount);
        ExecutorService service = Executors.newCachedThreadPool();
        for (long i=0; i<threadCount; i++){
            final  long threadNum = i;
            service.submit(()->{
                System.out.printf("Thread %s ready to start!\n", threadNum);
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

                for (int j=0; j<10; j++){
                    ringBuffer.publishEvent((event, sequence) -> {
                        event.setValue(threadNum);
                        System.out.println("生产了 " + threadNum);
                    });
                }
            });
        }

        service.shutdown();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(LongEventHandler.count);
    }
}
