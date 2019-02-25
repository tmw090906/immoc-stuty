package com.tmw.netty.packet;

import com.tmw.netty.common.Command;
import com.tmw.netty.packet.request.LoginRequestPacket;
import com.tmw.netty.packet.response.LoginResponsePacket;
import com.tmw.netty.serialize.JSONSerializer;
import com.tmw.netty.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

public class PacketCodeC {

    /**
     * 单例模式
     */
    public static final PacketCodeC INSTANCE = new PacketCodeC();


    /**
     * 8 位为 1个字节
     * 八进制 1 个数字为 4个字节
     */
    private static final int MAGIC_NUMBER = 0x12345678;

    private Serializer serializer;


    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }




    public ByteBuf encode(Packet packet) {
        // 1. 创建ByteBuf对象
        ByteBuf byteBuf = getByteBuf(ByteBufAllocator.DEFAULT, packet);

        return byteBuf;
    }


    public ByteBuf encode(ByteBufAllocator alloc, Packet packet) {

        ByteBuf byteBuf = getByteBuf(alloc, packet);


        return byteBuf;
    }

    private ByteBuf getByteBuf(ByteBufAllocator alloc, Packet packet) {
        if (serializer == null) {
            serializer = Serializer.DEFAULT;
        }

        // 1. 创建ByteBuf对象
        ByteBuf byteBuf = alloc.ioBuffer();

        // 2. 序列化Java对象
        byte[] bytes = serializer.serialize(packet);

        // 魔数
        byteBuf.writeInt(MAGIC_NUMBER);
        // 协议版本
        byteBuf.writeByte(packet.getVersion());
        // 序列化算法
        byteBuf.writeByte(serializer.getSerializerAlgorithm());
        // 请求指令
        byteBuf.writeByte(packet.getCommand());
        // 报文体长度
        byteBuf.writeInt(bytes.length);
        // 报文体
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }


    public Packet decode(ByteBuf byteBuf) {
        int magicNumber = byteBuf.readInt();
        // 如果不是版本中的魔数
        if (MAGIC_NUMBER != magicNumber) {
            return null;
        }

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 报文体长度
        int length = byteBuf.readInt();

        // 读取报文体
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);

        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType == null || serializer == null) {
            return null;
        }

        return serializer.deserialize(requestType, bytes);
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }

}
