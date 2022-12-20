package com.mingyisoft.javademo.thread.safe;

public class SynchronizedDemo0 implements Runnable {
	public void run() {
		synchronized(this) {
			//this代表SynchronizedDemo0对象
			for(int i = 0; i<3; i++) {
				//模拟执行动作
				System.out.println(Thread.currentThread().getName()+" "+i);
			}
		}
	}
	
	public static void main(String[] args) {
		//只new了一个SynchronizedDemo0对象，让三个线程来共享，从而造成同步执行。
		SynchronizedDemo0 s = new SynchronizedDemo0();
		//新建三个线程
		Thread t1 = new Thread(s);
		Thread t2 = new Thread(s);
		Thread t3 = new Thread(s);
		t1.start();
		t2.start();
		t3.start();
	}
}
