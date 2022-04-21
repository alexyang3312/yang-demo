package com.mingyisoft.javademo.designpattern.chainofresponsibility.demo1;

public class AFilter implements Filter{
    @Override
    public void doFilter(Request request, Response response, Filter filter) {
        request.requestStr = request.requestStr.replace("1", "A");
        filter.doFilter(request, response, filter);
        response.responseStr += "------AFilter";
    }
}
