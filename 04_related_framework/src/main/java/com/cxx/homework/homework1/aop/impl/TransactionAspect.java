package com.cxx.homework.homework1.aop.impl;

import com.cxx.homework.homework1.aop.Aspect;

public class TransactionAspect implements Aspect {
    @Override
    public void before() {
        System.out.println("事务开始");
    }

    @Override
    public void after() {
        System.out.println("事务结束");
    }
}
