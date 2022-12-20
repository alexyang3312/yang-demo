package com.mingyisoft.javademo.thread.concurrent;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo1 {
    public static void main(String[] args) throws InterruptedException {
        int threadNum = 4;
        CyclicBarrier barrier = new CyclicBarrier(threadNum, new MyThread());
        for (int i = 0; i < threadNum; i++) {
            Thread.sleep(3000);      //以睡眠来模拟操作
            new TestThread(barrier).start();
        }
    }

    static class TestThread extends Thread {
        private CyclicBarrier cyclicBarrier;
        public TestThread(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("线程" + Thread.currentThread().getName() + "执行完毕，等待其他线程执行完成");
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("一起完成啦");
        }
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.err.println("我是特殊任务");
        }
    }
}
