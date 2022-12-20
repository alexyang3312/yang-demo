package com.mingyisoft.javademo.string;

public class StringDemo1 {
	public static void main(String[] args) {
		/**
		 * 小驼峰转蛇形
		 */
		String s = "orderOptionId";
		String zz = s.replaceAll("[A-Z]", "_$0").toLowerCase();
		System.out.println(zz);

		String aaa = "ababc";
		System.out.println(aaa.lastIndexOf("ab"));
	}
}
