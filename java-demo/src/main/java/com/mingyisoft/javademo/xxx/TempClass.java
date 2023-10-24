package com.mingyisoft.javademo.xxx;

import java.util.*;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class TempClass {
    public static void main(String[] args) {
//        int[] nums = new int[]{1,3,3,3,4,5,6,7,8,8,8,8,8,9,10,10,11,11,12,23,33,33,10,2,2,2,2,2,2,1,1,11};
//        int[] nums = new int[]{1,3,3,2};
//        findMax(nums);

        PriorityQueue a;

    }

    public static void findMax(int[] nums) {
        int numCount = 1;
        int tmpCount = 1;
        int maxNum = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                tmpCount++;
            } else if (nums[i] < nums[i - 1]) {
                if (tmpCount > numCount) {
                    numCount++;
                    maxNum = nums[i - 1];
                }
                tmpCount = 1;
            }
        }
        System.out.println("最大的数字是：" + maxNum + "，出现次数为：" + numCount);
    }

}