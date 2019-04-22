package com.example.musicapi.common.service;


import com.example.musicapi.common.dto.UserDto;
import com.example.musicapi.common.model.User;

/**
 * 公用接口
 */
public interface UserService {

    /**
     * 验证账号密码
     * @return
     * @throws Exception
     */
    public User selectByAccountAndPwd(User user) throws Exception;


    /**
     * 拦截器处理上下文
     * @param
     * @return
     * @throws Exception
     */
    public UserDto selectUserById(String uid) throws Exception;
}
