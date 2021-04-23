package com.lww;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/*给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
        示例 1:

        输入: s = "abcabcbb"
        输出: 3
        解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
        示例 2:

        输入: s = "bbbbb"
        输出: 1
        解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
        示例 3:

        输入: s = "pwwkew"
        输出: 3
        解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
             请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
        示例 4:

        输入: s = ""
        输出: 0

        链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters*/

public class LTest {


    @Test
    public void test1() {
        String str = "acdfgrgegege";
        Map<String, String> map = new HashMap<>();
        map.put("substr", str.substring(0, 1));     // (   x  ,  y )
        System.out.println(map.get("substr"));
        //最长的可能是就是串的长度   str.length

        for (int i= 1; i <str.length(); i++) {    //当前字符串为几位
            for (int k=0;k<str.length();k++){
                String substr = str.substring(k, k+i);
                int start=k+i;
                int end=str.length()-1;
                String otherStr = str.substring(start,end);     //前闭后开
                System.out.println("substr:" + substr + "     otherStr:" + otherStr);
            }
        }
    }
}
