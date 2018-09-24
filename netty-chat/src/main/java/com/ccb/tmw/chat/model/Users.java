package com.ccb.tmw.chat.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "users")
@Entity
public class Users {

    @Id
    private String id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String faceImage;

    @Column
    private String faceImageBig;

    @Column
    private String nickname;

    @Column
    private String qrcode;

    @Column
    private String cid;

}