package com.mingyisoft.javademo.thread.concurrent.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class BlockingQueueDemo {
	public static void main(String[] args) throws Exception {
		ConcurrentHashMap a;
		BlockingQueue<String> bq = new ArrayBlockingQueue<>(2);
		new Producer(bq).start();
		new Producer(bq).start();
		new Producer(bq).start();
		new Consumer(bq).start();
		//生产者必须等待消费者消费后才能继续执行
	}
}
/**
 * 生产者
 */
class Producer extends Thread{
	BlockingQueue<String> bq ; 
	public Producer(BlockingQueue<String> bq) {
		this.bq = bq;
	}
	public void run() {
		for(int i = 0 ; i < 10 ; i++) {
			try {
				bq.put("a");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				System.out.println(this.getName()+" 生产完毕"+bq);
			}
		}
	}
}
/**
 * 消费者
 */
class Consumer extends Thread{
	BlockingQueue<String> bq ; 
	public Consumer(BlockingQueue<String> bq) {
		this.bq = bq;
	}
	public void run() {
		for(int i = 0 ; i < 10 ; i++) {
			try {
				String take = bq.take();
				System.out.println(this.getName()+" 消费完毕 "+take+" "+bq);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
}