package com.cxx.homework.homework1.aop.impl;

import com.cxx.homework.homework1.aop.Aspect;

public class LogAspect implements Aspect {
    @Override
    public void before() {
        System.out.println("调用前");
    }

    @Override
    public void after() {
        System.out.println("调用后");
    }
}
