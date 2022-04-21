package com.mingyisoft.javademo.designpattern.chainofresponsibility.demo1;

public interface Filter {
    void doFilter(Request request, Response response, Filter filter);
}