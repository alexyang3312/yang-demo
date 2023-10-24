package com.mingyisoft.javademo.map.hashmap;

import java.util.HashMap;
import java.util.Map;

public class HashMapDemo1 {
	public static void main(String[] args) {
		Map m = new HashMap();

		m.put("1", "one");
		m.put("2", null);

		System.out.println(m.get("1"));
		// 当查询不存在的key时，返回null

		if (m.containsKey("1")) {
			System.out.println("1 yes ");
		} else {
			System.out.println("1 no ");
		}
		
		if (m.containsValue("one")) {
			System.out.println("one yes ");
		} else {
			System.out.println("one no ");
		}
		
		System.out.println(m);
	}
}
