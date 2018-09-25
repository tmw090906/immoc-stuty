package stream;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

/**
 * 中间操作
 * 1.无状态操作: 当前操作跟其他元素的前后没有依赖关系
 * 2.有状态操作: 结果需要依赖于其他的元素, 例如sorted 依赖于所有元素都计算完毕,才会得出结果
 * 3.返回的都是Stream以便链式调用
 */
public class StreamDemo3 {

    public static void main(String[] args) {
        String str = "My name is tianmw";

        Stream.of(str.split(" ")).map(s -> s.length()).forEach(
                s -> System.out.println(s)
        );

        Stream.of(str.split(" ")).mapToDouble(s -> s.length()).forEach(
                s -> System.out.println(s)
        );


        Stream.of(str.split(" "))
                .filter(s -> s.length() > 3)
                .mapToDouble(s -> s.length()).forEach(
                s -> System.out.println(s)
        );

        // flatMap A -> B属性(是个集合), 最终得到的所有的A元素里面的所有B属性集合
        // IntStream/LongStream 并不是Stream的子类, 所以要进行装箱boxed
        Stream.of(str.split(" "))
                .flatMap(s -> s.chars().boxed())
                .forEach(s -> System.out.println((char)s.intValue()));
        StreamFlatMap[] streamFlatMaps = new StreamFlatMap[10];
        for (int i = 0 ; i < streamFlatMaps.length ; i ++) {
            streamFlatMaps[i] = new StreamFlatMap();
        }
        Stream.of(streamFlatMaps)
                .flatMap(streamFlatMap -> Arrays.stream(streamFlatMap.test)
                        .boxed()
                ).forEach(s -> System.out.println(s));


        // peek用于debug, 是个中间操作, 和foreach是终止操作
        Stream.of(str.split(" "))
                .peek(System.out::println)
                .forEach(System.out::println);


        // limit使用, 主要永远无限流
        new Random()
                .ints()
                .filter(i -> i < 100 && i > 0)
                .limit(100)
                .forEach(System.out::println);
    }


}

class StreamFlatMap {

    public int[] test;

    StreamFlatMap() {
        Random random = new Random();
        test = new int[2];
        test[0] = random.nextInt();
        test[1] = random.nextInt();
    }

}
