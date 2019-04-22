package com.example.musicapi.vue.user.service;

import com.example.musicapi.common.dto.UserDto;
import com.example.musicapi.common.model.PageForm;
import com.example.musicapi.common.model.User;

import java.util.List;

public interface VUserService {

    /**
     * 更新
     * @param user
     * @return
     * @throws Exception
     */
    public int updateUser(User user) throws Exception;

    /**
     * vue拉取
     * @param account
     * @return
     * @throws Exception
     */
    public User listUserbyAccount(String account) throws Exception;

    /**
     * vue列表
     * @param pageForm
     * @return
     * @throws Exception
     */
    public List<User> listUserbyAll(PageForm pageForm)throws Exception;

    /**
     * vue获得总数
     * @return
     */
    public Long listUserTotal();

}
