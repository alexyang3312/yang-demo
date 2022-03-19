package com.mingyisoft.demo.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    ProviderFeignClient providerFeignClient;

    @GetMapping("/open-feign-test")
    public String openFeignTest() {
        return providerFeignClient.hello("gege");
    }

    @GetMapping("/rest-template-test")
    public String restTemplateTest() {
        // 通过Spring Cloud Common中的负载均衡接口选取服务提供节点实现接口调用
        ServiceInstance serviceInstance = loadBalancerClient.choose("service-provider");
        String url = serviceInstance.getUri() + "/hello?name=didi";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return "Invoke : " + url + ", return : " + result;

        /**
         * RestTemplate还有一种方式：
         * <dependency>
         * 	<groupId>org.springframework.cloud</groupId>
         * 	<artifactId>spring-cloud-starter-ribbon</artifactId>
         * </dependency>
         *
         * @LoadBalanced
         * @Bean
         * public RestTemplate restTemplate(){
         * 		return new RestTemplate();
         *        }
         *
         * @Autowired
         * RestTemplate restTemplate;
         *
         * return restTemplate.getForObject("http://nacos-provider/hi?name=resttemplate",String.class);
         */
    }
}
