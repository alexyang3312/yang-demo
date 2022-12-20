package com.mingyisoft.javademo.leetcode;

/**
 * 1668. 最大重复子字符串
 * 给你一个字符串 sequence ，
 * 如果字符串 word 连续重复 k 次形成的字符串是 sequence 的一个子字符串，
 * 那么单词 word 的 重复值为 k 。
 *
 * 单词 word 的 最大重复值 是单词 word 在 sequence 中最大的重复值。
 * 如果 word 不是 sequence 的子串，那么重复值 k 为 0 。
 * 给你一个字符串 sequence 和 word ，请你返回 最大重复值 k 。
 * https://leetcode.cn/problems/maximum-repeating-substring/description/
 */
public class MaxRepeating {
    public static void main(String[] args) {
        System.out.println(maxRepeating("ababc","ab"));
        System.out.println(maxRepeating("ababc","ba"));
        System.out.println(maxRepeating("aaabaaaabaaabaaaabaaaabaaaabaaaaba","aaaba"));

    }

    public static int maxRepeating(String sequence, String word) {
        int i = 0;

        while(true){
            int tempInt = sequence.lastIndexOf(word);
            if(tempInt!=-1){
                i++;
                sequence = sequence.substring(0, tempInt);
            } else {
                break;
            }
        }

        return i;
    }
}
