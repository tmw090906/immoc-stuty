package jdk9;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * jdk 9 : Reactive Stream
 */
public class FlowDemo {

    public static void main(String[] args) throws InterruptedException {

        // 1. 定义发布者, 发布的数据类型是Integer
        // 直接使用jdk自带的SubmissionPublisher, 他实现了Publisher接口
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();


        // 2. 定义订阅者
        Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<>() {
            private Flow.Subscription subscription;

            private int i = 0;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                // 保存订阅关系, 需要用它来给发布者响应
                this.subscription = subscription;

                // 请求一个数据
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                // 接收到一个数据, 处理
                System.out.println("accept:" + item);

                // 处理完调用request再请求一个数据
                this.subscription.request(1);

                i ++;
                // 如果已经达到目标, 调用cancel告诉发布者不再接收数据
                // 自身调用cancle不会, 不会接收到发布者的的complete()调用
                if (i > 2) {
                    this.subscription.cancel();
                }
            }

            /**
             * 发生异常
             * @param throwable
             */
            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();

                this.subscription.cancel();
            }

            /**
             * 发布者关闭的时候调用
             */
            @Override
            public void onComplete() {
                System.out.println("发布者停止发布");
            }
        };

        // 3. 发布者和订阅者 建立订阅关系
        publisher.subscribe(subscriber);

        // 4. 产生数据, 并发布
        // 这里忽略数据产生过程
        int data = 111;
        publisher.submit(data);
        publisher.submit(data);
        publisher.submit(data);
        publisher.submit(data);


        // 5. 结束后, 关闭发布者
        publisher.close();

        // 主线程延迟停止, 否则数据没有消费就被退出
        Thread.currentThread().join(1000);


    }


}
