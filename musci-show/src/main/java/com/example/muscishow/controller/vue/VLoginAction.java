package com.example.muscishow.controller.vue;

import com.example.muscishow.ResultUtils.Result;
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
public class VLoginAction {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    CommonServer commonServer;

    /**
     * vue登入
     * @param user
     * @param
     * @return
     */
    @PostMapping(value = "/login")
    public Object vueLogin_vue(@RequestBody User user){
        return commonServer.vueLogin_vue(user);
    }

    /**
     * vue登出
     * @param
     * @return
     */
    @RequestMapping(value = "/logout")
    public Result loginOut_vue(){
        return commonServer.loginOut_vue();
    }
}
