package com.mingyisoft.javademo.collection.list;

import java.util.Vector;

/**
 * 加上synchronized关键字保证同一时间内只有一个线程访问共享资源
 *
 * 报错的问题的原因是：例子中的线程连续调用了两个或者两个以上的同步方法，
 *
 * removeThread线程会首先调用size方法获取大小，接着调用remove方法移除相应位置的元素，
 * 而printThread线程也是先调用size方法获取大小，接着调用get方法获取相应位置的元素
 *
 * 假设vector大小是5，此时printThread线程执行到i=4的时候，
 * 进入for循环但是在执行输出之前，线程的CPU时间片到了，此时printThread则转入到就绪状态
 *
 * 此时removeThread线程获得CPU的执行权，然后把vector中的5个元素都删除了，此时removeThread的CPU时间片到了
 *
 * 而此时printThread再获取到CPU的执行权，此时执行输出中的get(4)方法就会出现越界的错误，
 * 因为此时vector中的元素已经被remove线程删除了
 */
public class VectorDemo1 {
    private static Vector<Integer> vector = new Vector();
    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i); //往vector中添加元素
            }
            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        Thread.yield();
                        //移除第i个数据
                        vector.remove(i);
                    }
                }
            });

            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        Thread.yield();
                        //获取第i个数据并打印
                        System.out.println(vector.get(i));
                    }
                }
            });
            removeThread.start();
            printThread.start();
            //避免同时产生过多线程
            while (Thread.activeCount() > 20) ;

        }
    }
}
