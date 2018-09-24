package com.ccb.tmw.chat.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "chat_msg")
@Entity
public class ChatMsg implements Serializable {

    private static final long serialVersionUID = 5253944078050801342L;
    /**
     * 消息ID
     */
    @Id
    private String id;

    /**
     * 发送UserId
     */
    @Column
    private String sendUserId;


    /**
     * 接受消息UserId
     */
    @Column
    private String acceptUserId;

    /**
     * 消息内容
     */
    @Column
    private String msg;

    /**
     * 签收标志
     */
    @Column
    private Integer signFlag;

    /**
     * 发送时间
     */
    @Column
    private Date createTime;
}