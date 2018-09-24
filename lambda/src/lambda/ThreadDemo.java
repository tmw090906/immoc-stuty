package lambda;

public class ThreadDemo {


    public static void main(String[] args) {

        /**
         * jdk1.8 之前
         */
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(101);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Test 1");
            }
        };
        new Thread(task1).start();



        /**
         * jdk1.8之后
         * lambda表达式: 实际上就是实现了一个只有一个抽象方法接口
         */
        Runnable task2 = () -> System.out.println("Test 2");
        new Thread(task2).start();

    }

}
