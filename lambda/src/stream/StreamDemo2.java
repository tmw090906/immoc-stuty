package stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream流的创建
 */
public class StreamDemo2 {

    public static void main(String[] args) {

        List<String> stringList = new ArrayList<>();

        /**
         * 从集合创建
         */
        stringList.stream();
        /**
         * 并行流
         */
        stringList.parallelStream();


        /**
         * 从集合创建
         */
        Stream<Double> arrayStream = Arrays.stream(new Double[]{3d, 4d, 2d, 3d});
        arrayStream.map(x -> x + 100d).forEach(
                x -> System.out.println(x)
        );


        // 创建数字流
        DoubleStream.of(3d, 4d, 12d);
        // 带边界的流 rangeClosed 生成[1,10]的流
        // range的话 是(1,10)
        IntStream intStream = IntStream.rangeClosed(1, 10);
        intStream.forEach(x -> System.out.println(x));

        // 使用random创建一个无限流
        new Random().ints().limit(10);
        Random random = new Random();
        // 这两个操作其实类似 , 都是用random生成一个流, 同时限制了流的大小
        // 自己产生流
        System.out.println("start");
        Stream<Integer> stream = Stream.generate(() -> random.nextInt(100)).limit(20);
        //stream.forEach(x -> System.out.println(x));
        System.out.println(stream.parallel().max((x, y) -> x < y ? x : y).get());
    }

}
