package com.mingyisoft.javademo.thread.concurrent;

import java.util.concurrent.Exchanger;

public class ExchangerDemo1 {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String book = "《Java核心技术卷一》";
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("甲带着" + book + "到达交易地点！");
                try {
                    System.out.println("甲换出了：" + book + ",换回了：" + exchanger.exchange(book));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String money = "200元";
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("乙带着" + money + "到达交易地点！");
                try {
                    System.out.println("乙换出了：" + money + ",换回了：" + exchanger.exchange(money));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
