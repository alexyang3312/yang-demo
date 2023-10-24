package com.mingyisoft.javademo.thread.concurrent;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo1 {
    public static void main(String[] args) {
        ConcurrentHashMap a = new ConcurrentHashMap();
        a.put("a",1);
        a.put("b",1);
    }
}
