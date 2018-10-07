package lambda;

import java.util.function.Function;

/**
 * 级联表达式和柯里化
 * 柯里化: 把多个参数的函数转化为只有一个参数的函数
 */
public class CurryDemo {

    public static void main(String[] args) {
        // 实现了 x + y 的级联表达式
        Function<Integer, Function<Integer, Integer>> function
                = x -> y -> x + y;
        System.out.println(function.apply(3).apply(3));



        Function<Integer,
                Function<Integer,
                        Function<Integer, Integer>>>
        functionFunction = x -> y -> z -> x + y + z;
        System.out.println(functionFunction.apply(3).apply(4).apply(-7));

        int[] nums = {3, 2, 1};

    }

}
