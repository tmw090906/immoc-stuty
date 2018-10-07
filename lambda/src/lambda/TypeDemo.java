package lambda;

/**
 * 类型推断
 */
public class TypeDemo {

    public static void main(String[] args) {

        // 变量类型定义
        IMath lambda = (x, y) -> x + y;

        // 数组里
        IMath[] lambdas = {(x, y) -> x + y};

        // 强转
        Object lambdaObject = (IMath)(x, y) -> x + y;

        // 通过返回类型
        IMath craeteLambda = createLambda();

        // 通过参数类型定义
        // 当有二义性时, 需要使用强转对应的接口解决
        new TypeDemo().test((IMath2) (x, y) -> x + y);
    }


    public static IMath createLambda() {
        return (x, y) -> x + y;
    }

    public void test(IMath math) {
        math.add(3, 4);
    }

    public void test(IMath2 math) {
        math.add(3, 4);
    }

}

@FunctionalInterface
interface IMath {

    int add(int x, int y);

}

@FunctionalInterface
interface IMath2 {

    int add(int x, int y);

}



