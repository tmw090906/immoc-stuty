package stream;

import java.util.Arrays;
import java.util.stream.IntStream;

public class StreamDemo1 {

    public static void main(String[] args) {
        /**
         * 外部迭代:
         * 为什么不好?
         * 1. 首先是串行处理 , 数据量大的话 ,数据量不达标 Stream有 parallel()并行处理操作
         */
        int[] nums = {32,412,123,32,-2,213,1,2,42};

        int sum = 0;

        for (int i : nums) {
            sum += i;
        }
        System.out.println("结果为:" + sum);


        // 内部迭代
        int sum2 = IntStream.of(nums).parallel().sum();
        System.out.println("结果为:" + sum2);


        /**
         * map就是中间操作(返回Stream ,返回流的操作) , 返回值是流Stream
         * sum就是终止操作(产生一个结果(副作用)) , 返回值不是流Stream
         *
         * 中间操作只会在终止操作调用后, 才会执行
         */
        int sum3 = IntStream.of(nums).map(StreamDemo1::doubleNum).sum();
        System.out.println(sum3);

        System.out.println("惰性求值就是终止操作没有调用的情况下, 中间操作不会执行");
        IntStream stream = IntStream.of(nums).map(StreamDemo1::doubleNum);
        System.out.println("开始调用终止操作");
        stream.sum();

        Arrays.stream(nums);
    }

    public static int doubleNum(int i) {
        System.out.println("执行了X2");
        return i * 2;
    }
}
