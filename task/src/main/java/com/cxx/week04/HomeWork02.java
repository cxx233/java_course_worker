package com.cxx.week04;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName GetResult
 * @Description 在 main 函数启动一个新线程，运行一个方法，拿到这
 * 个方法的返回值后，退出主线程
 * <p>
 * 处理方案：线程同步，数据共享，异步回调
 * @Date 2022/3/20 13:39
 */
public class HomeWork02 {
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

//        tenth();

//        eleventh();

//        twelfth();

//        thirteenth();

        fourteenth();
        System.out.println("主线程结束");
    }

    private static void fourteenth() {
        // 这里会不会跟第八个有相同的问题
        AtomicReference<String> result = new AtomicReference<>();
        Thread thread = Thread.currentThread();
        Thread t = new Thread(() -> {
            result.set("the answer14");
            // 然后由子线程进行解放
            LockSupport.unpark(thread);
        });
        t.start();
        // 由于主线程暂停处理
        LockSupport.park();
        System.out.println("result:" + result.get());
    }

    /**
     * 第十三种方案：使用blockingqueue
     */
    private static void thirteenth() {
        String result = null;
//        BlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(1);
        BlockingQueue<String> blockingQueue = new SynchronousQueue(true);

        // 使用阻塞队列
        Thread t = new Thread(() -> {
            try {
                blockingQueue.put("the answer13");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
        try {
            // 这里会阻塞，生产者和消费者使用案例
//            result = blockingQueue.take();
            result = blockingQueue.take();
            System.out.println("result:" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void twelfth() {
        // 这里也是不能保证时间片 主线程先行还是子线程先行的情况。
        // 如果是子现象先行的话，那就会导致主线程一直卡死的情况，这种方案不可取，但能用。不建议使用 =》 这个可以在子线程先睡，先让主线程获取
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        AtomicReference<String> result = new AtomicReference<>();
        Thread t = new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                result.set("the answer12");
            } finally {
                lock.unlock();
            }
        });
        t.start();
        lock.lock();
        try {
            condition.await();
            System.out.println("result:" + result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    /**
     *
     */
    @Deprecated
    private static void eleventh() {
        // 跟第十种方案的同样的问题
        /**
         * Connected to the target VM, address: '127.0.0.1:8880', transport: 'socket'
         * result:null
         * 主线程结束
         * Disconnected from the target VM, address: '127.0.0.1:8880', transport: 'socket'
         */
        Lock lock = new ReentrantLock();
        AtomicReference<String> result = new AtomicReference<>();
        Thread t = new Thread(() -> {
            lock.lock();
            try {
                result.set("the answer11");
            } finally {
                lock.unlock();
            }
        });
        t.start();
        lock.lock();
        try {
            System.out.println("result:" + result.get());
        } finally {
            lock.unlock();
        }
    }

    /**
     * 第十种方案：使用synchronized 配合锁
     * 注意：但不稳定。
     */
    private static void tenth() {
//        System.out.println("result:" + result.get());
        // 想使用notify 和wait 处理，但线程间争抢的时间片不确定，所以有可能主线程还没执行完。还有一个问题，就是要配合synchronized 处理。。
        // 那就想让里面睡上，然后外面先锁上
        AtomicReference<String> result = new AtomicReference<>();
        Object lock = new Object();
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                result.set("the answer10");
                lock.notifyAll();
            }
        });
        t.start();
        try {
            // 这里会出现一个问题：不确定是哪个线程先行，所以会导致一种情况，这里还没wait 的情况下，那么子线程也就notify 了。导致这里会卡住，并永远不动了。
            synchronized (lock) {
                // wait 之后，会释放锁
                lock.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("result:" + result.get());

    }

    /**
     * 第九种方案：使用线程等待，但这种不可靠。
     * 建议还是使用通知等待处理较好。
     */
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

    /**
     * 第八种方案：使用join，让主线程等待子线程完成完成后再处理
     */
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
     * 不行，因为屏障需要等待其他线程一起等待情况的情况。
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

    /**
     * 第六种方案：
     * 1. 使用原子引用，配合countDownLatch ，让主线程暂停，子线程处理完后进行唤醒
     */
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

    /**
     * 第五种方案：ForkJoinFuture 子类返回结果
     * submit 方法相当于普通线程池调用。但不能配合线程以及普通线程池处理
     */
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
        forkJoinPool.shutdown();
    }

    /**
     * 第四种方案：ForkJoinFuture 子类返回结果
     * 但需要配置ForkJoinPool 使用。但调用的是invoke 方法 =》 立即执行，具体底层实现是join 方案
     */
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

    /**
     * 第三种方案：使用completableFuture 处理，异步返回数据
     */
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

    /**
     * 第二种方案：使用futureTask 配合callable 处理 =》 callable 返回数据
     * futureTask.get() 也是阻塞的。
     * futureTask 是一个Runnable 接口，配合线程运行
     */
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
     * 第一种方案：使用线程池提交callable ，返回future实例
     * future.get 返回会阻塞main 线程
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
