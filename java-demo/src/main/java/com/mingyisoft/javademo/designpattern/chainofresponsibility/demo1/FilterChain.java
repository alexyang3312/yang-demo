package com.mingyisoft.javademo.designpattern.chainofresponsibility.demo1;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements Filter{
    List<Filter> filters = new ArrayList<Filter>();
    int index = 0;

    public FilterChain addFilter(Filter f) {
        this.filters.add(f);
        return this;
    }

    @Override
    public void doFilter(Request request, Response response, Filter filter) {
        if (index == filters.size())
            return;
        Filter f = filters.get(index);
        index++;
        f.doFilter(request, response, filter);
    }
}