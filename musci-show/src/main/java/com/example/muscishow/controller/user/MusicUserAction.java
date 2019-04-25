package com.example.muscishow.controller.user;

import com.example.muscishow.ResultUtils.Result;
import com.example.muscishow.model.User;
import com.example.muscishow.server.CommonServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Author: yuanci
 * @Date: 2019/4/24
 * @Version: 1.0
 * @Description:
 */
@Controller
public class MusicUserAction {

    @Autowired
    CommonServer commonServer;

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping(value = "/api/register")
    @ResponseBody
    public Result register(User user){
        return commonServer.register(user);
    }
}
