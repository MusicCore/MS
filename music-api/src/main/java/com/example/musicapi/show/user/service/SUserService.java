package com.example.musicapi.show.user.service;


import com.example.musicapi.common.model.User;

public interface SUserService {
    /**
     * 插入
     * @param user
     * @return
     * @throws Exception
     */
    public int insertUser(User user) throws Exception;
    /**
     * 更新
     * @param user
     * @return
     * @throws Exception
     */
    public int updateUser(User user) throws Exception;

    /**
     * 验证账号密码
     * @param account
     * @param password
     * @return
     * @throws Exception
     */
    public User checkByAccountAndPwd(String account, String password) throws Exception;

    /**
     * 账号是否存在
     * @param user
     * @return
     * @throws Exception
     */
    public void checkIsHaveAccount(User user) throws Exception;

}
