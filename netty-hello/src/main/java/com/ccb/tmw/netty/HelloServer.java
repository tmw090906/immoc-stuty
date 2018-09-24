package com.ccb.tmw.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description: 实现客户端发送一个请求，服务器会返回 hello netty
 */
public class HelloServer {

    public static void main(String[] args) throws Exception {

        // 定义一对线程组
        // 主线程组, 只用于接收客户端的连接， 不做任务处理
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        // 从线程组, 主线程组会将任务丢给这个线程组，让这个线程组做任务
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {

            // netty 服务器的创建，ServerBootstrap是一个启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentGroup, childGroup)          // 设置主从线程
                    .channel(NioServerSocketChannel.class) // 设置nio双向通道
                    .childHandler(new HelloServerInitializer());                   // 子处理器，用于处理childGroup


            // 启动server，设置8088为启动监听端口号，同时启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();

            // 监听关闭的Channel , 设置为同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
               parentGroup.shutdownGracefully();
               childGroup.shutdownGracefully();
        }

    }

}
