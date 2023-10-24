package com.mingyisoft.javademo.jdk8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * List<User>变List<Integer>
 * @author alexyang
 *
 */
public class StreamDemo2 {
	public static void main(String[] args) {
		List<User> userList = new ArrayList<>();
		
		userList.add(new User(1,"a"));
		userList.add(new User(2,"b"));
		userList.add(new User(3,"c"));
		
		List<Integer> reutrnIntegerList = userList.stream().map(User::getId)
			    .collect(Collectors.toList());
		
		for(Integer t : reutrnIntegerList) {
			System.out.println(t);
		}
	}

	static class User{
		public User(Integer id,String name) {
			this.id = id;
			this.name = name;
		}
		private Integer id;
		private String name;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}


