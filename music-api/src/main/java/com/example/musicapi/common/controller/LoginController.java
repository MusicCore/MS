package com.example.musicapi.common.controller;

import com.example.musicapi.common.ResultUtils.Result;
import com.example.musicapi.common.ResultUtils.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统一的token过期action
 */
@RestController
@RequestMapping(value = "/api")
public class LoginController {

    private final static Logger log = LoggerFactory.getLogger(LoginController.class);
    /**
     * token过期处理
     * @return
     */
    @RequestMapping(value = "/token_expire")
    public Result tokenExpire() {
        log.info("\n-------------------Method : token失效 --------------------\n");
        return ResultFactory.buidResult(50014, "token已过期", "");
    }

}
