package com.ccb.tmw.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Description: 初始化器，channel注册后，会执行里面相应的初始化方法
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {


    protected void initChannel(SocketChannel channel) throws Exception {
        // 通过channel获得对应管道
        ChannelPipeline pipeline = channel.pipeline();

        // 通过管道，添加handler
        // HttpServerCodec是由netty自己提供的助手类，可以理解为拦截器
        // 当请求到服务端，我们需要做解码，相应到客户端做解码
        pipeline.addLast("httpServerCodec", new HttpServerCodec());

        pipeline.addLast("responseHelloHandler", new ResponseHelloHandler());

        //pipeline.addLast("customHandler", new CustomHandler());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx + "in initializer");
        super.channelActive(ctx);
    }
}
