package com.ccb.tmw.chat.model.socket;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataSocketFrame implements Serializable {

    private static final long serialVersionUID = -1155744346410946170L;
    /**
     * 动作类型
     */
    private Integer action;

    /**
     * 消息实体类
     */
    private MessageDTO message;


    /**
     * 备用字段
     */
    private String extend;

}
