package com.juc.t1216_disruptor;

/**
 * disruptor
 * 开发步骤
 * 1.定义Event，队列中要处理的元素
 * 2.定义Event工厂，用于填充队列
 *      disruptor初始化时，会调用Event工厂，对ringBuffer提前进行内存分配
 * 3.定义EventHandler(消费者)，处理容器中的元素
 */
public class T_Doc {
}
