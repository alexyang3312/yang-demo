package com.mingyisoft.javademo.thread.concurrent;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListDemo1 {
    public static void main(String[] args) {
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        list.add("test1");

        Thread addThread = new Thread(new Runnable() {
            @Override
            public void run() {
                list.add("test4");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        addThread.start();

    }
}
