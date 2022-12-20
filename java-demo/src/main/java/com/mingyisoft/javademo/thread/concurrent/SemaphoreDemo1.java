package com.mingyisoft.javademo.thread.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo1 {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);//3个空位
        for (int i = 0; i <6 ; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();// 抢到
                    System.out.println(Thread.currentThread().getName()+" 抢到");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println(Thread.currentThread().getName()+" 释放");
                    semaphore.release();// 释放
                }
            },String.valueOf(i)).start();
        }


    }
}
