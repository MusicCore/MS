package com.example.muscimanger.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.muscimanger.model.Comment;
import com.example.muscimanger.model.Music;
import com.example.muscimanger.service.CommentService;
import com.example.muscimanger.service.FavoritesService;
import com.example.muscimanger.service.MusicService;
import com.example.muscimanger.service.UserService;
import com.example.muscimanger.until.Result;
import com.example.muscimanger.until.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: yuanci
 * @Date: 2019/3/27
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping(value = "api/weixin/")
public class wxController {

    @Resource(name = "musicService")
    private MusicService musicService;

    @Resource(name="commentService")
    private CommentService commentService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "favoritesService")
    FavoritesService favoritesService;

    private final static Logger log = LoggerFactory.getLogger(wxController.class);

    @GetMapping(value = "/musicdetail")
    public Result getMusicDetail(int id, HttpServletRequest request) {
        try {
            List<Comment> cmtList = commentService.listById(id);
            Music music = musicService.listMusicById(id);
            JSONObject obj = new JSONObject();
            obj.put("music",music);
            obj.put("cmtList",cmtList);
            //判断是否加入了收藏 TODO
            return ResultFactory.buildSuccessResult(obj);
        } catch (Exception e){
            log.info("小程序取得曲谱详细信息失败："+ e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }
}
