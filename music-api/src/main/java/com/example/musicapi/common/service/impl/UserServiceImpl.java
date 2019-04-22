package com.example.musicapi.common.service.impl;


import com.example.musicapi.common.dto.UserDto;
import com.example.musicapi.common.mapper.UserMapper;
import com.example.musicapi.common.model.User;
import com.example.musicapi.common.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    protected UserMapper userMapper;

    @Override
    public UserDto selectUserById(String uid) throws Exception {
        User user = userMapper.selectUserById(Integer.valueOf(uid));
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user,dto);
        return dto;
    }

    @Override
    public User selectByAccountAndPwd(User user) throws Exception {
        return userMapper.selectByAccountAndPwd(user);
    }
}
