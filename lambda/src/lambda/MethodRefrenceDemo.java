package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class MethodRefrenceDemo {


    public static void main(String[] args) {
        // 方法引用
        Consumer<String> consumer = System.out::println;
        consumer.accept("test");

        Dog dog = new Dog();
        //dog.eat(1);

        // 1. 静态方法的方法引用, 使用类名加方法名
        Consumer<Dog> dogConsumer = Dog::bark;
        dogConsumer.accept(dog);


        // 2. 非静态方法, 使用对象实例来引用
        // Function<Integer, Integer> function
        // UnaryOperator<Integer> integerUnaryOperator
        IntUnaryOperator intUnaryOperator = dog::eat;
        System.out.println("还剩下"
                + intUnaryOperator.applyAsInt(8)
                + "斤");

        // special. 使用类名来调用非静态方法, 需要多加一个参数
        // 该参数为要调用方法的类对象
        BiFunction<Dog, Integer, Integer> biFunction =
                Dog::eat;
        System.out.println("还剩下"
                + biFunction.apply(dog,2)
                + "斤");

        // 3 .构造函数的方法引用
        Supplier<Dog> dogSupplier = Dog::new;
        System.out.println("还剩下"
                + dogSupplier.get().eat(2)
                + "斤");

        // 4. 带参数的构造方法引用
        Function<String, Dog> dogFunction =
                Dog::new;
        System.out.println(dogFunction.apply("小白"));


        System.out.println("--------------------");
        List<String> list = new ArrayList();
        new MethodRefrenceDemo().test(list);
        System.err.println(list);

    }

    private void test(List<String> list) {
        list = null;
    }

}


class Dog {

    private String name = "汪汪汪";

    private int food = 10;

    public static void bark(Dog dog) {

        System.out.println(dog + "叫了");

    }

    public Dog() {

    }

    public Dog(String name) {
        this.name = name;
    }

    /**
     * 吃狗粮
     * @param num
     * @return 还剩下多少斤
     *
     * JDK 默认会把当前实例传入到非静态方法中,参数名为this, 位置为第一个
     * 这里和python很像
     */
    public int eat(int num) {
        System.out.println("吃了" + num + "斤");
        this.food -= num;
        return this.food;
    }



    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                '}';
    }
}