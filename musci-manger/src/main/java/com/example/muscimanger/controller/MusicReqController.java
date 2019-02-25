package com.example.muscimanger.controller;

import com.example.muscimanger.model.Music;
import com.example.muscimanger.model.PageForm;
import com.example.muscimanger.model.SerchBean;
import com.example.muscimanger.service.MusicService;
import com.example.muscimanger.until.Result;
import com.example.muscimanger.until.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: yuanci
 * @Date: 2019/2/25
 * @Version: 1.0
 * @Description:
 */
@Controller
@RequestMapping("/music")
public class MusicReqController {

    private final static Logger log = LoggerFactory.getLogger(MusicReqController.class);

    @Resource(name = "musicService")
    private MusicService musicService;

    @GetMapping(value = "/musiclist")
    @ResponseBody
    public Result getMusicList(PageForm PageForm){
        List<Music> list = musicService.listMusicByPar(PageForm);
        return ResultFactory.buildSuccessResult(list);
    }

    @GetMapping(value = "/musicSRlist")
    @ResponseBody
    public Result getmusicSRlist(HttpServletRequest request){
        SerchBean SB = SerchBean.getFromExt(request);
        List<Music> list = musicService.listByTitle(SB);
        return ResultFactory.buildSuccessResult(list);
    }

}
