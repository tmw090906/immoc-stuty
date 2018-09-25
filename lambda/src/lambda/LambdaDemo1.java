package lambda;

/**
 * jdk 1.8 以后, 接口的新特性
 *
 * 1. lambda表达式, 实际上返回的是一个 单抽象方法 的接口
 * 2. @FcuntionalInterface 注解, 可以在编译期检测函数式接口是否编写正确
 * 3. 接口可以实现默认的方法
 * 4. 接口可以实现静态方法
 */
public class LambdaDemo1 {

    public static void main(String[] args) {
        // 1
        Interface1 i1 = (x) -> x * 2;
        // 2 常见
        Interface1 i2 = x -> x * 3;
        // 3
        Interface1 i3 = (int x) -> x * 3;
        // 4
        Interface1 i4 = (int x) -> {
            System.out.println("----Test----");
            return x * 3;
        };

        System.out.println(i4.doubleNum(34));
        System.out.println(i4.add(0112, 0321));
    }


}

/**
 * 编译器校验 :
 * 函数接口只能有一个未实现的方法 :编译时校验是否附合该规则
 */
@FunctionalInterface
interface Interface1 {

    int doubleNum(int i);

    /**
     * jdk 8 新增特性: 接口中可以实现默认实现方法, 也可以重写
     * @param x
     * @param y
     * @return
     */
    default int add(int x, int y) {
        return x + y;
    }
}

@FunctionalInterface
interface Interface2 {

    int doubleNum(int i);

    /**
     * jdk 8 新增特性: 接口中可以实现默认实现方法, 也可以重写
     * @param x
     * @param y
     * @return
     */
    default int add(int x, int y) {
        return x * y;
    }
}


/**
 * 因为接口可以多重继承的原因
 * 两个父接口有一个同名的 默认实现方法 时
 * 子接口需要重写该默认方法
 */
@FunctionalInterface
interface Interface3 extends Interface1, Interface2 {

    @Override
    default int add(int x, int y) {
        return 0;
    }

    /**
     * jdk 8 新增特性: 接口中可以实现默认实现方法, 也可以重写
     * @param x
     * @param y
     * @return
     */

}

