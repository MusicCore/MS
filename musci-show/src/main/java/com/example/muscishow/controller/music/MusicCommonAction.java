package com.example.muscishow.controller.music;

import com.example.muscishow.ResultUtils.Result;
import com.example.muscishow.model.PageForm;
import com.example.muscishow.server.CommonServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yuanci
 * @Date: 2019/4/22
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/music")
public class MusicCommonAction {

    @Autowired
    CommonServer commonServer;

    @GetMapping("/musiclist")
    public Result getMusicListComment(PageForm pageForm){
        return commonServer.getMusicList(pageForm);
    }

    @GetMapping("/musicSRlist")
    public Result getmusicSRlist(@RequestParam("pageStart") String pageStart,@RequestParam("rows") String rows,
                                 @RequestParam("title") String title){
        return commonServer.getmusicSRlist(pageStart,rows,title);
    }
}
