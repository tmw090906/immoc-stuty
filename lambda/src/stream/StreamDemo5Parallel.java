package stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class StreamDemo5Parallel {


    public static void main(String[] args) throws InterruptedException {

        // 调用parallel产生一个并行流
        /**
        IntStream.rangeClosed(1, 200)
                .parallel()
                .peek(StreamDemo5Parallel::debug)
                .count();
         */

        // 实现这样一个效果: step1: 并行操作 step2: 串行操作
        // 多次调用 parallel / sequential , 以最后一次调用为准
        /**
        IntStream.rangeClosed(1, 200)
                // 调用parallel产生并行流
                .parallel()
                .peek(StreamDemo5Parallel::debug)
                // 调用sequetial产生串行流
                .sequential()
                .peek(StreamDemo5Parallel::debug2)
                .count();
         */


        // 并行流使用的线程池: ForkJoinPool.commonPool
        // 默认的线程数是当前机器的CPU个数
        // 使用以下参数修改默认并行流线程数
        /**
        System.setProperty(
                "java.util.concurrent.ForkJoinPool.common.parallelism",
                "10");
        IntStream.rangeClosed(1, 200)
                .parallel()
                .peek(StreamDemo5Parallel::debug)
                .count();
         */


        // 使用自己的线程池, 不适用默认的线程池, 防止资源阻塞
        ForkJoinPool pool = new ForkJoinPool(12);
        pool.submit(
                () -> {
                    IntStream.rangeClosed(1, 200)
                            .parallel()
                            .peek(StreamDemo5Parallel::debug)
                            .count();
                });
        pool.shutdown();

        // 如果不加等待,  则会因为主线程的结束导致自己的线程池未执行
        TimeUnit.SECONDS.sleep(30);


    }

    public static void debug(int i) {
        System.out.println(Thread.currentThread().getName() + "debug" + i);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void debug2(int i) {
        System.err.println("debug2" + i);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
