package com.cxx.homework.homework1;


import org.springframework.aop.AopInvocationException;

import java.lang.reflect.InvocationHandler;

/**
 * 使用代理实现aop
 * https://blog.csdn.net/hq86937375/article/details/98481629
 */
public class Homework1_ProxyForAop {

    public static void main(String[] args) {
        Test t1 = new Test() {
            @Override
            public void methodTransaction() {
                System.out.println("methodTransaction");
            }

            @Override
            public void methodLog() {
                System.out.println("methodLog");
            }

            @Override
            public void methodTransactionWithLog() {
                System.out.println("methodTransactionWithLog");
            }

            @Override
            public void methodNothing() {
                System.out.println("methodNothing");
            }
        };

        InvocationHandler testHandler =
    }

}
