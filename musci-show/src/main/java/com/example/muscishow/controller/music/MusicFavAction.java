package com.example.muscishow.controller.music;

import com.example.muscishow.ResultUtils.Result;
import com.example.muscishow.server.CommonServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: yuanci
 * @Date: 2019/4/24
 * @Version: 1.0
 * @Description:
 */
@Controller
public class MusicFavAction {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    CommonServer commonServer;

    /**
     * 关联收藏夹
     * @param music_id
     * @return
     */
    @PostMapping(value="/favorites/link")
    @ResponseBody
    public Result link(@RequestParam(value = "music_id") Integer music_id){
        return commonServer.favLink(music_id);
    }

    /**
     * 查询收藏夹
     * @param page
     * @return
     */
    @GetMapping(value="/favorites/list")
    @ResponseBody
    public Result list(Integer page){
        return commonServer.favList(page);
    }

    /**
     * 删除收藏夹
     * @param music_id
     * @return
     */
    @GetMapping(value="/favorites/remove")
    @ResponseBody
    public Result delete(@RequestParam Integer music_id){
        return commonServer.favDelete(music_id);
    }
}
