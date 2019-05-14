package com.example.muscishow.controller.music;

import com.example.muscishow.ResultUtils.Result;
import com.example.muscishow.model.Music;
import com.example.muscishow.model.PageForm;
import com.example.muscishow.server.CommonServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: yuanci
 * @Date: 2019/4/23
 * @Version: 1.0
 * @Description:
 */
@Controller
public class MusicPageAction {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    CommonServer commonServer;

    /**
     * 跳转上传谱子页面
     * @return
     */
    @GetMapping(value = "/musiccreate")
    public String toMusicCreate(){
        return "musiccreate.html";
    }
    /**
     * 跳转注册页面
     * @return
     */
    @GetMapping(value = "/register")
    public String toRegister() { return "register.html"; }
    /**
     * 跳转收藏夹页面
     * @return
     */
    @GetMapping(value = "/musicfav")
    public String toFav() { return "musicfav.html"; }

    /**
     * 分页查询谱子数据 返回首页
     * @param model
     * @return
     */
    @GetMapping(value = "/")
    public String goIndex(Model model){
        PageForm pageForm = new PageForm();
        pageForm.setPageStart(1);
        pageForm.setRows(5);
        try {
            Result result = commonServer.getMusicList(pageForm);
            model.addAttribute("musiclist",result.getData());
        }catch (Exception e){
            model.addAttribute("error","error");
        }
        return "index.html";
    }

    /**
     * show 谱子详情页面
     * @param id
     * @return
     */
    @GetMapping(value = "/musicdetail")
    public String getMusicDetail(int id, Model model){
        Result result = commonServer.getMusicDetail(id);
        model.addAllAttributes(net.sf.json.JSONObject.fromObject(result.getData()));
        return "musicdetail.html";
    }

    /**
     * 获取修改页面信息
     * @param id
     * @return
     */
    @GetMapping(value = "/modify")
    public String musicModify(int id,Model model){
        Result result = commonServer.musicModify(id);
        model.addAllAttributes(net.sf.json.JSONObject.fromObject(result.getData()));
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
        return commonServer.musicUpdate(music);
    }

    /**
     * 搜索谱子第一页并返回到页面，后续在/music/musiclist
     * @param pageStart
     * @param title
     * @param rows
     * @return
     */
    @GetMapping(value = "/musicSR")
    public String getMusicListforTitle(@RequestParam(value = "pageStart",required = false) String pageStart,
                                       @RequestParam(value = "rows",required = false) String rows,
                                       @RequestParam("title") String title,Model model){
        Result result = commonServer.getmusicSRlist(pageStart,rows,title);
        model.addAttribute("musiclist",result.getData());
        model.addAttribute("title",title);
        return "musicserch.html";
    }

    /**
     * show插入信息
     */
    @PostMapping(value = "/api/article/create")
    @ResponseBody
    public Result musicCreate(@RequestBody Music music) {
        return commonServer.musicCreate(music);
    }

}
