package com.ccb.tmw.chat.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "friends_request")
@Entity
public class FriendsRequest {

    @Id
    private String id;

    @Column
    private String sendUserId;

    @Column
    private String acceptUserId;

    @Column
    private Date requestDataTime;
}