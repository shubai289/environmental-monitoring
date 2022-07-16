package com.wclp.springserver.service;

import com.wclp.springserver.pojo.Page;
import com.wclp.springserver.pojo.User;

public interface UserService {
    /**
     * 登录功能
     */
    String login(User user);

    /**
     * 账户注册功能
     */
    int register(User user);

    /**
     * 获取用户列表
     */
    Page<User> findUserByPage(int page, int rows);
}
