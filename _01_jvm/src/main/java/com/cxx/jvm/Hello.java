package com.cxx.jvm;

/**
 * @author xujiaxi <br>
 * @version 1.0 <br>
 * @description: Hello <br>
 * @date 2022/2/28 11:51 <br>
 */
public class Hello {
    public static void main(String[] args) {
        int a = 10;
        int b = 20;
        int c = a * b;

        switch (c) {
            case 200:
                System.out.println("switch c");
            default:
                System.out.println("default");
        }

        for (int i = 10; i <= c; i += 10) {
            float f = i;
            if (f - 100 > 0) {
                System.out.println("float:" + f);
            }
            if (f - 10 <= 0f) {
                double d = a + b;
                System.out.println("d = a + b => " + d);
            } else if (f - 20 <= 0f) {
                double d = a - b;
                System.out.println("double d = a - b =>" + d);
            } else if ( f - 30 <= 0f) {
                double d = (double)  a / b;
                System.out.println("double d = (double)  a / b ==> " + d);
            }
        }

    }
}
