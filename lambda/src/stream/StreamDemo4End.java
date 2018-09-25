package stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 终止操作
 * 1. 非短路操作: 与短路操作相反
 * 2. 短路操作: 不需要等待所有的结果都计算完, 就可以结束当前流的操作 例如 findFirst ,findAny
 */
public class StreamDemo4End {


    public static void main(String[] args) {
        String str = "my wife is yangm";


        // 终止 : 非短路
        // 使用并行流
        str.chars().parallel().forEach(
                c -> System.out.print((char)c)
        );
        System.out.println();
        // 使用foreachOrded 保证并行计算时,按照顺序计算
        str.chars().forEachOrdered(
                c -> System.out.print((char)c)
        );
        System.out.println();
        List<String> list = Stream.of(str.split("" ))
                .collect(Collectors.toList());
        System.out.println(list);


        // reduce -> 把我们的流合并为一个数据
        // Otional 中的orElse 类似 if (Obeject != null) object else other
        Optional<String> letter = Stream.of(str.split(" "))
                .reduce((s1, s2) -> s1 + "|" + s2);
        System.out.println(letter.orElse(""));

        // reduce -> 把我们的流合并为一个数据
        // 该方法为设定第一个操作的参数
        // 若设流中数据为  a, b, c, d
        // reduce计算结果为: ((("" + "|" + a) + "|" + b)) + "|" + c) ...
        Integer countLength = Stream.of(str.split(" "))
                .map(s -> s.length())
                .reduce(0, (l1, l2) -> l1 + l2);
        System.out.println(countLength);


        // min / max / count
        Optional<Integer> integer = Stream.of(str.split(" "))
                .map(s -> s.length())
                .min(Comparator.comparingInt(Integer::intValue));
        System.out.println(integer.get());



        // 终止 : 短路操作
        OptionalInt optionalInt = new Random()
                .ints()
                .filter(i -> i == 100) // 中间操作
                .findFirst();
        System.out.println(optionalInt.getAsInt());

    }

}
