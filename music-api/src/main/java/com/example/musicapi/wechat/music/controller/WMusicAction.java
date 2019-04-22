package com.example.musicapi.wechat.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.musicapi.common.ResultUtils.Result;
import com.example.musicapi.common.ResultUtils.ResultFactory;
import com.example.musicapi.common.model.Comment;
import com.example.musicapi.common.model.Music;
import com.example.musicapi.common.model.Security;
import com.example.musicapi.wechat.music.service.WFavService;
import com.example.musicapi.wechat.music.service.WMusicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: yuanci
 * @Date: 2019/3/27
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping(value = "api/weixin/")
public class WMusicAction {

    @Resource(name = "wMusicService")
    private WMusicService wMusicService;

//    @Resource(name="commentService")
//    private CommentService commentService;

    @Resource(name = "wFavService")
    WFavService wFavService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    Security security;

    private final static Logger log = LoggerFactory.getLogger(WMusicAction.class);

    /**
     * 微信得到详细的谱子信息
     * @param id 乐谱ID
     * @param request
     * @return
     */
    @GetMapping(value = "/musicdetail")
    public Result getMusicDetail(int id, HttpServletRequest request) {
        try {
//            List<Comment> cmtList = commentService.listById(id);
            Music music = wMusicService.listMusicById(id);
            JSONObject obj = new JSONObject();
            obj.put("music",music);
//            obj.put("cmtList",cmtList);
            //判断是否加入了收藏
            String token = request.getHeader("X-Token");
            boolean isFav = false;
            if(token != null){
                String openId = redisTemplate.opsForValue().get(token).split(",")[0];
                System.out.println(openId);
                if(openId != null) {
                    List<Music> list = wFavService.listWx(openId);
                    for (Music musicFav :
                            list) {
                        if (music.getId() == musicFav.getId()) {
                            isFav = true;
                            break;
                        }
                    }
                }
            }
            obj.put("isFav",isFav);
            return ResultFactory.buildSuccessResult(obj);
        } catch (Exception e){
            log.info("小程序取得曲谱详细信息失败："+ e.getMessage());
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    /**
     * 微信用户添加收藏夹
     * @param id 乐谱ID
     * @param request
     * @return
     */
    @GetMapping(value = "/fav/link")
    public Result addFavWx(int id, HttpServletRequest request){
        try{
            String token = request.getHeader("X-Token");
            String openId = null;
            String unionId = null;
            if(token != null) {
                openId = redisTemplate.opsForValue().get(token).split(",")[0];
                unionId = redisTemplate.opsForValue().get(token).split(",")[1];
                if (openId == null) return ResultFactory.buidResult(50014, "token已过期", "");
            }
            List<Music> list = wFavService.listWx(openId);
            for (Music music:
                    list) {
                if(id == music.getId()) return ResultFactory.buildFailResult("您已经收藏此谱！");
            }
            wFavService.saveWx(openId,unionId,id);
            return ResultFactory.buildSuccessResult(id);
        }catch(Exception e){
            log.info("收藏出错："+ e );
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    /**
     * 微信用户删除收藏夹
     * @param id 乐谱ID
     * @return
     */
    @GetMapping(value="/favorites/remove")
    public Result delete(Integer id,HttpServletRequest request){
        try{
            String token = request.getHeader("X-Token");
            String openId = null;
            if(token != null) {
                openId = redisTemplate.opsForValue().get(token).split(",")[0];
            }
            wFavService.removeWx(id,openId);
            return ResultFactory.buildSuccessResult("");
        }catch(Exception e){
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }

    /**
     * 查询收藏夹
     * @param page
     * @return
     */
    @GetMapping(value="/favorites/list")
    public Result list(Integer page,Integer rows,HttpServletRequest request){
        try{
            String token = request.getHeader("X-Token");
            String openId = null;
            JSONObject list = new JSONObject();
            if(token != null) {
                list = wFavService.listByPageWx(redisTemplate.opsForValue().get(token).split(",")[0],page,rows);
            }
            return ResultFactory.buildSuccessResult(list);
        }catch(Exception e){
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }
}
