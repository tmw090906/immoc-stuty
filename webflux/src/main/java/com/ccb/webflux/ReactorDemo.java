package com.ccb.webflux;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;

public class ReactorDemo {

    public static void main(String[] args) {

        // reactor = jdk8 stream + jdk 9 reactive strem
        // Mono 0-1 个元素
        // Flux 0-N 个元素
        String[] strs = {"1", "2", "3", "12"};


        // 2. 定义订阅者
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            private Subscription subscription;

            private int i = 0;

            @Override
            public void onSubscribe(Subscription subscription) {
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

        Flux.fromArray(strs)
                .map(s -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return Integer.parseInt(s);
                })
                // 最终操作 订阅
                // 这里就是jdk9的reactive stream
                .subscribe(subscriber);

    }


}
