package com.example.musicapi.vue.user.mapper;

import com.example.musicapi.show.user.mapper.UserMapper;
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
public interface VUserMapper extends UserMapper {

    /**
     * vue分页查看用户信息
     * @param pageForm
     * @return
     * @throws Exception
     */
    public List<User> getUserlist(@Param("pageForm") PageForm pageForm) throws Exception;

    /**
     * vue分页时拿到总数
     * @return
     */
    public Long getUserListTotal();

    /**
     * vue登录后拉取用户信息
     * @param account
     * @return
     * @throws Exception
     */
    public User getUser(@Param("account") String account) throws Exception;

}
