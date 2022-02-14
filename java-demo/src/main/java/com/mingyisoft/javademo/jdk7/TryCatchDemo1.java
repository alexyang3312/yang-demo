package com.mingyisoft.javademo.jdk7;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class TryCatchDemo1 {
	public static void main(String[] args) {
		// java7中自动关闭资源的try写法
		try (PrintStream ps = new PrintStream(new FileOutputStream("1.txt"));) {
			System.out.println("1234");
		} catch (Exception e) {

		}
	}
}
