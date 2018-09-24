package lambda;

import java.util.stream.IntStream;

public class MinDemo {


    public static void main(String[] args) {
        int[] nums = {32,412,123,32,-2,213,1,2,42};

        /**
         * 命令式编程
         */
        int min = Integer.MAX_VALUE;

        for (int i : nums) {
            if (i < min) {
                min = i;
            }
        }

        System.out.println(min);


        /**
         * jdk1.8之后
         * 函数式编程
         */
        int minNum = IntStream.of(nums).parallel().max().getAsInt();

        System.out.println(minNum);

    }
}
