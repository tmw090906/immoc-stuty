package com.ccb.tmw.chat.repository;

import com.ccb.tmw.chat.model.ChatMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMsgRepository extends JpaRepository<ChatMsg, String> {



}
