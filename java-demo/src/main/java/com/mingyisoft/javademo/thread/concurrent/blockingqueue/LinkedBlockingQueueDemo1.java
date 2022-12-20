package com.mingyisoft.javademo.thread.concurrent.blockingqueue;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueDemo1 {
	static LinkedBlockingQueue<String> a = new LinkedBlockingQueue<String>(10);
	
	public static void main(String[] args) {
		for(int i = 0 ; i < 100 ; i++) {
//			a.add(i+"");
			try {
				a.put(i+"");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("i="+i);
		}
		
	}
}
