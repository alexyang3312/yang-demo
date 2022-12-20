package com.mingyisoft.javademo.thread.concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo1 {
	public static void main(String[] args) {
		//newCachedThreadPool：

		// newFixedThreadPool：
		ExecutorService pool = Executors.newFixedThreadPool(3);
		Runnable t = new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName());
			}
		};
		
		for(int i = 0 ; i < 9 ;i++) {
			pool.submit(t);
		}
		//关闭线程池
		pool.shutdown();
		/**
		 * 调用shutdown()后线程池不再接受新任务，但会将以前所有已提交的任务执行完成，
		 * 待所有任务执行完毕后，线程池内的所有线程会全部死亡。
		 */


		// newScheduledThreadPool：
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(// run every 1 sec to collect the raw data values
				new Thread(new Runnable() {
					public void run() {
						System.out.println("2222");
					}
				}), 0, 1, TimeUnit.SECONDS);

		// newSingleThreadExecutor：

	}
}