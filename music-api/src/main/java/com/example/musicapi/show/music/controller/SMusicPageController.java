package com.example.musicapi.show.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.musicapi.common.ResultUtils.Result;
import com.example.musicapi.common.ResultUtils.ResultFactory;
import com.example.musicapi.common.dto.UserDto;
import com.example.musicapi.common.model.*;
import com.example.musicapi.common.service.MusicService;
import com.example.musicapi.common.service.UserService;
import com.example.musicapi.show.comment.service.CommentService;
import com.example.musicapi.show.music.service.impl.SFavServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 前端Controller
 */
@RestController
@RequestMapping(value = "/show")
public class SMusicPageController {

    private final static Logger log = LoggerFactory.getLogger(SMusicPageController.class);

    @Resource(name = "musicService")
    private MusicService musicService;
    @Resource(name="commentService")
    private CommentService commentService;
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "sFavService")
    SFavServiceImpl sFavServiceImpl;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * show插入信息
     */
    @PostMapping(value = "/create")
    public Result musicCreate(@RequestBody Music music) {
        log.info("------------method:musicCreate-------------");
        try{
            insData(music);
            musicService.saveMusic(music);
            return ResultFactory.buildSuccessResult("");
        }catch (Exception e){
            log.info("简谱上传存入错误："+e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    /**
     * show 分页查询谱子数据 返回首页
     * 放弃使用
     * @param pageForm
     * @return
     */
//    @GetMapping(value = "/")
    public Result goIndex(PageForm pageForm){
        pageForm.setPageStart(1);
        pageForm.setRows(5);
        List<Music> list;
        try {
            list = musicService.listMusicByPar(pageForm);
//            model.addAttribute("musiclist",list);
            return ResultFactory.buildSuccessResult(new JSONObject().put("musiclist",list));
        }catch (Exception e){
//            model.addAttribute("error","error");
            return ResultFactory.buildFailResult("error");
        }
//        return "index.html";
    }

    /**
     * show 谱子详情页面
     * @param model
     * @param id
     * @param request
     * @return
     */
    @GetMapping(value = "/musicdetail")
    public Result getMusicDetail(int id,HttpServletRequest request){

        JSONObject obj = new JSONObject();
        try {
            List<Comment> cmtList = commentService.listById(id);
            Music music = musicService.listMusicById(id);
            obj.put("music",music);
            obj.put("cmtList",cmtList);
            int showModify = 0;
            //因为此url被设定为不拦截url,所以在这里拿role,并判断用户是否收藏了此谱,此代码块只针对登录了的用户所作
            Cookie[] cookies=request.getCookies();
            String token = null;
            boolean isFav = false;
            for (Cookie cookie : cookies) {
                switch(cookie.getName()){
                    default:
                        break;
                    case "token":
                        token = URLDecoder.decode(cookie.getValue(),"utf-8");
                        break;
                }
            }
            if(token != null){
                if(music.getIsModify() == 1 ) showModify = 1;
                if(redisTemplate.opsForValue().get(token) != null) {
                    String uid = redisTemplate.opsForValue().get(token);
                    UserDto dto = userService.selectUserById(uid);
                    if (dto.getAccount().equals(music.getAuthorAccount()) || dto.getRoles().equals("superadmin")) showModify = 1;
                    List<Music> list = sFavServiceImpl.list(dto.getId());
                    for (Music musicFav:
                            list) {
                        if(music.getId() == musicFav.getId()) {
                            isFav = true;
                            break;
                        }
                    }
                }
            }
            obj.put("showModify",showModify);
            obj.put("isFav",isFav);
            //因为此url被设定为不拦截url,所以在这里拿role,此代码块只针对登录了的用户所作
        }catch (Exception e){
            log.error("getMusicDetail："+ e);
            return ResultFactory.buildFailResult(e.getMessage());
        }
//        return "musicdetail.html";
        return ResultFactory.buildSuccessResult(obj);
    }

    /**
     * show 搜索谱子
     * 放弃使用
     * 由于初始
     * @param request
     * @param title
     * @param pageForm
     * @param model
     * @return
     */
//    @GetMapping(value = "/musicSR")
    public Result getMusicListforTitle(HttpServletRequest request, @RequestParam("title") String title, PageForm pageForm, Model model){
        try {
            SerchBean SB = SerchBean.getFromExt(request);
            TableTagBean ttb = TableTagBean.getFromExt(request);
            List<Music> list = musicService.listByTitle(SB);
            JSONObject obj = new JSONObject();
            obj.put("musiclist",list);
            obj.put("title",title);
//            model.addAttribute("musiclist",list);
//            model.addAttribute("title",title);
            return ResultFactory.buildSuccessResult(obj);
        }catch (Exception e){
//            model.addAttribute("error",e);
            log.error("/musicSR出错：" + e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
//        return "musicserch.html";
    }
    /**
     * show 获取修改页面信息
     * @param id
     * @return
     */
    @GetMapping(value = "/modify")
    public Result musicModify(int id,Model model) {
        log.info("------------method:musicModify-------------");
        try {
            Music music = musicService.listMusicById(id);
            return ResultFactory.buildSuccessResult(new JSONObject().put("ms",music));
//            model.addAttribute("ms",music);
        }catch (Exception e) {
            log.info("简谱查询错误："+e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
//            model.addAttribute("error",e);
        }
//        return "musicmodify.html";
    }

    /**
     * show 更新曲谱
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
//            music.setIsModify(musicOrigin.getIsModify());
            musicService.updateMusicInfoById(music,musicOrigin.getIsModify());
            return ResultFactory.buildSuccessResult(music.getId());
        }catch (Exception e){
            log.info("简谱更新错误："+e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    /**
     * music数据装载
     * @param music
     */
    public Music insData(Music music){
        //默认系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Date date = new Date();
        if(null==music.getAuthorAccount())
            music.setAuthorAccount(CommonContext.getInstance().getAccount());
        if(null==music.getAuthorName())
            music.setAuthorName(CommonContext.getInstance().getName());
        if(null==music.getContentImg())
            music.setContentImg("/");
        if(null==music.getCreateTime())
            music.setCreateTime(format.format(date));
        if(null==music.getContentShort())
            music.setContentShort("");
        return music;
    }

}
