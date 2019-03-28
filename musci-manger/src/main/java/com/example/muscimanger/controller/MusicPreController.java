package com.example.muscimanger.controller;

import com.example.muscimanger.dto.UserDto;
import com.example.muscimanger.model.*;
import com.example.muscimanger.service.FavoritesService;
import com.example.muscimanger.service.MusicService;
import com.example.muscimanger.service.CommentService;
import com.example.muscimanger.service.UserService;
import com.example.muscimanger.until.Result;
import com.example.muscimanger.until.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 前端Controller
 */
@Controller
public class MusicPreController {

    private final static Logger log = LoggerFactory.getLogger(MusicPreController.class);

    @Resource(name = "musicService")
    private MusicService musicService;
    @Resource(name="commentService")
    private CommentService commentService;
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "favoritesService")
    FavoritesService favoritesService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 添加评论
     * @param model
     * @param comment
     * @return
     */
    @PostMapping(value="/comment")
    @ResponseBody
    public Result toComment(Model model,@RequestBody Comment comment){
        try {
            initComment(comment);
            commentService.save(comment);
            log.info("\n账号："+comment.getCommentAuthorAccount()+",用户："+ comment.getCommentAuthorName()+"添加评论\n");
            return ResultFactory.buildSuccessResult("");
        }catch (Exception e){
            log.error("\n添加评论出错："+e+"\n");
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    /**
     * 移除评论
     * @param model
     * @param id
     * @return
     */
    @PostMapping(value="/rComment")
    @ResponseBody
    public Result removerComment(Model model, Integer id){
        try {
            commentService.remove(id);
            log.info("移除id为："+ id +"的评论");
            return ResultFactory.buildSuccessResult("");
        }catch (Exception e){
            log.error("移除id为："+ id +"的评论 出错！");
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    /**
     * 分页查询谱子数据 返回首页
     * @param model
     * @param pageForm
     * @return
     */
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
     * 谱子详情页面
     * @param model
     * @param id
     * @param request
     * @return
     */
    @GetMapping(value = "/musicdetail")
    public String getMusicDetail(Model model, int id,HttpServletRequest request){

        try {
            List<Comment> cmtList = commentService.listById(id);
            Music music = musicService.listMusicById(id);
            model.addAttribute("music",music);
            model.addAttribute("cmtList",cmtList);
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
                    List<Music> list = favoritesService.list(dto.getId());
                    for (Music musicFav:
                            list) {
                        if(music.getId() == musicFav.getId()) isFav = true;
                    }
                }
            }
            model.addAttribute("showModify",showModify);
            model.addAttribute("isFav",isFav);
            //因为此url被设定为不拦截url,所以在这里拿role,此代码块只针对登录了的用户所作
        }catch (Exception e){
            log.error("getMusicDetail："+ e);
            model.addAttribute("error",e);
        }
        return "musicdetail.html";
    }

    /**
     * 搜索谱子
     * @param request
     * @param title
     * @param pageForm
     * @param model
     * @return
     */
    @GetMapping(value = "/musicSR")
    public String getMusicListforTitle(HttpServletRequest request, @RequestParam("title") String title, PageForm pageForm, Model model){
        try {
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
//            music.setIsModify(musicOrigin.getIsModify());
            musicService.updateMusicInfoById(music,musicOrigin.getIsModify());
            return ResultFactory.buildSuccessResult(music.getId());
        }catch (Exception e){
            log.info("简谱更新错误："+e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    /**
     * 装填数据
     * @param comment
     */
    public void initComment(Comment comment){
        //默认系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Date date = new Date();
        comment.setCommentAuthorAccount(CommonContext.getInstance().getAccount());
        comment.setCommentAuthorAvatar(CommonContext.getInstance().getAvatar());
        comment.setCommentAuthorName(CommonContext.getInstance().getName());
        comment.setUserId(CommonContext.getInstance().getId());
        comment.setCommentDate(format.format(date));
        comment.setCommentApproved(1);
    }
}
