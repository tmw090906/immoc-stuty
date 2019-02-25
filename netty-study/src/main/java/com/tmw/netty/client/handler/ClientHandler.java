package com.tmw.netty.client.handler;

import com.tmw.netty.packet.Packet;
import com.tmw.netty.packet.PacketCodeC;
import com.tmw.netty.packet.request.LoginRequestPacket;
import com.tmw.netty.packet.response.LoginResponsePacket;
import com.tmw.netty.utils.TimeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(TimeUtils.dateToStandardStr(new Date()) + ":客户端开始登录");

        // 构造登陆报文
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("tianmaowei.zh");
        loginRequestPacket.setPassword("pwd");

        // 编码
        ByteBuf buffer = PacketCodeC.INSTANCE.encode(loginRequestPacket);

        // 写数据
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {

            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                System.out.println(TimeUtils.dateToStandardStr(new Date()) + ": 客户端登录成功 ");
            } else {
                System.out.println(TimeUtils.dateToStandardStr(new Date()) + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
        }


    }



}
