package com.mingyisoft.javademo.thread.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo1 {
    static AtomicInteger a = new AtomicInteger(0); // 使用原子类，运行结果是2000000
//    static int a = 0; // 不使用原子累，运行结果不是2000000
    static final int THREAD_COUNT = 20;

    static void increase(){
        a.incrementAndGet();
//        a++;
    }

    public static void main(String[] args) throws Exception{
        System.out.println("aaaaa");
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0 ; i < THREAD_COUNT; i++){
          threads[i] = new Thread(new Runnable() {
              @Override
              public void run() {
                  for(int i = 0 ; i < 100000; i++){
                      increase();
                  }
              }
          });
          threads[i].start();
        }

        Thread.sleep(5 * 1000);
        System.out.println(a);
    }
}
