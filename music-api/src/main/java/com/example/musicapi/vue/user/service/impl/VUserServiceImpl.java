package com.example.musicapi.vue.user.service.impl;


import com.example.musicapi.common.model.PageForm;
import com.example.musicapi.common.model.User;
import com.example.musicapi.vue.user.mapper.VUserMapper;
import com.example.musicapi.vue.user.service.VUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vUserService")
public class VUserServiceImpl implements VUserService {

    @Autowired
    protected VUserMapper vUserMapper;

    @Override
    public int updateUser(User user) throws Exception {
        return vUserMapper.update(user);
    }

    @Override
    public User listUserbyAccount(String account) throws Exception {
        User user = vUserMapper.getUser(account);
        return user;
    }

    @Override
    public List<User> listUserbyAll(PageForm pageForm) throws Exception {
        List<User> list = vUserMapper.getUserlist(pageForm);
        return list;
    }

    @Override
    public Long listUserTotal() {
        return vUserMapper.getUserListTotal();
    }

}
