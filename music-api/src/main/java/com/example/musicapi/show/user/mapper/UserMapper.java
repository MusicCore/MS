package com.example.musicapi.show.user.mapper;

import com.example.musicapi.common.model.PageForm;
import com.example.musicapi.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户Mapper
 */
@Repository
@Mapper
public interface UserMapper {

    /**
     * 统一的登录
     * @param user
     * @return
     * @throws Exception
     */
    public User selectByAccountAndPwd(User user) throws Exception;

    /**
     * show修改密码，vue修改用户信息
     * @param user
     * @return
     * @throws Exception
     */
    public int update(User user) throws Exception;

    /**
     * 拦截器通过本地线程存储的上下文id查询user
     * @param id
     * @return
     * @throws Exception
     */
    public User selectUserById(Integer id) throws Exception;

    public String selectPwdByAccount(User user) throws Exception;

    public User checkAP(String account, String password) throws Exception;

}
