package com.mingyisoft.javademo.thread.creation;

public class RunnableDemo1 {
	public static void main(String[] args) {
		RunnableDemo r1 = new RunnableDemo();
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r1);

		t1.start();
		t2.start();
	}
}

class RunnableDemo implements Runnable {
	 int i = 0;
	/**
	 * 线程要执行的代码
	 */
	public void run() {
		for (; i < 10; i++) {
			// Thread.currentThread().getName()可以获取当前线程名称
			System.out.println(Thread.currentThread().getName() + "==>" + i);
		}
	}
}
