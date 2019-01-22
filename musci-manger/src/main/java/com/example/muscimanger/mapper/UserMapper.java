package com.example.muscimanger.mapper;

import com.example.muscimanger.model.PageForm;
import com.example.muscimanger.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    public User selectByAccountAndPwd(User user) throws Exception;

    public User checkByAccountAndPwd(String account, String password) throws Exception;

    public int insert(User user) throws Exception;

    public int update(User user) throws Exception;

    public int selectIsHaveAccount(User user) throws Exception;

    public User selectUserById(Integer id) throws Exception;

    public String selectPwdByAccount(User user) throws Exception;

    public User checkAP(String account, String password) throws Exception;

    public List<User> getUserlist(@Param("pageForm")PageForm pageForm) throws Exception;

    public Long getUserListTotal();

    public User getUser(@Param("account") String account) throws Exception;

}
