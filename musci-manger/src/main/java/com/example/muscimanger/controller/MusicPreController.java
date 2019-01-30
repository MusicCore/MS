package com.example.muscimanger.controller;

import com.example.muscimanger.model.Music;
import com.example.muscimanger.model.PageForm;
import com.example.muscimanger.model.SerchBean;
import com.example.muscimanger.model.TableTagBean;
import com.example.muscimanger.service.MusicService;
import com.example.muscimanger.until.Result;
import com.example.muscimanger.until.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @GetMapping(value = "/music/musiclist")
    @ResponseBody
    public Result getMusicList(PageForm PageForm){
        List<Music> list = musicService.listMusicByPar(PageForm);
        return ResultFactory.buildSuccessResult(list);
    }

    @GetMapping(value = "/musiccreate")
    public String toMusicCreate(){
        return "musiccreate.html";
    }

    @GetMapping(value = "/music/musicSRlist")
    @ResponseBody
    public Result getmusicSRlist(HttpServletRequest request){
        SerchBean SB = SerchBean.getFromExt(request);
        List<Music> list = musicService.listByTitle(SB);
        return ResultFactory.buildSuccessResult(list);
    }

    @GetMapping(value = "/music/musicSR")
    public String getMusicListforTitle(HttpServletRequest request, @RequestParam("title") String title, PageForm pageForm, Model model){
        try {
            SerchBean SB = SerchBean.getFromExt(request);
            TableTagBean ttb = TableTagBean.getFromExt(request);
            List<Music> list = musicService.listByTitle(SB);
            model.addAttribute("musiclist",list);
            model.addAttribute("title",title);
        }catch (Exception e){
            model.addAttribute("error","error");
        }
        return "musicserch.html";
    }
    @GetMapping(value = "/music/musicdetail")
    public String getMusicDetail(Model model,int id){
        try {
            Music music = musicService.listMusicById(id);
            model.addAttribute("music",music);
        }catch (Exception e){
            log.error("getMusicDetail："+ e);
            model.addAttribute("error",e);
        }
        return "musicdetail.html";
    }

}
