package com.example.muscishow.controller.vue;

import com.example.muscishow.ResultUtils.Result;
import com.example.muscishow.model.PageForm;
import com.example.muscishow.model.User;
import com.example.muscishow.server.CommonServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yuanci
 * @Date: 2019/5/16
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/api/vue")
public class VUserAction {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    CommonServer commonServer;

    /**
     * vue 查看用户信息(全)
     * @return
     */
    @PostMapping(value = "/userlist")
    public Object getUserList_vue( PageForm pageForm){
        return commonServer.getUserList_vue(pageForm);
    }

    /**
     * vue拉取用户信息
     * @param account
     * @return
     */
    @PostMapping(value = "/info")
    public Object getUserInfo_vue(@RequestParam("account") String account){
        return commonServer.getUserInfo_vue(account);
    }

    /**
     * vue更新用户信息
     * @param user
     * @return
     */
    @PostMapping(value = "/userupdate")
    public Result updateInfo_vue(@RequestBody User user){
        return commonServer.updateInfo_vue(user);
    }
}
