package com.mingyisoft.javademo.thread.safe;

public class SynchronizedDemo1 {
	public static void main(String[] args) throws Exception {
		Item item1 = new Item();
		item1.count = 1;
		item1.name = "Java编程思想";

		SynchronizedThread r1 = new SynchronizedThread(item1);
		// 开启10个线程来购买商品
		for (int i = 0; i < 10; i++) {
			new Thread(r1).start();
		}

		Thread.sleep(1 * 1000);
		System.out.println(item1.count);
	}
}

/**
 * 模拟商品
 */
class Item {
	// 商品名称
	String name;
	// 商品库存
	Integer count;
}

/**
 * 模拟购买线程
 */
class SynchronizedThread implements Runnable {
	private Item item;

	public SynchronizedThread(Item item) {
		this.item = item;
	}

	public void run() {
		// 每个线程都要先获取item对象的监视器的锁，才能进入。
		synchronized (item) {
			if (item.count > 0) {
				System.out.println("购买成功");
				item.count--;
			} else {
				System.out.println("购买失败，库存不足");
			}
		}
	}
}