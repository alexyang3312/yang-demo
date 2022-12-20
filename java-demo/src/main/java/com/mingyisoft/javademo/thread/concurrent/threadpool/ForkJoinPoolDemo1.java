package com.mingyisoft.javademo.thread.concurrent.threadpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolDemo1 {
	public static void main(String[] args) throws Exception{
		System.out.println(5/2);
		
		/**
		 * 创建连接池，如果不传入构造方法参数作为线程数，
		 * 则会默认使用Runtime.getRuntime().availableProcessors()的数量作为线程数。
		 */
		ForkJoinPool pool = new ForkJoinPool();
		/**
		 * 通过submit()和invoke()来指定任务调用
		 * submit()的参数是ForkJoinTask，ForkJoinTask代表一个可并行、合并的任务。
		 * ForkJoinTask有2个子类：RecursiveAction（没有返回值）和RecursiveTask（有返回值，需要使用泛型与Future）。
		 * 
		 */
		pool.submit(new PrintTask(0,10));
		//等待所有线程执行完毕，或超时退出
		pool.awaitTermination(2, TimeUnit.SECONDS);
		//关闭线程池
		pool.shutdown();
	}
}

class PrintTask extends RecursiveAction {
	// 每个小任务最多只能打印3个数字
	private static final int LIMIT = 3;
	//打印从start到end的数字
	private int start;
	private int end;

	public PrintTask(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected void compute() {
		if (end - start < LIMIT) {
			for (int i = start; i < end; i++) {
				System.out.println(Thread.currentThread().getName() + " " + i);
			}
		} else {
			int middle = (start+end)/2;
			//分解成2个小任务
			PrintTask left = new PrintTask(start,middle);
			PrintTask right = new PrintTask(middle,end);
			//2个小任务的并行执行
			left.fork();
			right.fork();
		}
	}
}
