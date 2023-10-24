package com.mingyisoft.javademo.thread.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

/**
 * 自定义线程池
 */
public class CustomThreadPool {
    public static void main(String[] args) {
        CustomThreadPool customThreadPool = new CustomThreadPool(new LinkedBlockingQueue<>(10), 5);
        IntStream.rangeClosed(1, 5).forEach((i)-> {
            try {
                customThreadPool.execute(()-> {
                    System.out.println(Thread.currentThread().getName() + " =====");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    BlockingQueue<Runnable> taskQueue;  //任务队列
//    List<MyThread> threadList; //线程列表

    CustomThreadPool(BlockingQueue<Runnable> taskQueue, int threadSize){
        this.taskQueue = taskQueue;
//        threadList = new ArrayList<>(threadSize);

        // 循环创建线程，启动线程，并加入到线程列表中
        IntStream.rangeClosed(1, threadSize).forEach((i)-> {
            MyThread thread = new MyThread("my-task-thread-" + i);
            // 线程刚开始都start了，不太好，是否可以懒加载，待优化
            thread.start();
//            threadList.add(thread);
        });
    }

    public void execute(Runnable task) throws InterruptedException{
        taskQueue.put(task);
    }

    class MyThread extends Thread{
        public MyThread(String name){
            super(name);
        }

        public void run(){
            // 不断的从任务队列中获取任务并执行
            while(true){
                Runnable task = null;
                try {
                    task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
