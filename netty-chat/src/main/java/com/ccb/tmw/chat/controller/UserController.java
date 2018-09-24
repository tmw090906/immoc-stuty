package com.ccb.tmw.chat.controller;


import com.ccb.tmw.chat.enums.OperatorFriendRequestTypeEnum;
import com.ccb.tmw.chat.enums.SearchFriendsStatusEnum;
import com.ccb.tmw.chat.model.Users;
import com.ccb.tmw.chat.model.bo.UsersBO;
import com.ccb.tmw.chat.model.vo.UsersVO;
import com.ccb.tmw.chat.repository.UsersRepository;
import com.ccb.tmw.chat.service.IUserService;
import com.ccb.tmw.chat.utils.ChatResponse;
import com.ccb.tmw.chat.utils.FastDFSClient;
import com.ccb.tmw.chat.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@RestController
@RequestMapping("user")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    private final IUserService userService;

    private final FastDFSClient fastDFSClient;

    private final UsersRepository usersRepository;


    public UserController(IUserService userService,
                          FastDFSClient fastDFSClient,
                          UsersRepository usersRepository) {
        this.userService = userService;
        this.fastDFSClient = fastDFSClient;
        this.usersRepository = usersRepository;
    }

    @PostMapping("registerOrLogin")
    public ChatResponse registerOrLogin(@RequestBody Users users) {
        if (StringUtils.isBlank(users.getUsername())
                || StringUtils.isBlank(users.getPassword())) {
            return ChatResponse.errorMsg("用户名或密码不能为空...");
        }
        Users userResult;
        if (userService.existUsername(users.getUsername())) {
            userResult = userService.login(users);
            if (userResult == null) {
                return ChatResponse.errorMsg("密码错误,请确认后重新输入");
            }
        } else {
            userResult = userService.register(users);
        }
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userResult, usersVO);
        return  ChatResponse.ok(usersVO);
    }


    @PostMapping("/uploadImg")
    public ChatResponse uploadFaceBase64(@RequestBody UsersBO usersBO) throws Exception {

        // 获取前端传过来的base64
        String base64Data = usersBO.getFaceData();
        String userFacePath = "D:\\" + usersBO.getUserId() + "userFace64.png";
        FileUtils.base64ToFile(userFacePath, base64Data);

        // 上传文件到fastDfs
        MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
        String url = fastDFSClient.uploadBase64(faceFile);

        log.info(url);



        // 获取缩略图url
        String thump = "_80x80.";
        String urlPart[] = url.split("\\.");
        String tempImgUrl = urlPart[0] + thump + urlPart[1];

        // 更新用户头像
        Users user = new Users();
        user.setId(usersBO.getUserId());
        user.setFaceImage(tempImgUrl);
        user.setFaceImageBig(url);

        Users returnUser = userService.updateUserInfo(user);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(returnUser, usersVO);

        return ChatResponse.ok(usersVO);

    }

    @PostMapping("setNickName")
    public ChatResponse setNickName(@RequestBody UsersBO usersBO) {

        Users user = new Users();
        user.setId(usersBO.getUserId());
        user.setNickname(usersBO.getNickName());

        Users result = userService.updateUserInfo(user);

        return ChatResponse.ok(result);
    }


    /**
     * 根据帐号做匹配查询,而不是模糊查询
     * @param myUserId         当前用户的UserId
     * @param friendUsername   请求添加好友的UserId
     * @return 响应状态
     */
    @PostMapping("searchUser")
    public ChatResponse searchUser(String myUserId,
                                   String friendUsername) {

        if (StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUsername)) {
            return ChatResponse.errorMsg("");
        }

        // 1 如果搜索的用户不存在, 返回无此用户
        // 2 搜索帐号是你自己, 返回 不能添加自己
        // 3 如果搜索的好友是你的好友, 返回 该用户已经是你的好友

        Integer status = userService.preconditionSearchFriend(myUserId, friendUsername);

        if (status == 0) {
            Users friendUserInfo = usersRepository.findUsersByUsername(friendUsername);
            UsersVO usersVO = new UsersVO();
            BeanUtils.copyProperties(friendUserInfo, usersVO);
            return ChatResponse.ok(usersVO);
        }

        return ChatResponse.errorMsg(SearchFriendsStatusEnum.getMsgByKey(status));
    }


    @Transactional
    @PostMapping("addFriendRequest")
    public ChatResponse addFriendRequest(String myUserId,
                                         String friendUsername) {

        if (StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUsername)) {
            return ChatResponse.errorMsg("");
        }

        ChatResponse availableResult = this.searchUser(myUserId, friendUsername);
        if (availableResult.isOK()) {
            userService.sendAddFriendRequest(myUserId, friendUsername);
        }
        return availableResult;
    }


    @Transactional
    @PostMapping("getFriendsRequestList")
    public ChatResponse getFriendsRequestList(String acceptId) {
        if (StringUtils.isBlank(acceptId)) {
            return ChatResponse.errorMsg("");
        }

        return ChatResponse
                .ok(userService.listAddFriendsRequest(acceptId));
    }


    @Transactional
    @PostMapping("operatorFriendRequest")
    public ChatResponse operatorFriendRequest(String acceptUserId,
                                              String sendUserId,
                                              Integer operatorType) {
        if (StringUtils.isBlank(acceptUserId) || StringUtils.isBlank(sendUserId)) {
            return ChatResponse.errorMsg("");
        }

        // 如果操作类型没有初始化, 则报错
        if (StringUtils.isBlank(OperatorFriendRequestTypeEnum.getMsgByType(operatorType))) {
            return ChatResponse.errorMsg("操作类型非法");
        }

        userService.operatorFriendRequest(acceptUserId, sendUserId, operatorType);

        return ChatResponse.ok(userService.getFriendsList(acceptUserId));
    }



    @PostMapping("myFriends")
    public ChatResponse listMyFriends(String userId) {
        if (StringUtils.isBlank(userId)) {
            return ChatResponse.errorMsg("");
        }
        return ChatResponse.ok(userService.getFriendsList(userId));
    }


}