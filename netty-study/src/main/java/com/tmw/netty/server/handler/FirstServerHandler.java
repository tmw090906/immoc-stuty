package com.tmw.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ByteBuf byteBuf = (ByteBuf) msg;

        // 读数据时, 也需要指定为UTF-8编码
        System.out.println(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));

        System.out.println(new Date() + ": 服务端开始写数据");
        // 服务端开始写数据
        ByteBuf writerBuf = getByteBuf(ctx);

        ctx.writeAndFlush(writerBuf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {

        ByteBuf buf = ctx.alloc().buffer();

        byte[] message = "tianmaowei.zh 这么晚了还在学习, 不容易不容易!".getBytes(Charset.forName("UTF-8"));

        buf.writeBytes(message);

        return buf;
    }


}
