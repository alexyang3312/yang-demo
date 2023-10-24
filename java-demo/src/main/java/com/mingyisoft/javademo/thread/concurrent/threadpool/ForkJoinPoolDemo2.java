package com.mingyisoft.javademo.thread.concurrent.threadpool;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;


public class ForkJoinPoolDemo2 {


    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        LoveTask task = new LoveTask("I love you");
        int result = forkJoinPool.invoke(task);
        System.out.println("Final Result: " + result);

        Condition a;


    }


    static class LoveTask extends RecursiveTask<Integer> {
        private final String message;

        public LoveTask(String message) {
            this.message = message;
        }

        @Override
        protected Integer compute() {
            if (message.equals("I love you")) {
                System.out.println(message);
                return 1;
            } else {
                LoveTask task1 = new LoveTask("I");
                LoveTask task2 = new LoveTask("love");
                LoveTask task3 = new LoveTask("you");

                task1.fork();
                task2.fork();
                task3.fork();

                int result1 = task1.join();
                int result2 = task2.join();
                int result3 = task3.join();

                return result1 + result2 + result3;
            }
        }
    }
}