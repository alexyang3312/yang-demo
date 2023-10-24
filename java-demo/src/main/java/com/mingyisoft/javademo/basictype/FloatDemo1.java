package com.mingyisoft.javademo.basictype;

import java.math.BigDecimal;

public class FloatDemo1 {
	public static void main(String[] args) {
		// 浮点数无法进行精准运算，因为底层二进制无法精准表述带小数点的十进制
		double a = 2.0 - 1.8;
		// 输出0.19999999999999996
		System.out.println(a);
		
		float b = 2.0f - 1.8f;
		System.out.println(b);
		
		// 解决方案
		BigDecimal b1 = new BigDecimal("2.0");
		BigDecimal b2 = new BigDecimal("1.8");
		System.out.println(b1.subtract(b2).doubleValue());
	}
}
