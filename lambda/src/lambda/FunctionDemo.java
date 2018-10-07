package lambda;

import java.util.Random;
import java.util.function.*;

public class FunctionDemo {



    public static void main(String[] args) {
        // 断言接口函数 : 有参数,返回值为Boolean
        Predicate<Integer> predicate = i -> i > 0;
        System.out.println(predicate.test(20));

        // 消费者函数, 对参数进行操作(计算),没有返回值 : 有参数
        Consumer<String> consumer = s -> System.out.println(s);
        consumer.accept("Test");

        // 输入T , 返回R Function<T, R> : 有参数有返回值
        Function<Integer, String> function = s -> s + "TEST";
        System.out.println(function.apply(1010));

        // 提供一个数据 ,参数为空 , 返回值为自定义泛型
        Supplier<String> stringSupplier = () -> {
            Random random = new Random();
            return random.nextInt() + "t";
        };


        // 一元函数(输出输入类型相同)
        UnaryOperator<String> stringUnaryOperator
                = s -> s + s + "1010";
        System.out.println(stringUnaryOperator.apply("YM"));

        // 2个输入函数 ,<T, U, R> 参数,参数,返回值
        BiFunction<String, Integer, String> biFunction =
                (s, i) -> {
                    StringBuilder sb = new StringBuilder();
                    for (int index = 0; index < i ; index ++) {
                        sb.append(s);
                    }
                    return sb.toString();
                };

        System.out.println(biFunction.apply("tmwYM", 3));


        // 二元函数,  输入两个函数 都是 <T>  返回值也是<T>
        BinaryOperator<Integer> integerIntegerBinaryOperator
            = (i1, i2) -> i1 * i1;
        System.out.println(integerIntegerBinaryOperator.apply(3, 4));


    }

}
