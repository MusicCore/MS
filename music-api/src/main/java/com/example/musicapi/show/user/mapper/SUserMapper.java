package com.example.musicapi.show.user.mapper;

import com.example.musicapi.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户Mapper
 */
@Repository
@Mapper
public interface SUserMapper extends UserMapper {

    /**
     * show验证账号密码
     * @param account
     * @param password
     * @return
     * @throws Exception
     */
    public User checkByAccountAndPwd(String account, String password) throws Exception;

    /**
     * show注册用户
     * @param user
     * @return
     * @throws Exception
     */
    public int insert(User user) throws Exception;

    /**
     * show注册时使用
     * @param user
     * @return
     * @throws Exception
     */
    public int selectIsHaveAccount(User user) throws Exception;

    /**
     * show根据账号找回密码
     * @param user
     * @return
     * @throws Exception
     */
    public String selectPwdByAccount(User user) throws Exception;
}
