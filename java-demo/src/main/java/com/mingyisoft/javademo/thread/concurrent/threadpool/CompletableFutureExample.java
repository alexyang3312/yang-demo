package com.mingyisoft.javademo.thread.concurrent.threadpool;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // 执行任务并返回结果
            return 10 + 20;
        });

        future.thenAccept(result -> {
            System.out.println("Result: " + result);
        });

        Thread.sleep(1000); // 等待异步任务完成

        future.join(); // 等待任务完成并获取结果
    }
}
