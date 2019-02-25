package com.tmw.netty.packet.response;

import com.tmw.netty.common.Command;
import com.tmw.netty.packet.Packet;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }


}
