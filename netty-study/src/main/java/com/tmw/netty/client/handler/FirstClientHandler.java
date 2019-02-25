package com.tmw.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext context) {

        System.out.println(new Date() + ": 客户端写出数据");

        // 获取数据
        ByteBuf byteBuf = getByteBuf(context);

        // 写数据
        context.channel().writeAndFlush(byteBuf);

    }


    private ByteBuf getByteBuf(ChannelHandlerContext context) {

        // 1. 获取二进制抽象 ByteBuf
        ByteBuf buffer = context.alloc().buffer();

        // 2. 准备数据, 指定字符集为UTF-8
        byte[] message = "Hello, tianmaowei.zh".getBytes(Charset.forName("UTF-8"));

        // 3. 填充数据到ByteBuf
        buffer.writeBytes(message);

        return buffer;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ByteBuf byteBuf = (ByteBuf) msg;

        // 读数据时, 也需要指定为UTF-8编码
        System.out.println(new Date() + ": 客户端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
    }

}
