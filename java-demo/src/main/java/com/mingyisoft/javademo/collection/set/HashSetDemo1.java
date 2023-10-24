package com.mingyisoft.javademo.collection.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HashSetDemo1 {
	public static void main(String[] args) { 
		Set<String> s = new HashSet<String>();
		for(int i = 0 ; i < 3 ; i ++){
			s.add(i+"");
		}
		s.add("1");// 重复数据插不进去
		// 重复数据会返回false
		System.out.println(s.add("1"));
		s.add(null);// null数据最多只能插进去一个
		s.add(null);
		
		Iterator<String> iter = s.iterator();
		while(iter.hasNext()){
			//并没有严格按照顺序走，所以是无序的。
			System.out.println(iter.next());
		}
		
		System.out.println("----");
		
		s.addAll(Arrays.asList("one two three".split(" ")));
		for(String z : s){
			System.out.println(z);
		}
	}
}
