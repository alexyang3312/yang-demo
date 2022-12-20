package com.mingyisoft.javademo.thread.creation;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableDemo1 {
	public static void main(String[] args) {
		//注意Callable需要泛型支持
		FutureTask<Boolean> ft1 = new FutureTask<>(new CallableDemo(1));
		FutureTask<Boolean> ft2 = new FutureTask<>(new CallableDemo(0));
		FutureTask<Boolean> ft3 = new FutureTask<>(new CallableDemo(2));
		Thread t1 = new Thread(ft1);
		Thread t2 = new Thread(ft2);
		Thread t3 = new Thread(ft3);

		t1.start();
		t2.start();
		t3.start();

		// 获取线程返回值
		try {
			//get方法获取返回值时候会导致主线程阻塞，直到call()结束并返回为止
			System.out.println(ft1.get());
			System.out.println(ft2.get());
			System.out.println(ft3.get());
		} catch (Exception e) {
			//可以catch住线程体里的异常
			e.printStackTrace();
		}
		
		t1.start();
	}
}

class CallableDemo implements Callable<Boolean> {
	int flag;
	int i = 0;

	public CallableDemo(int flag) {
		this.flag = flag;
	}

	/**
	 * 线程要执行的代码
	 */
	public Boolean call() throws Exception {
		for (; i < 10; i++) {
			// Thread.currentThread().getName()可以获取当前线程名称
			System.out.println(Thread.currentThread().getName() + "==>" + i);
		}

		if (flag == 1) {
			return true;
		} else if(flag == 0){
			return false;
		} else {
			throw new Exception("哇咔咔咔");
		}
	}
}