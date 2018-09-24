package com.ccb.tmw.chat.repository;

import com.ccb.tmw.chat.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    /**
     * 根据用户名查是否存在客户
     * @param username
     * @return
     */
    int countUsersByUsername(String username);


    /**
     * 使用用户名和密码进行登陆
     * @param username
     * @param password
     * @return
     */
    Users findUsersByUsernameAndPassword(String username, String password);


    /**
     * 根据帐号查询用户信息
     * @param username
     * @return
     */
    Users findUsersByUsername(String username);
}
