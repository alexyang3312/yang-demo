package com.mingyisoft.javademo.thread.creation;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadDemo1 {
	public static void main(String[] args) {
		// 创建三个线程对象
		ThreadDemo t1 = new ThreadDemo();
		ThreadDemo t2 = new ThreadDemo();
		ThreadDemo t3 = new ThreadDemo();
		// 启动三个线程对象
		t1.start();
		t2.start();
		t3.start();
		
		Thread t4 = new Thread(new Runnable() {
			public void run() {
				System.out.println(5 / 0);
			}
		});
		t4.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("捕获住了线程的异常");
				e.printStackTrace();
			}
		});
		t4.start();
		

	}
}

class ThreadDemo extends Thread {
	/**
	 * 线程要执行的代码
	 */
	public void run() {
		for (int i = 0; i < 10; i++) {
			// this.getName()可以获取当前线程名称
			System.out.println(this.getName() + " ==>" + i);
		}
	}
}