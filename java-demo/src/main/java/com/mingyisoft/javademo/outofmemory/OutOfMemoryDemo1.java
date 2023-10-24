package com.mingyisoft.javademo.outofmemory;

public class OutOfMemoryDemo1 {
	public static void main(String[] args) {
		while(true){
			new Thread(new Runnable() {
	            public void run() {
	            	for(int n = 0 ; n < 10000000;n++){
	            		String a = new String(""+n);
		            	System.out.println(a);
		            	Person p = new Person();
		            	p.setName("aaaa"+n);
		            	p.setAge(n);
		            	System.out.println(p);
	            	}
	            	
	            }
	            }).start();
		}
		
		
		
	}
}

class Person{
	private String name;
	private Integer age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
}
