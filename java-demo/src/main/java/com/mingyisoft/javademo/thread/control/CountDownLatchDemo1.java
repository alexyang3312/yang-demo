package com.mingyisoft.javademo.thread.control;

import java.util.concurrent.CountDownLatch;
/**
 * 等待大量线程执行完之后，主线程再接着往下走，在大规模场景下，比join更好用。
 * @author lenovo
 *
 */
public class CountDownLatchDemo1 {
	public static void main(String[] args) {
		// 计数器
		Counter counter = new Counter();

		// 线程数量(1000)
		int threadCount = 1000;

		// 用来让主线程等待threadCount个子线程执行完毕
		CountDownLatch countDownLatch = new CountDownLatch(threadCount);

		// 启动threadCount个子线程
		for (int i = 0; i < threadCount; i++) {
			Thread thread = new Thread(new MyThread(counter, countDownLatch));
			thread.start();
		}

		try {
			// 主线程等待所有子线程执行完成，再向下执行
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// 计数器的值
		System.out.println(counter.getCount());
	}

}

class MyThread implements Runnable {
	private Counter counter;

	private CountDownLatch countDownLatch;

	public MyThread(Counter counter, CountDownLatch countDownLatch) {
		this.counter = counter;
		this.countDownLatch = countDownLatch;
	}

	public void run() {
		// 每个线程向Counter中进行10000次累加
		for (int i = 0; i < 10000; i++) {
			counter.addCount();
		}

		// 完成一个子线程
		countDownLatch.countDown();
	}
}

class Counter {
	private int count = 0;

	public int getCount() {
		return count;
	}

	/**
	 * 上面只是在addCount()方法中加上了synchronized同步控制，就成为一个线程安全的计数器了。
	 */
	public synchronized void addCount() {
		count++;
	}
}
