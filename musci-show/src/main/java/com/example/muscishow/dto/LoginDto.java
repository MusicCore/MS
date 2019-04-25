package com.example.muscishow.dto;

import com.example.muscishow.model.User;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: yuanci
 * @Date: 2019/4/24
 * @Version: 1.0
 * @Description:
 */
@Data
public class LoginDto {
    private User user;
    private HttpServletRequest request;
}
