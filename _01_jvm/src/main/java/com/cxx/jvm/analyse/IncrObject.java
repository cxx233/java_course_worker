package com.cxx.jvm.analyse;

/**
 * 相加的object
 */
public class IncrObject {
    private volatile int counter;

    private int count;

    public int getCount(int x) {
        this.count = this.counter + x;
        return count;
    }
}
