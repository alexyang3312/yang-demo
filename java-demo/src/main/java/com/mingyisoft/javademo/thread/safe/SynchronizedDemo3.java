package com.mingyisoft.javademo.thread.safe;

/**
 * synchronized锁的可重入性
 * @author alexyang
 *
 */
public class SynchronizedDemo3 {
	public static void main(String[] args) {
		SynchronizedDemo3Father f = new SynchronizedDemo3Son();
		f.eat();
	}
}

class SynchronizedDemo3Father {
	public synchronized void eat() {
		System.out.println("我是爸爸");
	}
}

class SynchronizedDemo3Son extends SynchronizedDemo3Father {
	public synchronized void eat() {
		System.out.println("我是儿子");
		super.eat();
	}
}