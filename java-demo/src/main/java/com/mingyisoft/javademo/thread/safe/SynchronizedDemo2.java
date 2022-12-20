/** 
 * File: XXX.java<br> 
 * Project: deleteme<br> 
 * Package: com.xxx.thread<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年2月28日 下午2:42:46
 * @author yangmh
 */ 
package com.mingyisoft.javademo.thread.safe;

public class SynchronizedDemo2 {
	static int j ;
	
	public static void main(String[] args) {
		Thread t1 = new XX();
		Thread t2 = new XX2();
		Thread t3 = new Thread(new XX3());
		Thread t4 = new Thread(new XX4());
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();	
	}
	
	public static synchronized void add(){
		j++;
		System.out.println("加1后，j="+j);
	}
	
	public static synchronized void delete(){
		j--;
		System.out.println("减1，j="+j);
	}
}
class XX extends Thread{
	public void run(){
		SynchronizedDemo2.add();
	}
}
class XX2 extends Thread{
	public void run(){
		SynchronizedDemo2.add();
	}
}
class XX3 implements Runnable{
	public void run() {
		SynchronizedDemo2.delete();
	}
}
class XX4 implements Runnable{
	public void run() {
		SynchronizedDemo2.delete();
	}
}
