package com.example.muscishow.controller.vue;

import com.example.muscishow.ResultUtils.Result;
import com.example.muscishow.model.Music;
import com.example.muscishow.model.PageForm;
import com.example.muscishow.server.CommonServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yuanci
 * @Date: 2019/5/16
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/vue/music")
public class VMusicAction {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    CommonServer commonServer;

    /**
     * vue获取修改页面信息
     * @param id
     * @return
     */
    @GetMapping(value = "/detail")
    public Result musicEidt_vue(@RequestParam("id") Integer id){
        return commonServer.musicEidt_vue(id);
    }

    /**
     * vue更新
     * @param music
     * @return
     */
    @PostMapping(value = "/update")
    public Result musicUpdate_vue(@RequestBody Music music){
        return commonServer.musicUpdate_vue(music);
    }

    /**
     * vue获取信息
     * @param pageForm
     * @return
     */
    @GetMapping(value = "/list")
    public Result musicList_vue(PageForm pageForm){
        return commonServer.musicList_vue(pageForm);
    }

    /**
     * vue插入信息
     */
    @PostMapping(value = "/create")
    public Result musicCreate_vue(@RequestBody Music music){
        return commonServer.musicCreate_vue(music);
    }

    /**
     * 简谱删除
     * @param id
     * @return
     */
    @PostMapping(value = "/musicDelete")
    public Result musicDelete(@RequestParam Integer id){
        return commonServer.musicDelete(id);
    }
}
