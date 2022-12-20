package com.mingyisoft.javademo.thread.communication;

/**
 * 如果现在有存钱、取钱两个线程，要求不断重复存钱取钱操作，存钱后立刻取钱，
 * 不允许有两次连续的存钱，也不允许有两次连续的取钱，那我们就需要这两个线程之间有通信机制。
 *
 */
public class NotifyDemo1 {
	public static void main(String[] args) {
		Item item = new Item();
		for (int i = 0; i < 10; i++) {
			new ThreadGet(item,"get"+i).start();
			new ThreadSave(item,"save"+i).start();
		}
	}
}

/**
 * 被两个线程共享的对象所属的类
 */
class Item {
	int count = 0 ;
	String flag = "save";
}

/**
 * 存钱线程
 *
 */
class ThreadSave extends Thread {
	Item item;

	public ThreadSave(Item item,String name) {
		super(name);
		this.item = item;
	}

	public void run() {
		try {
			synchronized (item) {
				if ("get".equals(item.flag)) {
					item.wait();
				}

				if ("save".equals(item.flag)) {
					item.count++;
					System.out.println(Thread.currentThread().getName() + " 存钱后，金额=" + item.count);
					item.flag = "get";
					item.notifyAll();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
/**
 *	取钱线程
 */
class ThreadGet extends Thread {
	Item item;

	public ThreadGet(Item item,String name) {
		super(name);
		this.item = item;
	}

	public void run() {
		try {
			synchronized (item) {
				if ("save".equals(item.flag)) {
					item.wait();
//					System.out.println("-------------------"+
//							Thread.currentThread().getName() + "取钱被唤醒了，此刻的flag是"+item.flag);
					//取钱线程执行后调用item.notifyAll()，notifyAll()会唤醒所有处于item对象等待队列中的所有线程，即所有处于等待状态的存钱线程与取钱线程。
					//此刻，如果侥幸让一个取钱线程抢到了锁并执行，就会从item.wait()下面开始执行，在euqals判断时，因为flag=save而导致没有进入到if ("get".equals(item.flag)) 里，
					//因此这个线程执行完毕后，没有执行任何操作，那这个线程就相当于浪费掉了，save线程也是同理。
					//这就是所谓的“丢线程”
				}

				if ("get".equals(item.flag)) {
					item.count--;
					System.out.println(Thread.currentThread().getName() + " 取钱后，金额=" + item.count);
					item.flag = "save";
					item.notifyAll();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
