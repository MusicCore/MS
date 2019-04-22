package com.example.musicapi.show.comment.controller;

import com.example.musicapi.common.ResultUtils.Result;
import com.example.musicapi.common.ResultUtils.ResultFactory;
import com.example.musicapi.common.model.Comment;
import com.example.musicapi.common.model.CommonContext;
import com.example.musicapi.show.comment.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 前端Controller
 */
@RestController
@RequestMapping("/show/comment")
public class SCommentController {

    private final static Logger log = LoggerFactory.getLogger(SCommentController.class);

    @Resource(name="commentService")
    private CommentService commentService;

    /**
     * 添加评论
     * @param model
     * @param comment
     * @return
     */
    @PostMapping(value="/comment")
    public Result toComment(Model model, @RequestBody Comment comment){
        try {
            initComment(comment);
            commentService.save(comment);
            log.info("\n账号："+comment.getCommentAuthorAccount()+",用户："+ comment.getCommentAuthorName()+"添加评论\n");
            return ResultFactory.buildSuccessResult("");
        }catch (Exception e){
            log.error("\n添加评论出错："+e+"\n");
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    /**
     * 移除评论
     * @param model
     * @param id
     * @return
     */
    @PostMapping(value="/rComment")
    public Result removerComment(Model model, Integer id){
        try {
            commentService.remove(id);
            log.info("移除id为："+ id +"的评论");
            return ResultFactory.buildSuccessResult("");
        }catch (Exception e){
            log.error("移除id为："+ id +"的评论 出错！");
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }


    /**
     * 装填数据
     * @param comment
     */
    public void initComment(Comment comment){
        //默认系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Date date = new Date();
        comment.setCommentAuthorAccount(CommonContext.getInstance().getAccount());
        comment.setCommentAuthorAvatar(CommonContext.getInstance().getAvatar());
        comment.setCommentAuthorName(CommonContext.getInstance().getName());
        comment.setUserId(CommonContext.getInstance().getId());
        comment.setCommentDate(format.format(date));
        comment.setCommentApproved(1);
    }
}
