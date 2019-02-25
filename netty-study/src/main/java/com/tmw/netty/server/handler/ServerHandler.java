package com.tmw.netty.server.handler;

import com.tmw.netty.packet.Packet;
import com.tmw.netty.packet.PacketCodeC;
import com.tmw.netty.packet.request.LoginRequestPacket;
import com.tmw.netty.packet.response.LoginResponsePacket;
import com.tmw.netty.utils.TimeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println(TimeUtils.dateToStandardStr(new Date()) + ": 客户端开始登录……");

        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);

        if (packet instanceof LoginRequestPacket) {

            // 登录
            LoginResponsePacket responsePacket = new LoginResponsePacket();
            responsePacket.setVersion(packet.getVersion());

            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            if (valid(loginRequestPacket)) {
                responsePacket.setReason(" 账号密码校验成功 ");
                responsePacket.setSuccess(true);
                System.out.println(TimeUtils.dateToStandardStr(new Date()) + ": 登录成功!");
            } else {
                responsePacket.setReason(" 账号密码校验失败 ");
                responsePacket.setSuccess(false);
                System.out.println(TimeUtils.dateToStandardStr(new Date()) + ": 登录失败!");
            }
            // 登录响应
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), responsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
