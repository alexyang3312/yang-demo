package com.mingyisoft.javademo.designpattern.chainofresponsibility.demo1;

public class BFilter implements Filter{
    @Override
    public void doFilter(Request request, Response response, Filter filter) {
        request.requestStr = request.requestStr.replace("2", "B");
        filter.doFilter(request, response, filter);
        response.responseStr += "------BFilter";
    }
}