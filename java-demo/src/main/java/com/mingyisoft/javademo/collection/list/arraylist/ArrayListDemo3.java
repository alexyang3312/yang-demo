package com.mingyisoft.javademo.collection.list.arraylist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * ArrayList允许插入重复的null值
 */
public class ArrayListDemo3 {
    Vector v = null;
    public static void main(String[] args) {
        List myList = new ArrayList();

        myList.add(null);
        myList.add(null);
        myList.add(null);

        System.out.println(Arrays.toString(myList.toArray()));
        System.out.println(myList.size());
    }
}
