package com.mingyisoft.javademo.xxx;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SumCalculator {

    public static void main(String[] args) throws Exception{
        CountDownLatch countDownLatch = new CountDownLatch(10);
        int[] numbers = new int[10000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        // 使用AtomicLong来保存累加结果
        AtomicLong sum = new AtomicLong(0);

        // 创建 ThreadPoolExecutor，使用10个线程
        int corePoolSize = 10;
        int maxPoolSize = 10;
        long keepAliveTime = 1;
        TimeUnit unit = TimeUnit.MINUTES;
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, unit, workQueue);

        // 假设10个线程
        int batchSize = numbers.length / 10;
        for (int i = 0; i < 10; i++) {
            int startIndex = i * batchSize;
            int endIndex = (i + 1) * batchSize;
            executor.submit(new SumTask(numbers, startIndex, endIndex, sum, countDownLatch));
        }

        countDownLatch.await();
        // 输出累加结果
        System.out.println("Sum----: " + sum.get());

    }
}

class SumTask implements Runnable {
    private final int[] numbers;
    private final int startIndex;
    private final int endIndex;
    private CountDownLatch countDownLatch;
    private final AtomicLong sum;

    public SumTask(int[] numbers, int startIndex, int endIndex, AtomicLong sum, CountDownLatch countDownLatch) {
        this.numbers = numbers;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.countDownLatch = countDownLatch;
        this.sum = sum;
    }

    @Override
    public void run() {
        long localSum = 0;
        for (int i = startIndex; i < endIndex; i++) {
            localSum += numbers[i];
        }
        // 使用原子类的addAndGet方法进行累加操作，保证线程安全
        sum.addAndGet(localSum);
        countDownLatch.countDown();
    }
}
