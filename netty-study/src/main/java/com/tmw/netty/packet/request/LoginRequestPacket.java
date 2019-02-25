package com.tmw.netty.packet.request;

import com.tmw.netty.common.Command;
import com.tmw.netty.packet.Packet;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {


    private String userId;

    private String username;

    private String password;


    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
