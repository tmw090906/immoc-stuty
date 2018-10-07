package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 变量引用
 * @author tmw090906
 */
public class VarDemo {

    public static void main(String[] args) {
        // jdk 1.8 以前,匿名内部类要调用外部的参数, 这个参数必须是final的
        // jdk 1.8 以后,可以不定义final了(语法糖)
        // 但实际编译器会自动编译为final的, 所以也是不能在改变引用
        List<String> strs = new ArrayList();
        Consumer<String> consumer = s -> System.out.println(strs + s);
        consumer.accept("12");


        // 为什么有这个机制呢?
        // java传参是传值, 不是传引用
        // 这个机制是为了保证 外部和引用和内部的引用是同一个, 不产生二义性


    }


}
