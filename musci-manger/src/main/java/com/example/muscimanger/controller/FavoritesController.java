package com.example.muscimanger.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.muscimanger.model.CommonContext;
import com.example.muscimanger.model.Music;
import com.example.muscimanger.service.FavoritesService;
import com.example.muscimanger.until.Result;
import com.example.muscimanger.until.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class FavoritesController {

    private final static Logger log = LoggerFactory.getLogger(FavoritesController.class);

    @Resource(name = "favoritesService")
    FavoritesService favoritesService;

    /**
     * 关联收藏夹
     * @param music_id
     * @return
     */
    @PostMapping(value="/favorites/link")
    @ResponseBody
    public Result link(@RequestParam(value = "music_id") Integer music_id){
        try{
            int user_id = CommonContext.getInstance().getId();
            List<Music> list = favoritesService.list(user_id);
            for (Music music:
                    list) {
                if(music_id == music.getId()) return ResultFactory.buildFailResult("您已经收藏此谱！");
            }
            favoritesService.save(user_id,music_id);
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
    @GetMapping(value="/favorites/list")
    @ResponseBody
    public Result list(Integer page){
        try{
            JSONObject list = favoritesService.listByPage(CommonContext.getInstance().getId(),page,10);
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
    @GetMapping(value="/favorites/remove")
    @ResponseBody
    public Result delete(@RequestParam Integer music_id){
        try{
            favoritesService.remove(music_id);
            return ResultFactory.buildSuccessResult("");
        }catch(Exception e){
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }
}
