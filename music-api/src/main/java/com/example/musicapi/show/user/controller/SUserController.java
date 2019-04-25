package com.example.musicapi.show.user.controller;

import com.example.musicapi.common.ResultUtils.Result;
import com.example.musicapi.common.ResultUtils.ResultFactory;
import com.example.musicapi.common.dto.UserDto;
import com.example.musicapi.common.model.User;
import com.example.musicapi.common.service.impl.SecurityServiceImpl;
import com.example.musicapi.common.until.TFM;
import com.example.musicapi.show.user.service.SUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * show用户功能
 * wjk
 */
@RestController
@RequestMapping("/show")
public class SUserController {
    @Autowired
    private SecurityServiceImpl securityService;

    @Resource(name = "sUserService")
    private SUserService sUserService;

    private final static Logger log = LoggerFactory.getLogger(SUserController.class);

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping(value = "/register")
    public Result register(@RequestBody User user){
        log.info("\n-------------------Method : register--------------------\n");
        try {
            sUserService.checkIsHaveAccount(user);
            Result result= ResultFactory.buildFailResult("账号已存在");
            return result;
        }catch (Exception e){
            try {
                setUser(user);
                sUserService.insertUser(user);
                Result result=ResultFactory.buildSuccessResult("注册成功!!");
                return result;
            }catch (Exception e1){
                log.debug(e1.getMessage());
                Result result=ResultFactory.buildFailResult(e1.getMessage());
                return result;
            }
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
            sUserService.updateUser(user);
        }catch (Exception e){
            log.info(e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
        return ResultFactory.buildSuccessResult("更新用户信息成功");
    }
    /**
     * 修改密码
     * @return
     */
    @PostMapping(value = "/updatePwd")
    public Result updatePassword(@RequestBody UserDto userDto){
        log.info("\n-------------------Method : getLostPassword--------------------\n");
        try{
            String oldPwd  = TFM.md5(userDto.getOldPassword());
            User user = sUserService.checkByAccountAndPwd(userDto.getAccount(),oldPwd);
            if (null != user){
                String newPwd = TFM.md5(userDto.getNewPassword());
                user.setPassword(newPwd);
                sUserService.updateUser(user);
            }
            securityService.deleteToken(userDto.getToken());
        }catch (Exception se){
//            在命令行打印异常信息在程序中出错的位置及原因            se.printStackTrace();
            log.debug(se.getMessage());
            return ResultFactory.buildFailResult("修改密码失败");
        }
        return ResultFactory.buildSuccessResult("修改密码成功，请重新登入");
    }
    /**
     * User 数据装填
     * @param user
     * @return
     */
    private  User setUser(User user){
        //默认系统时间
        Date date = new Date();
        user.setPassword(TFM.md5(user.getPassword()));
        if (null==user.getRoles())
            user.setRoles("user");
        if (null==user.getAvatar())
            user.setAvatar("/static/img/body-img/other/tx.jpg");
        if (null==user.getCreatetime())
            user.setCreatetime(date);
        if (null==user.getUpdatetime())
            user.setUpdatetime(date);
        if (null==user.getActive())
            user.setActive("Y");
        return user;
    }
}
