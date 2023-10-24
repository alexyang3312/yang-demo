package com.mingyisoft.javademo.xxx;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class AAA {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Step 1: 1");

        Callable<Integer> step2Callable = () -> {
            System.out.println("Step 2: 2");
            Thread.sleep(10000);
            System.out.println("22222");
            return 2;
        };

        Callable<Integer> step3Callable = () -> {
            System.out.println("Step 3: 3");
            Thread.sleep(5000);
            System.out.println("33333");
            return 3;
        };

        FutureTask<Integer> step2Task = new FutureTask<>(step2Callable);
        FutureTask<Integer> step3Task = new FutureTask<>(step3Callable);

        new Thread(step2Task).start();
        new Thread(step3Task).start();

        int step2Result = step2Task.get();
        int step3Result = step3Task.get();

        int result = step2Result + step3Result;
        System.out.println("Step 4: " + result);
    }
}
