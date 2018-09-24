package com.ccb.tmw.chat.repository;

import com.ccb.tmw.chat.model.MyFriends;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyFriendsRepository extends JpaRepository<MyFriends, String> {


    MyFriends findMyFriendsByMyUserIdAndMyFriendUserId(String myUserId, String friendUserId);



}
