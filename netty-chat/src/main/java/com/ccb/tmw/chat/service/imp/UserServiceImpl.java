package com.ccb.tmw.chat.service.imp;

import com.ccb.tmw.chat.enums.MsgSignFlagEnum;
import com.ccb.tmw.chat.enums.OperatorFriendRequestTypeEnum;
import com.ccb.tmw.chat.enums.SearchFriendsStatusEnum;
import com.ccb.tmw.chat.mapper.CompleteSQLMapper;
import com.ccb.tmw.chat.model.ChatMsg;
import com.ccb.tmw.chat.model.FriendsRequest;
import com.ccb.tmw.chat.model.MyFriends;
import com.ccb.tmw.chat.model.Users;
import com.ccb.tmw.chat.model.socket.MessageDTO;
import com.ccb.tmw.chat.model.vo.FriendsRequestVO;
import com.ccb.tmw.chat.model.vo.MyFriendsVO;
import com.ccb.tmw.chat.repository.ChatMsgRepository;
import com.ccb.tmw.chat.repository.FriendsRequestRepository;
import com.ccb.tmw.chat.repository.MyFriendsRepository;
import com.ccb.tmw.chat.repository.UsersRepository;
import com.ccb.tmw.chat.service.IUserService;
import com.ccb.tmw.chat.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements IUserService {

    private final UsersRepository usersRepository;

    private final MyFriendsRepository myFriendsRepository;

    private final Sid sid;

    private final QRCodeUtils qrCodeUtils;

    private final FastDFSClient fastDFSClient;

    private final FriendsRequestRepository friendsRequestRepository;

    private final CompleteSQLMapper completeSQLMapper;

    private final ChatMsgRepository chatMsgRepositoryl;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository,
                           MyFriendsRepository myFriendsRepository,
                           FriendsRequestRepository friendsRequestRepository,
                           ChatMsgRepository chatMsgRepository,
                           CompleteSQLMapper completeSQLMapper,
                           Sid sid,
                           QRCodeUtils qrCodeUtils,
                           FastDFSClient fastDFSClient) {
        this.usersRepository = usersRepository;
        this.myFriendsRepository = myFriendsRepository;
        this.friendsRequestRepository = friendsRequestRepository;
        this.chatMsgRepositoryl = chatMsgRepository;
        this.completeSQLMapper = completeSQLMapper;
        this.sid = sid;
        this.qrCodeUtils = qrCodeUtils;
        this.fastDFSClient = fastDFSClient;
    }


    @Override
    public boolean existUsername(String username) {
        return usersRepository.countUsersByUsername(username) > 0;
    }


    @Override
    public Users login(Users users) {
        users.setPassword(MD5Util.MD5EncodeUtf8(users.getPassword()));
        return usersRepository
                .findUsersByUsernameAndPassword(users.getUsername(),
                        users.getPassword());
    }

    @Transactional
    @Override
    public Users register(Users users) {
        String userId = sid.nextShort();
        users.setId(userId);
        users.setNickname(users.getUsername());

        // 为用户生成一个唯一的二维码
        String qrCodePath = "D://user" + userId + "qrcode.png";

        // netty_chat: [username]
        qrCodeUtils.createQRCode(qrCodePath, "netty_chat:" + users.getUsername());

        MultipartFile qrCodeFile = FileUtils.fileToMultipart(qrCodePath);
        String qrCodeUrl = null;
        try {
            qrCodeUrl = fastDFSClient.uploadQRCode(qrCodeFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        users.setQrcode(qrCodeUrl == null ? "" : qrCodeUrl);

        users.setPassword(MD5Util.MD5EncodeUtf8(users.getPassword()));
        if (StringUtils.isBlank(users.getFaceImage())) {
            users.setFaceImage("M00/00/00/wKh3hluP8UeASeu7AAQv5mIN0nI122.png");
            users.setFaceImageBig("M00/00/00/wKh3hluP8UeASeu7AAQv5mIN0nI122.png");
        }

        
        File qrCodeImg = new File(qrCodePath);
        qrCodeImg.deleteOnExit();


        return usersRepository.save(users);
    }

    @Transactional
    @Override
    public Users updateUserInfo(Users userFaceInfo) {
        Optional<Users> optionalUsers = usersRepository.findById(userFaceInfo.getId());
        if (optionalUsers.isPresent()) {
            Users user = optionalUsers.get();
            BeanUtils.copyProperties(userFaceInfo, optionalUsers.get());
            usersRepository.save(user);
        }
        return optionalUsers.orElse(userFaceInfo);
    }

    @Override
    public Integer preconditionSearchFriend(String myUserId, String friendUsername) {
        Users friendUserInfo = usersRepository.findUsersByUsername(friendUsername);

        if (friendUserInfo == null) {
            return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
        }

        if (myUserId.equals(friendUserInfo.getId())) {
            return SearchFriendsStatusEnum.NOT_YOURSELF.status;
        }

        MyFriends myFriends = myFriendsRepository
                .findMyFriendsByMyUserIdAndMyFriendUserId(myUserId, friendUserInfo.getId());

        if (myFriends != null) {
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
        }

        return SearchFriendsStatusEnum.SUCCESS.status;
    }

    @Transactional
    @Override
    public void sendAddFriendRequest(String myUserId, String friendUsername) {

        Users friendUserInfo = usersRepository.findUsersByUsername(friendUsername);
        if (friendUserInfo == null) {
            return;
        }
        if (friendsRequestRepository.countBySendUserIdAndAcceptUserId(myUserId, friendUserInfo.getId()) < 1) {
            FriendsRequest request = new FriendsRequest();
            request.setAcceptUserId(friendUserInfo.getId());
            request.setSendUserId(myUserId);
            request.setId(sid.nextShort());
            request.setRequestDataTime(new Date());
            friendsRequestRepository.save(request);
        }
    }

    @Override
    public List<FriendsRequestVO> listAddFriendsRequest(String acceptId) {
        return completeSQLMapper.listFriendsRequest(acceptId);
    }

    @Override
    public void operatorFriendRequest(String acceptUserId, String sendUserId, Integer operatorType) {
        if (OperatorFriendRequestTypeEnum.PASS.type.equals(operatorType)) {
            this.saveFriend(acceptUserId, sendUserId);
            this.saveFriend(sendUserId, acceptUserId);
        }
        friendsRequestRepository.deleteByAcceptUserIdAndSendUserId(acceptUserId, sendUserId);
    }


    @Override
    public List<MyFriendsVO> getFriendsList(String userId) {
        return completeSQLMapper.listFriends(userId);
    }


    @Override
    public String saveMsg(MessageDTO messageDTO) {
        String msgId = sid.nextShort();
        ChatMsg chatMsg = new ChatMsg();
        BeanUtils.copyProperties(messageDTO, chatMsg);
        chatMsg.setId(msgId);
        chatMsg.setCreateTime(new Date());
        chatMsg.setSignFlag(MsgSignFlagEnum.unsign.type);
        chatMsgRepositoryl.save(chatMsg);
        return msgId;
    }

    private void saveFriend(String myUserId, String myFriendUserId) {
        MyFriends record = new MyFriends();
        record.setMyUserId(myUserId);
        record.setMyFriendUserId(myFriendUserId);
        record.setId(sid.nextShort());
        myFriendsRepository.save(record);
    }

}
