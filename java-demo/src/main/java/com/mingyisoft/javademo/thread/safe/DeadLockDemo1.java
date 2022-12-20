package com.mingyisoft.javademo.thread.safe;
/**
 * 死锁
 * @author lenovo
 *
 */
public class DeadLockDemo1 {
	public static void main(String[] args) {
		A a = new A();
		B b = new B();
		
		/**
		 * 线程1调用获得b对象的监视器锁，线程被暂停了，但还没有释放。
		 * 线程2调用并获得a对象的监视器锁，线程被暂停了，但还没有释放。
		 * 线程1恢复并调用a对象的监视器锁，但a对象的锁被线程2持有还没有释放，因此被阻塞。
		 * 线程2恢复并调用b对象的监视器锁，但b对象的锁被先吃1持有还没有释放，并且线程1还在等待线程2释放a对象的锁，
		 * 最后导致两个线程互相等待对方释放锁，因此出现死锁。
		 */
		
		//线程1
		new Thread(new Runnable() {
			public void run() {
				//
				b.one(a);
			}
		}).start();
		
		//线程2
		new Thread(new Runnable() {
			public void run() {
				//
				a.one(b);
			}
		}).start();
	}
}

class A {
	public synchronized void one(B b) {
		System.out.println(Thread.currentThread().getName()+" 进入A one");
		
		try {
			Thread.sleep(1*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" 调用B two");
		b.two();
	}

	public synchronized void two() {
		System.out.println("进入A two");
	}
}

class B {
	public synchronized void one(A a) {
		System.out.println(Thread.currentThread().getName()+" 进入B one");
		try {
			Thread.sleep(1*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" 调用A two");
		a.two();
	}
	
	public synchronized void two() {
		System.out.println("B two");
	}
}