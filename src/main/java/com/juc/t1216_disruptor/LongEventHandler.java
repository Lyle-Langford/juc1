package com.juc.t1216_disruptor;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent> {

    public static long count = 0;

    /**
     *
     * @param event
     * @param sequence ringBuffer的序列号
     * @param endOfBatch 是否为最后一个元素
     * @throws Exception
     */
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        count ++;
        System.out.println("[" + Thread.currentThread().getContextClassLoader() + "] event:" + event.getValue() + " 序号:" + sequence);
    }
}
