package com.mingyisoft.javademo.thread.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 如果现在有存钱、取钱两个线程，要求不断重复存钱取钱操作，存钱后立刻取钱，
 * 不允许有两次连续的存钱，也不允许有两次连续的取钱，那我们就需要这两个线程之间有通信机制。
 * 
 * @author lenovo
 *
 */
public class ConditionDemo1 {
	public static void main(String[] args) {
		Bank b = new Bank();
		for (int i = 0; i < 10; i++) {
			new Thread(new SaveThread(b)).start();
			new Thread(new GetThread(b)).start();
		}
	}
}

class Bank {
	Integer count = 10;
	String flag = "save";

	final Lock lock = new ReentrantLock();
	final Condition condition = lock.newCondition();

	public void save() {
		try {
			lock.lock();
			if ("get".equals(flag)) {
				condition.await();
			} else {
				count++;
				System.out.println(Thread.currentThread().getName() + " 存钱后，金额=" + count);
				flag = "get";
				condition.signalAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void get() {
		try {
			lock.lock();
			if ("save".equals(flag)) {
				condition.await();
			} else {
				count--;
				System.out.println(Thread.currentThread().getName() + " 取钱后，金额=" + count);
				flag = "save";
				condition.signalAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}

class SaveThread implements Runnable {
	Bank bank;

	public SaveThread(Bank bank) {
		this.bank = bank;
	}

	public void run() {
		bank.save();
	}
}

class GetThread implements Runnable {
	Bank bank;

	public GetThread(Bank bank) {
		this.bank = bank;
	}

	public void run() {
		bank.get();
	}
}
