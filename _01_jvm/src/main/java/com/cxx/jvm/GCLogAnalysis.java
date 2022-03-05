package com.cxx.jvm;


import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * 演示GC 日志生成与解读
 */
public class GCLogAnalysis {
    private static Random random = new Random();
    public static void main(String[] args) {
        // 当前毫秒时间戳
        long startMills = System.currentTimeMillis();
        // 持续运行毫秒数；可根据需要进行修改
        long timeoutMillis = TimeUnit.SECONDS.toMillis(1);
        // 结束时间错
        long endMills = startMills + timeoutMillis;
        // 原子操作
        LongAdder counter = new LongAdder();
        System.out.println("正在执行...");
        // 缓存一部分对象；进入老年代
        int cacheSize = 2000;
        Object[] cachedGrabage = new Object[cacheSize];
        // 是否小于1s 后的时间 =》 在此时间范围内，持续循环
        while (System.currentTimeMillis() < endMills) {
            // 10w 生成回收对象
            Object garbage = generateGarbage(100 * 1024);
            counter.increment();
            int randomIndex = random.nextInt(2 * cacheSize);
            // 有一半概率塞到外面的数组引用 -》 进入老年代
            if (randomIndex < cacheSize) {
                cachedGrabage[randomIndex] = garbage;
            }
        }
        System.out.println("执行结束1共生成对象次数：" + counter.longValue());

    }

    /**
     * 生成对象
     * @param max
     * @return
     */
    private static Object generateGarbage(int max) {
        int randomsize = random.nextInt(max);
        int type = randomsize % 4;
        Object result = null;
        switch (type)  {
            case 0:
                result = new int[randomsize];
                break;
            case 1:
                result  = new byte[randomsize];
                break;
            case 2:
                result = new double[randomsize];
                break;
            default:
                StringBuilder builder = new StringBuilder();
                String randomString = "randomString-Anything";
                while (builder.length() < randomsize) {
                    builder.append(randomString);
                    builder.append(max);
                    builder.append(randomsize);
                }
                result = builder.toString();
        }
        return result;
    }
}
