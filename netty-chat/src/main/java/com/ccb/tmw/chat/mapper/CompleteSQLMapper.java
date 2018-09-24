package com.ccb.tmw.chat.mapper;

import com.ccb.tmw.chat.model.vo.FriendsRequestVO;
import com.ccb.tmw.chat.model.vo.MyFriendsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tianmaowei.zh
 * 用于复杂的SQL, 如连表查询等操作
 * 实际上连表操作可以在应用层面解决
 */
@Mapper
public interface CompleteSQLMapper {

    /**
     * 获取添加好友请求列表
     * @param acceptId
     * @return
     */
    List<FriendsRequestVO> listFriendsRequest(@Param("acceptId") String acceptId);


    /**
     * 获取好友列表
     * @param userId
     * @return
     */
    List<MyFriendsVO> listFriends(@Param("userId") String userId);

}
