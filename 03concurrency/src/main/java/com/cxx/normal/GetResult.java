package com.cxx.normal;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ClassName GetResult
 * @Description 在 main 函数启动一个新线程，运行一个方法，拿到这
个方法的返回值后，退出主线程
 * @Date 2022/3/20 13:39
 */
public class GetResult {
    public static void main(String[] args) {
        // 第一种
//        first();

//        second();

//        third();

//        fourth();

//        fifth();

        
//        sixth();

//        seventh();

//        eighth();

//        nighth();
        
        tenth();
        System.out.println("主线程结束");
    }

    private static void tenth() {
  
        System.out.println("result:" + result.get());
    }

    private static void nighth() {
        AtomicReference<String> result = new AtomicReference<>();
        Thread t = new Thread(() -> {
            result.set("the answer9");

        });
        t.start();
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("result:" + result.get());
    }

    private static void eighth() {
        AtomicReference<String> result = new AtomicReference<>();
        Thread t = new Thread(() -> {
            result.set("the answer8");
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("result:" + result.get());
    }

    /**
     * 不行，因为屏障无法展厅，
     */
    @Deprecated
    private static void seventh() {
        AtomicReference<String> result = new AtomicReference<>();
        CyclicBarrier cyc = new CyclicBarrier(1);
        new Thread(() -> {

            result.set("the answer7");
            try {
                cyc.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("result:" + result.get());
    }

    private static void sixth() {
        AtomicReference<String> result = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            result.set("the answer6");
            countDownLatch.countDown();
        }).start();
        try {
            countDownLatch.await();
            System.out.println("result:" + result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void fifth() {
        String result = null;
        RecursiveTask<String> task = new RecursiveTask<String>() {
            @Override
            protected String compute() {
                try {
                    Thread.sleep(4_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "the answer5";
            }
        };
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future<String> future = forkJoinPool.submit(task);
        try {
            // get 方法会阻塞
            result = future.get();
            System.out.println("result:" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void fourth() {
        String result = null;
        RecursiveTask<String> task = new RecursiveTask<String>() {
            @Override
            protected String compute() {
                try {
                    Thread.sleep(4_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "the answer4";
            }
        };
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 里面join 了，阻塞了
        result = forkJoinPool.invoke(task);
        System.out.println("result:" + result);
        forkJoinPool.shutdown();
    }

    private static void third() {
        String result = null;
        // 这里不用使用线程
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(
                () -> "the answer3");
        try {
            result = completableFuture.get();
            System.out.println("result:" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void second() {
        String result = null;
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "the answer2";
            }
        };
        FutureTask<String> ft1 = new FutureTask<>(callable);
        Thread t = new Thread(ft1);
        try {
            t.start();
            result = ft1.get();
            System.out.println("result:" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * 第一种方案：使用线程池处理
     */
    private static void first() {
        String result = null;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> stringFuture = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "the answer";
            }
        });
        executor.shutdown();
        try {
            result = stringFuture.get();
            System.out.println("result:" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
