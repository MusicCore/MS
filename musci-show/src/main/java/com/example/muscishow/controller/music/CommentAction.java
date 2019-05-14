package com.example.muscishow.controller.music;

import com.example.muscishow.ResultUtils.Result;
import com.example.muscishow.model.Comment;
import com.example.muscishow.server.CommonServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: yuanci
 * @Date: 2019/4/24
 * @Version: 1.0
 * @Description:
 */
@Controller
public class CommentAction {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    CommonServer commonServer;

    /**
     * show 添加评论
     * @param comment
     * @return
     */
    @PostMapping(value="/comment")
    @ResponseBody
    public Result toComment(@RequestBody Comment comment){
        return commonServer.toComment(comment);
    }

    /**
     * 移除评论
     * @param id
     * @return
     */
    @PostMapping(value="/rComment")
    @ResponseBody
    public Result removerComment(Integer id){
        return commonServer.removerComment(id);
    }
}
