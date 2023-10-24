package com.mingyisoft.demo;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@SpringBootApplication
@MapperScan("com.mingyisoft.demo.user.dao")
@EnableCaching//用于缓存标签
public class MingyisoftDemoApplication {
	public static final Logger logger = LoggerFactory.getLogger("aaa");

	public static void main(String[] args) {
		ExecutorService a;
//		SpringApplication.run(MingyisoftDemoApplication.class, args);
	}
}