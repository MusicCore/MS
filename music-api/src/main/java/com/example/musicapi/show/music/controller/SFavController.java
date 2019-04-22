package com.example.musicapi.show.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.musicapi.common.ResultUtils.Result;
import com.example.musicapi.common.ResultUtils.ResultFactory;
import com.example.musicapi.common.model.CommonContext;
import com.example.musicapi.common.model.Music;
import com.example.musicapi.show.music.service.SFavService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 收藏夹Controller
 */
@RestController
@RequestMapping("/show/favorites")
public class SFavController {

    private final static Logger log = LoggerFactory.getLogger(SFavController.class);

    @Resource(name = "sFavService")
    SFavService sFavService;

    /**
     * 关联收藏夹
     * @param music_id
     * @return
     */
    @PostMapping(value="/link")
    public Result link(@RequestParam(value = "music_id") Integer music_id){
        try{
            int user_id = CommonContext.getInstance().getId();
            List<Music> list = sFavService.list(user_id);
            for (Music music:
                    list) {
                if(music_id == music.getId()) return ResultFactory.buildFailResult("您已经收藏此谱！");
            }
            sFavService.save(user_id,music_id);
            log.info("用户id:"+ user_id + "收藏了id为:" + music_id + "谱子");
            return ResultFactory.buildSuccessResult("");
        }catch(Exception e){
            log.info("收藏出错："+ e );
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }
    /**
     * 查询收藏夹
     * @param page
     * @return
     */
    @GetMapping(value="/list")
    public Result list(Integer page){
        try{
            JSONObject list = sFavService.listByPage(CommonContext.getInstance().getId(),page,10);
            return ResultFactory.buildSuccessResult(list);
        }catch(Exception e){
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    /**
     * 删除收藏夹
     * @param music_id
     * @return
     */
    @GetMapping(value="/remove")
    public Result delete(@RequestParam Integer music_id){
        try{
            sFavService.remove(music_id);
            return ResultFactory.buildSuccessResult("");
        }catch(Exception e){
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }
}
