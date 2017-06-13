package com.demo.quickindexbar;

/**
 * Created by lxj on 2016/8/29.
 */
public class Friend implements Comparable<Friend> {
    public String name;
    public String pinyin;

    public Friend(String name) {
        this.name = name;
        this.pinyin = PinYinUtil.getPinyin(name);
    }

    @Override
    public int compareTo(Friend another) {
        return pinyin.compareTo(another.pinyin);
    }
}
