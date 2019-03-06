package com.example.muscimanger.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.muscimanger.model.*;
import com.example.muscimanger.service.MusicService;
import com.example.muscimanger.until.Result;
import com.example.muscimanger.until.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MusicPreController {

    private final static Logger log = LoggerFactory.getLogger(MusicPreController.class);

    @Resource(name = "musicService")
    private MusicService musicService;

    @GetMapping(value = "/")
    public String goIndex(Model model, PageForm pageForm){
        pageForm.setPageStart(1);
        pageForm.setRows(5);
        try {
            List<Music> list = musicService.listMusicByPar(pageForm);
            model.addAttribute("musiclist",list);
        }catch (Exception e){
            model.addAttribute("error","error");
        }
        return "index.html";
    }


    @GetMapping(value = "/musiccreate")
    public String toMusicCreate(){
        return "musiccreate.html";
    }

    @GetMapping(value = "/register")
    public String toRegister() { return "register.html"; }


    @GetMapping(value = "/musicdetail")
    public String getMusicDetail(Model model, int id){
        try {
            Comment comment = new Comment(1,2,"张三","noway","/static/img/body-img/other/tx.jpg","2019-3-5 00","我是评论1",1, 0,12);
            Comment comment1 = new Comment(1,2,"李四","noway","/static/img/body-img/other/tx.jpg","2019-3-5 00","我是评论2，父级评论是1",1, 1,12);
            Comment comment2 = new Comment(1,2,"王五","noway","/static/img/body-img/other/tx.jpg","2019-3-5 00","我是评论1",1, 0,12);
            List<Comment> cmtList = new ArrayList<>();
            cmtList.add(comment);cmtList.add(comment1);cmtList.add(comment2);
            Music music = musicService.listMusicById(id);
            model.addAttribute("music",music);
            model.addAttribute("cmtList",cmtList);
        }catch (Exception e){
            log.error("getMusicDetail："+ e);
            model.addAttribute("error",e);
        }
        return "musicdetail.html";
    }

    @GetMapping(value = "/musicSR")
    public String getMusicListforTitle(HttpServletRequest request, @RequestParam("title") String title, PageForm pageForm, Model model){
        try {
//            title = URLDecoder.decode(title,"utf-8");
            SerchBean SB = SerchBean.getFromExt(request);
            TableTagBean ttb = TableTagBean.getFromExt(request);
            List<Music> list = musicService.listByTitle(SB);
            model.addAttribute("musiclist",list);
            model.addAttribute("title",title);
        }catch (Exception e){
            model.addAttribute("error",e);
        }
        return "musicserch.html";
    }


    /**
     * 获取修改页面信息
     * @param id
     * @return
     */
    @GetMapping(value = "/modify")
    public String musicModify(int id,Model model) {
        log.info("------------method:musicModify-------------");
        try {
            Music music = musicService.listMusicById(id);
            model.addAttribute("ms",music);
        }catch (Exception e) {
            log.info("简谱查询错误："+e.getMessage());
            model.addAttribute("error",e);
        }
        return "musicmodify.html";
    }

    /**
     * 更新
     * @param music
     * @return
     */
    @PostMapping(value = "/musicupdate")
    @ResponseBody
    public Result musicUpdate(@RequestBody Music music){
        log.info("------------method:musicUpdate-------------");
        try {
            Music musicOrigin =  musicService.listMusicById(music.getId());
            music.setAuthorAccount(musicOrigin.getAuthorAccount());
            music.setIsModify(musicOrigin.getIsModify());
            musicService.updateMusicInfoById(music);
            return ResultFactory.buildSuccessResult(music.getId());
        }catch (Exception e){
            log.info("简谱更新错误："+e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

}
