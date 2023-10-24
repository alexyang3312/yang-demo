package com.mingyisoft.javademo.collection.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HashMapDemo1 {
	public static void main(String[] args) {


		Map<Person,Person> m = new HashMap<Person,Person>();
		Person p1 = new Person();
		Person p2 = new Person();

		p1.setName("alex");
		p1.setAge(20);

		p2.setName("frank");
		p2.setAge(21);

		m.put(p1, p1);
		m.put(p2, p2);
		
		//如果没有重写hashcode和equals，那么hashMap会默认存储2个属性一样的对象。那如果key是String呢？
		for(Entry<Person, Person> e : m.entrySet()) {
			System.out.println(e.getKey().getName()+" "+e.getValue().getAge());
		}
		
		Map<String,Person> m1 = new HashMap<String,Person>();
		m1.put("1", p1);
		m1.put("2", p2);
		//因为String也重写了equals和hashcode
		for(Entry<String, Person> e : m1.entrySet()) {
			System.out.println(e.getKey()+" "+e.getValue());
		}
	}
}

class Person {
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