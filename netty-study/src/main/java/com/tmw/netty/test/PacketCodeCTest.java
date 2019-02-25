package com.tmw.netty.test;

import com.tmw.netty.packet.Packet;
import com.tmw.netty.packet.PacketCodeC;
import com.tmw.netty.packet.request.LoginRequestPacket;
import com.tmw.netty.serialize.JSONSerializer;
import com.tmw.netty.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class PacketCodeCTest {

    @Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("zhangsan");
        loginRequestPacket.setPassword("password");

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);

        ByteBuf newByteBuf = byteBuf.copy();

        Packet decodedPacket = packetCodeC.decode(byteBuf);


        newByteBuf.skipBytes(4 + 1 + 1 + 1);
        int length = newByteBuf.readInt();
        byte[] bytes = new byte[length];
        newByteBuf.readBytes(bytes);
        System.out.println(new String(bytes));


        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }
}