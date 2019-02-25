package com.tmw.netty;

import com.tmw.netty.client.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    private static final int MAX_RETRY = 5;

    private static final int PORT = 8000;

    public static void main(String[] args) {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(
                    new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });

        connect(bootstrap, "127.0.0.1", PORT, MAX_RETRY);

    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap
                .connect(host, port)
                .addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if (future.isSuccess()) {
                            System.out.println("连接成功!");
                        } else if (retry == 0) {
                            System.err.println("重试次数已用完，放弃连接！");
                        } else {
                            // 第几次重连
                            int order = (MAX_RETRY - retry) + 1;
                            // 本次重连的间隔
                            int delay = 1 << order;
                            System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                            bootstrap.config().group().schedule(
                                    () -> connect(bootstrap, host, port, retry - 1),
                                    delay,
                                    TimeUnit.SECONDS);
                        }
                    }
                });

    }
}
