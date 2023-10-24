package com.mingyisoft.javademo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@EnableAsync// 开启异步任务
public class JavaDemoApplication {
    public static void main(String[] args) {
//        SpringApplication.run(JavaDemoApplication.class, args);
        System.out.println(123);
    }
}