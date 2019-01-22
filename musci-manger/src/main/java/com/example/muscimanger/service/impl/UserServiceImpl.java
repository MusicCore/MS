package com.example.muscimanger.service.impl;


import com.example.muscimanger.dto.UserDto;
import com.example.muscimanger.mapper.UserMapper;
import com.example.muscimanger.model.PageForm;
import com.example.muscimanger.model.User;
import com.example.muscimanger.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    protected UserMapper userMapper;

    @Override
    public int insertUser(User user) throws Exception {
        userMapper.insert(user);
        return 0;
    }

    @Override
    public int updateUser(User user) throws Exception {
        userMapper.update(user);
        return 0;
    }

    @Override
    public User listUserbyAccount(String account) throws Exception {
        User user = userMapper.getUser(account);
        return user;
    }

    @Override
    public List<User> listUserbyAll(PageForm pageForm) throws Exception {
        List<User> list = userMapper.getUserlist(pageForm);
        return list;
    }

    @Override
    public Long listUserTotal() {
        return userMapper.getUserListTotal();
    }

    @Override
    public User checkByAccountAndPwd(String account, String password) throws Exception {
        User user = userMapper.checkByAccountAndPwd(account,password);
        return user;
    }

    @Override
    public void checkIsHaveAccount(User user) throws Exception {
        userMapper.selectIsHaveAccount(user);
    }

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
