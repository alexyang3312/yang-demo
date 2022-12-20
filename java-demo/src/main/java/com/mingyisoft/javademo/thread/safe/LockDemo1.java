package com.mingyisoft.javademo.thread.safe;

import java.util.concurrent.locks.ReentrantLock;

public class LockDemo1 {
	// 定义lock锁对象,每个lock对象对应一个要加锁的对象，能达到synchronized的效果。
	private final ReentrantLock lock = new ReentrantLock();

	public void doSomething1() {
		//开启锁
		lock.lock();
		System.out.println(Thread.currentThread().getName() + " do something1...");
	}

	public void doSomething2() {
		System.out.println(Thread.currentThread().getName() + " do something2...");
		// 释放锁，可以跨方法
		lock.unlock();
	}

	public static void main(String[] args) {
		LockDemo1 lockDemo1 = new LockDemo1();
		LockThread lockThread = new LockThread(lockDemo1);
		for (int i = 0; i < 20; i++) {
			new Thread(lockThread).start();
		}
	}
}

class LockThread implements Runnable {
	LockDemo1 lockDemo1;

	public LockThread(LockDemo1 lockDemo1) {
		this.lockDemo1 = lockDemo1;
	}

	public void run() {
		lockDemo1.doSomething1();
		lockDemo1.doSomething2();
	}
}
