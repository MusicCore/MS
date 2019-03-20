package com.example.muscimanger.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.muscimanger.model.Music;
import com.example.muscimanger.service.FavoritesService;
import com.example.muscimanger.until.Result;
import com.example.muscimanger.until.ResultFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class FavoritesController {
    @Resource(name = "favoritesService")
    FavoritesService favoritesService;

    /**
     * 关联收藏夹
     * @param user_id
     * @param music_id
     * @return
     */
    @PostMapping(value="/favorites/link")
    public Result link(@RequestParam Integer user_id, @RequestParam Integer music_id){
        try{
            favoritesService.save(user_id,music_id);
            return ResultFactory.buildSuccessResult("");
        }catch(Exception e){
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }
    /**
     * 查询收藏夹
     * @param user_id
     * @return
     */
    @GetMapping(value="/favorites/list")
    public Result list(@RequestParam Integer user_id){
        try{
            List<Music> list = favoritesService.list(user_id);
            JSONObject object = new JSONObject();
            object.put("favorites",list);
            return ResultFactory.buildSuccessResult(object);
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
    public Result delete(@RequestParam Integer music_id){
        try{
            favoritesService.remove(music_id);
            return ResultFactory.buildSuccessResult("");
        }catch(Exception e){
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }
}
