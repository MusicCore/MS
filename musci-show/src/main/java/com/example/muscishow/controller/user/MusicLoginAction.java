package com.example.muscishow.controller.user;

import com.example.muscishow.ResultUtils.Result;
import com.example.muscishow.dto.LoginDto;
import com.example.muscishow.model.User;
import com.example.muscishow.server.CommonServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yuanci
 * @Date: 2019/4/24
 * @Version: 1.0
 * @Description:
 */
@Controller
public class MusicLoginAction {
    @Autowired
    CommonServer commonServer;

    /**
     * show登入
     * @param user
     * @param
     * @return
     */
    @PostMapping(value = "/api/login")
    @ResponseBody
    public Result vueLogin(@RequestBody User user, HttpServletRequest request){
        return commonServer.showLogin(user);
    }

    /**
     * show登出
     * @param
     * @return
     */
    @RequestMapping(value = "/api/logout")
    @ResponseBody
    public Result loginOut(HttpServletRequest request){
        return commonServer.showLoginOut();
    }
}
