package com.mingyisoft.demo.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-provider")
public interface ProviderFeignClient {
    @GetMapping("/hello")
    String hello(@RequestParam(value = "name", defaultValue = "xxx", required = false) String name);
}
