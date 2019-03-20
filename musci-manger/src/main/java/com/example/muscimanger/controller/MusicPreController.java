package com.example.muscimanger.controller;

import com.example.muscimanger.model.*;
import com.example.muscimanger.service.MusicService;
import com.example.muscimanger.service.CommentService;
import com.example.muscimanger.until.Result;
import com.example.muscimanger.until.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MusicPreController {

    private final static Logger log = LoggerFactory.getLogger(MusicPreController.class);

    @Resource(name = "musicService")
    private MusicService musicService;
    @Resource(name="commentService")
    private CommentService commentService;

    @PostMapping(value="/comment")
    public Result toComment(Model model,@RequestBody Comment comment){
        try {
            commentService.save(comment);
            return ResultFactory.buildSuccessResult("");
        }catch (Exception e){
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    @PostMapping(value="/rComment")
    public Result removerComment(Model model,Integer id){
        try {
            commentService.remove(id);
            return ResultFactory.buildSuccessResult("");
        }catch (Exception e){
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

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
            List<Comment> cmtList = commentService.listById(id);
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
