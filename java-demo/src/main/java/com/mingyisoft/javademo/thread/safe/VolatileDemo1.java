package com.mingyisoft.javademo.thread.safe;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class VolatileDemo1 {
//    public static volatile boolean flag = false;
public static boolean flag = false;
    public static void main(String[] args) {
        ReentrantReadWriteLock a = null;
        ReentrantLock b = null;
        StampedLock c = null;

        Thread t1 = new Thread() {
            public void run(){
                int i = 1;
                while(!flag){
                    i++;
                }
                System.out.println("i = " + i);
            }
        };
        t1.start();
        try {
            Thread.sleep(1000);
            flag = true;
            System.out.println("flag = " + flag);
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
    }
}
