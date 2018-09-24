package com.ccb.tmw.chat.repository;

import com.ccb.tmw.chat.model.FriendsRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendsRequestRepository  extends JpaRepository<FriendsRequest, String> {

    Integer countBySendUserIdAndAcceptUserId(String sendUserId, String acceptUserId);


    Integer deleteByAcceptUserIdAndSendUserId(String acceptUserId, String sendUserId);


}

