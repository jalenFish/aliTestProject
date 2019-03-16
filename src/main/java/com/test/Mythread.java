package com.test;

/**
 * Created by yjl on 2019-02-25.
 */
public class Mythread extends Thread {
    private int count = 5;
    @Override
    public void run() {
        count --;
        System.out.println(this.currentThread().getName()+"   count:"+count);

    }

    public static void main(String[] args) {
        Mythread mythread = new Mythread();
        Thread thread1 = new Thread(mythread,"thread1");
        Thread thread2 = new Thread(mythread, "thread2");
        Thread thread3 = new Thread(mythread, "thread3");
        Thread thread4 = new Thread(mythread, "thread4");
        Thread thread5 = new Thread(mythread, "thread5");
        mythread.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}
