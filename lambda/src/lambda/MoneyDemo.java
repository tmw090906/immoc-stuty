package lambda;

import java.text.DecimalFormat;
import java.util.function.Function;

public class MoneyDemo {

    public static void main(String[] args) {

        MyMoney myMoney = new MyMoney(1032424);

        myMoney.printMoney(
                money ->  new DecimalFormat("#,###").format(money)
                );

        /**
         * 使用Function接口时, 还可以链式调用
         * 例如 execute.andThen():
         *  先执行execute中的表达式
         *  在用execute的返回值去执行andThen中的lambda表达式
         * 例如 execute.compose():
         *  先执行compose中的lambda表达式
         *  再用compose种的lambda表达式去执行execute的lambda表达式
         */
        Function<Integer, String> execute = money -> new DecimalFormat("#,##").format(money);
        myMoney.printMoney4Function(execute.andThen(money -> money + "Test"));

    }



}


interface IMoneyFormat {
    String format(int i);
}

class MyMoney {
    private final int money;

    MyMoney(int money) {
        this.money = money;
    }

    /**
     * 调用的接口满足 @FunctionalInterface 时, 可以如下
     * @param moneyFormat
     */
    void printMoney(IMoneyFormat moneyFormat) {
        System.out.println("我的存款:" + moneyFormat.format(this.money));
    }

    /**
     * 使用Function接口来代替, 其中Function接口的消费方法为  apply()
     * <T,R> T 为参数  R为返回类型
     * @param moneyFormat
     */
    void printMoney4Function(Function<Integer, String> moneyFormat) {
        System.out.println("我的存款:" + moneyFormat.apply(this.money));
    }
}
