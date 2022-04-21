package com.mingyisoft.javademo.designpattern.chainofresponsibility.demo1;

public class CFilter implements Filter {
    @Override
    public void doFilter(Request request, Response response, Filter filter) {
        request.requestStr = request.requestStr.replace("3","C");
        filter.doFilter(request, response, filter);
        response.responseStr += "-------CFilter";
    }
}
