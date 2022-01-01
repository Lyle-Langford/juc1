package com.juc.t1130;

import java.util.concurrent.*;

/**
 * Callable接近于Runnable，但是可以返回执行结果
 * Future 存储未来执行出来的结果
 */
public class T03_Callable {

    public static void main(String[] args) {
        Callable<String> c = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello Callable";
            }
        };

        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(c);

        try {
            System.out.println(future.get()); //get为阻塞方法，会等callable执行完才会返回

            service.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
