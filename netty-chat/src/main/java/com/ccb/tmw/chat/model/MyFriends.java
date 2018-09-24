package com.ccb.tmw.chat.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "my_friends")
@Entity
public class MyFriends {

    @Id
    private String id;

    @Column
    private String myUserId;

    @Column
    private String myFriendUserId;

}