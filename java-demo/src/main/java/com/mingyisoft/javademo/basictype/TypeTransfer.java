package com.mingyisoft.javademo.basictype;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类型转换
 */
public class TypeTransfer {
    public static void main(String[] args) {
        // String转Long
        long b = Long.parseLong("12345");

        // Long转Date
        Date d = new Date(b);

        // Date转Long
        Long l = d.getTime();

        // Integer转int
        int aa = new Integer(5).intValue();

        // int转Integer
        Integer xxx = new Integer(1);

        // String转Integer
        Integer.valueOf("");

        // Date转String
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        dateformat.format(new Date());

        // String转Date
        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dd = sdfStart.parse("2005-06-06");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // String转Float
        float a = Float.valueOf("1.5");

        Object obj = new Object();
        // int转Object
        int value = Integer.parseInt(obj.toString());

        // Object转int
        int bbb = (Integer)obj ;

        // Object转Date
        Date tempDate = (Date)obj;
    }
}
