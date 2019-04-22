package com.example.musicapi.show.user.service.impl;

import com.example.musicapi.common.model.User;
import com.example.musicapi.show.user.mapper.SUserMapper;
import com.example.musicapi.show.user.service.SUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("sUserService")
public class SUserServiceImpl implements SUserService {

    @Autowired
    protected SUserMapper sUserMapper;

    @Override
    public int insertUser(User user) throws Exception {
        return sUserMapper.insert(user);
    }

    @Override
    public int updateUser(User user) throws Exception {
        return sUserMapper.update(user);
    }

    @Override
    public User checkByAccountAndPwd(String account, String password) throws Exception {
        User user = sUserMapper.checkByAccountAndPwd(account,password);
        return user;
    }

    @Override
    public void checkIsHaveAccount(User user) throws Exception {
        sUserMapper.selectIsHaveAccount(user);
    }
}
