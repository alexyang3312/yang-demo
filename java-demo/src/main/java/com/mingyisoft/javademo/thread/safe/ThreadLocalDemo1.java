package com.mingyisoft.javademo.thread.safe;

public class ThreadLocalDemo1 {
	public static void main(String[] args) {
		Person p = new Person();
		// 启动两个线程，两个线程共享person对象
		new ThreadDemo1(p).start();
		new ThreadDemo2(p).start();
	}

	static class ThreadDemo1 extends Thread {
		Person p;

		public ThreadDemo1(Person p) {
			this.p = p;
		}

		public void run() {
			System.out.println(this.getName() + " start " + p.getName());
			// 将线程局部变量赋值
			p.setName("张三");
			System.out.println(this.getName() + " end " + p.getName());
		}
	}

	static class ThreadDemo2 extends Thread {
		Person p;

		public ThreadDemo2(Person p) {
			this.p = p;
		}

		public void run() {
			System.out.println(this.getName() + " start " + p.getName());
			// 将线程局部变量赋值
			p.setName("李四");
			System.out.println(this.getName() + " end " + p.getName());
		}
	}

	static class Person {
		// 定义线程局部变量，每个线程都会保留该变量的一个副本，多个线程之间并不互相影响。
		private ThreadLocal<String> name = new ThreadLocal<>();

		public String getName() {
			return this.name.get();
		}

		public void setName(String name) {
			this.name.set(name);
		}
	}
}

