package com.mingyisoft.javademo.thread.concurrent.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueDemo1 {
static ArrayBlockingQueue<String> a = new ArrayBlockingQueue<String>(10);
	
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
