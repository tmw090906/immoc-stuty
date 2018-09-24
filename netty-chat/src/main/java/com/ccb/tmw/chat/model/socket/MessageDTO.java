package com.ccb.tmw.chat.model.socket;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 3812935555131068207L;

    private String msgId;

    private String sendUserId;

    private String acceptUserId;

    private String text;
}
