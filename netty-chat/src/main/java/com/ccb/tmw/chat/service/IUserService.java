package com.ccb.tmw.chat.service;

import com.ccb.tmw.chat.model.ChatMsg;
import com.ccb.tmw.chat.model.Users;
import com.ccb.tmw.chat.model.socket.MessageDTO;
import com.ccb.tmw.chat.model.vo.FriendsRequestVO;
import com.ccb.tmw.chat.model.vo.MyFriendsVO;

import java.util.List;

public interface IUserService {

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    boolean existUsername(String username);


    /**
     * 登陆
     * @param users
     * @return
     */
    Users login(Users users);

    /**
     * 注册
     * @param users
     * @return
     */
    Users register(Users users);

    /**
     * 修改用户信息
     * @param user
     */
    Users updateUserInfo(Users user);


    /**
     * 搜索好友前置条件
     * @param myUserId
     * @param friendUsername
     * @return
     */
    Integer preconditionSearchFriend(String myUserId, String friendUsername);

    /**
     * 发送添加好友请求
     * @param myUserId
     * @param friendUsername
     */
    void sendAddFriendRequest(String myUserId, String friendUsername);

    /**
     * 获取好友请求列表
     * @param acceptId
     * @return
     */
    List<FriendsRequestVO> listAddFriendsRequest(String acceptId);

    /**
     * 操作好友请求
     * @param acceptUserId
     * @param sendUserId
     * @param operatorType
     */
    void operatorFriendRequest(String acceptUserId, String sendUserId, Integer operatorType);


    /**
     * 获取好友请求
     * @param userId
     * @return
     */
    List<MyFriendsVO> getFriendsList(String userId);


    /**
     * 保存聊天记录
     * @param messageDTO
     * @return
     */
    String saveMsg(MessageDTO messageDTO);

}
