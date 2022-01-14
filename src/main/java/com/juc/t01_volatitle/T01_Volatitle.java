package com.juc.t01_volatitle;

public class T01_Volatitle {

    public static void main(String[] args) {
        visible();
    }

    static volatile boolean isRuning = true;
    //可见性
    private static void visible(){
        new Thread(()->{
            while (isRuning){

            }
            System.out.println("end");
        }).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isRuning = false;
        System.out.println("isRuning is false");
    }
}
