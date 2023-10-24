package com.mingyisoft.javademo.thread.control;

import java.util.concurrent.*;

public class ExecutorServiceExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<Integer> task = new MyTask();
        Future<Integer> future = executorService.submit(task);
        int result = future.get();
        System.out.println("Result: " + result);
        executorService.shutdown();
    }

    static class MyTask implements Callable<Integer> {
        @Override
        public Integer call() {
            // 执行任务并返回结果
            return 10 + 20;
        }
    }
}
