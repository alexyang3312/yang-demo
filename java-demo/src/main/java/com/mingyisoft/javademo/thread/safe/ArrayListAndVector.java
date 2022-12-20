package com.mingyisoft.javademo.thread.safe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 下面的代码，在主线程中new了一个非线程安全的ArrayList，然后开100个线程分别向这个ArrayList里面添加元素，
 * 每个线程添加100个元素，等所有线程执行完成后，这个ArrayList的size应该是多少？应该是100个？
 * 
 * @author yangmh
 *
 */
public class ArrayListAndVector {
	public static void main(String[] args) {
		// 用来测试的ArrayList
		List<Object> list = new ArrayList<Object>();
		
//		List<Object> list = new Vector<Object>();

		/**
		 * 上面的输出结果发现，并不是预想中的10000，多运行几次可以发现每次结果都不一样。
		 * 这就是非线程安全带来的问题了。上面的代码如果用于生产环境，就会有隐患就会有BUG了。
		 * 再用线程安全的Vector来进行测试，上面代码改变一处，new ArrayList<Object>()改成new
		 * Vector<Object>()，再运行程序，输出结果： 10000
		 * 无论运行多少次，输出结果都是10000，因为Vector是线程安全的，在多线程操作同一个Vector对象时，不会有任何问题。
		 * 有兴趣的话还可以再换成LinkedList试试，同样还会出现ArrayList类似的问题，因为LinkedList也是非线程安全的。
		 * 
		 */

		// 线程数量(10)
		int threadCount = 100;

		// 用来让主线程等待threadCount个子线程执行完毕
		CountDownLatch countDownLatch = new CountDownLatch(threadCount);

		// 启动threadCount个子线程
		for (int i = 0; i < threadCount; i++) {
			Thread thread = new Thread(new MyThread(list, countDownLatch));
			thread.start();
		}

		try {
			// 主线程等待所有子线程执行完成，再向下执行
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// List的size
		System.out.println(list.size());
	}
}

class MyThread implements Runnable {
	private List<Object> list;

	private CountDownLatch countDownLatch;

	public MyThread(List<Object> list, CountDownLatch countDownLatch) {
		this.list = list;
		this.countDownLatch = countDownLatch;
	}

	public void run() {
		// 每个线程向List中添加100个元素
		for (int i = 0; i < 100; i++) {
			list.add(new Object());
		}

		// 完成一个子线程
		countDownLatch.countDown();
	}
}