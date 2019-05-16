package com.example.musicapi.common.controller;

import com.example.musicapi.common.ResultUtils.Result;
import com.example.musicapi.common.ResultUtils.ResultFactory;
import com.example.musicapi.common.model.Music;
import com.example.musicapi.common.model.PageForm;
import com.example.musicapi.common.model.SerchBean;
import com.example.musicapi.show.music.service.MusicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * wechat show共用
 *
 * @Author: yuanci
 * @Date: 2019/2/25
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/music")
public class MusicCommentController {

    private final static Logger log = LoggerFactory.getLogger(MusicCommentController.class);

    @Resource(name = "musicService")
    private MusicService musicService;

    @PostMapping(value = "/musiclist")
    public Result getMusicList(@RequestBody PageForm PageForm){
        List<Music> list = musicService.listMusicByPar(PageForm);
        return ResultFactory.buildSuccessResult(list);
    }

    @PostMapping(value = "/musicSRlist")
    public Result getmusicSRlist(HttpServletRequest request){
        SerchBean SB = SerchBean.getFromExt(request);
        List<Music> list = musicService.listByTitle(SB);
        return ResultFactory.buildSuccessResult(list);
    }

}
