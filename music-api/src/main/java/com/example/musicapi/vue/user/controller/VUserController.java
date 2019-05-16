package com.example.musicapi.vue.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.musicapi.common.ResultUtils.Result;
import com.example.musicapi.common.ResultUtils.ResultFactory;
import com.example.musicapi.common.model.CommonContext;
import com.example.musicapi.common.model.PageForm;
import com.example.musicapi.common.model.User;
import com.example.musicapi.vue.user.service.VUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * vue用户功能
 * wjk
 */
@RestController
@RequestMapping("/vue")
public class VUserController {

    @Resource(name = "vUserService")
    private VUserService vUserService;

    private final static Logger log = LoggerFactory.getLogger(VUserController.class);

    /**
     * 查看用户信息(全)
     * @return
     */
    @PostMapping(value = "/userlist")
    public Object getUserList(@RequestBody PageForm pageForm){
        log.info("\n-------------------Method : getAllUser--------------------\n");
        try {
            List<User> list = vUserService.listUserbyAll(pageForm);
            Long total = vUserService.listUserTotal();
            JSONObject object = new JSONObject();
            object.put("items",list);
            object.put("total",total);
            Result result= ResultFactory.buildSuccessResult(object);
            return result;
        }catch (Exception e){
            log.debug(e.getMessage());
            Result result=ResultFactory.buildFailResult(e.getMessage());
            return result;
        }
    }
    /**
     * 拉取用户信息
     * @param account
     * @return
     */
    @PostMapping(value = "/info")
    public Object getUserInfo(@RequestParam String account){
        log.info("\n-------------------Method : 取得"+account+"信息--------------------\n");
        try {
            User user = vUserService.listUserbyAccount(account);
            JSONObject object = new JSONObject();
            object.put("name", CommonContext.getInstance().getName());
            object.put("avatar",CommonContext.getInstance().getAvatar());
            String [] roles = {CommonContext.getInstance().getRoles()};
            object.put("roles",roles);//返回数组格式权限
            Result result=ResultFactory.buildSuccessResult(object);
            return result;
        }catch (Exception e){
            log.debug(e.getMessage());
            Result result=ResultFactory.buildFailResult(e.getMessage());
            return result;
        }
    }
    /**
     * 更新
     * @param user
     * @return
     */
    @PostMapping(value = "/userupdate")
    public Result updateInfo(@RequestBody User user){
        log.info("\n-------------------Method :  更新"+user.getAccount() +"--------------------\n");
        try{
            vUserService.updateUser(user);
        }catch (Exception e){
            log.info(e.getMessage());
            Result result=ResultFactory.buildFailResult(e.getMessage());
        }
        return ResultFactory.buildSuccessResult("更新用户信息成功");
    }
}
