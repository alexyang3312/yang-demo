package com.mingyisoft.javademo.designpattern.chainofresponsibility.demo1;

public class MainTest {
    public static void main(String[] args) {
        Request request = new Request();
        request.requestStr = "123";
        Response response = new Response();

        FilterChain fc = new FilterChain();
        fc.addFilter(new AFilter())
                .addFilter(new BFilter())
                .addFilter(new CFilter());

        fc.doFilter(request, response, fc);
        System.out.println("request = " + request.requestStr);
        System.out.println("response = " + response.responseStr);
    }
}
