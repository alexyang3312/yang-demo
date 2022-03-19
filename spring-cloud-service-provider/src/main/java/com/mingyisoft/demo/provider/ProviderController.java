package com.mingyisoft.demo.provider;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ProviderController {
    // @RequestMapping(value = "/hello")
    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        System.out.println("name==="+name);
        return "hello: " + name;
    }

    @GetMapping("/hello2")
    public String hello2(@RequestParam String name) {
        System.out.println("name2==="+name);
        return "hello2: " + name;
    }

    //AtomicLong 类支持线程安全的自增自减操作
    private AtomicLong atomicLong=new AtomicLong(1);

    /**
     * 此方法演示慢调用过程下的限流
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/hello3")
    public String hello3() throws InterruptedException {
        //获取自增对象的值,然后再加1
        long num=atomicLong.getAndIncrement();
        if(num%2==0){//模拟50%的慢调用比例
            Thread.sleep(200);
        }
        return "hello3 slow...";
    }

}