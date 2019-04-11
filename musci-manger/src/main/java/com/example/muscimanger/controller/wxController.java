package com.example.muscimanger.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.muscimanger.model.Comment;
import com.example.muscimanger.model.Music;
import com.example.muscimanger.model.Security;
import com.example.muscimanger.service.CommentService;
import com.example.muscimanger.service.FavoritesService;
import com.example.muscimanger.service.MusicService;
import com.example.muscimanger.service.UserService;
import com.example.muscimanger.service.impl.TokenServiceImpl;
import com.example.muscimanger.until.*;
import nl.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yuanci
 * @Date: 2019/3/27
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping(value = "api/weixin/")
public class wxController {

    @Resource(name = "musicService")
    private MusicService musicService;

    @Resource(name="commentService")
    private CommentService commentService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "favoritesService")
    FavoritesService favoritesService;

    @Autowired
    private TokenServiceImpl tokenService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    Security security;

    private final static Logger log = LoggerFactory.getLogger(wxController.class);

    /**
     * 微信得到详细的谱子信息
     * @param id 乐谱ID
     * @param request
     * @return
     */
    @GetMapping(value = "/musicdetail")
    public Result getMusicDetail(int id, HttpServletRequest request) {
        try {
            List<Comment> cmtList = commentService.listById(id);
            Music music = musicService.listMusicById(id);
            JSONObject obj = new JSONObject();
            obj.put("music",music);
            obj.put("cmtList",cmtList);
            //判断是否加入了收藏
            String token = request.getHeader("X-Token");
            boolean isFav = false;
            if(token != null){
                String openId = redisTemplate.opsForValue().get(token).split(",")[0];
                System.out.println(openId);
                if(openId != null) {
                    List<Music> list = favoritesService.listWx(openId);
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
            List<Music> list = favoritesService.listWx(openId);
            for (Music music:
                    list) {
                if(id == music.getId()) return ResultFactory.buildFailResult("您已经收藏此谱！");
            }
            favoritesService.saveWx(openId,unionId,id);
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
            favoritesService.removeWx(id,openId);
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
                list = favoritesService.listByPageWx(redisTemplate.opsForValue().get(token).split(",")[0],page,rows);
            }
            return ResultFactory.buildSuccessResult(list);
        }catch(Exception e){
            return ResultFactory.buildFailResult(e.getMessage());
        }
    }
    /**
     * 验证微信小程序的请求域名
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/token")
    public void weChat(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("---------------------------");
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter print;
        if (isGet) {
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (signature != null && CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
                try {
                    print = response.getWriter();
                    print.write(echostr);
                    print.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    /**
     * 生成token,取得openid unionId
     * @param encryptedData
     * @param iv
     * @param code
     * @param request
     * @return
     */
    @GetMapping(value = "/decodeUserInfo")
    public Result decodeUserInfo(String encryptedData, String iv, String code,HttpServletRequest request) {

        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            return ResultFactory.buildFailResult("code 不能为空");
        }
        //授权（必填）
        String grant_type ="authorization_code";

        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + security.getWxspAppid() + "&secret=" + security.getWxspSecret() + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        log.info("\n获得会话session_key：" + session_key +"\n");
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        log.info("\n获得唯一标识openid：" + openid + "\n");
        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                net.sf.json.JSONObject userInfoJSON = net.sf.json.JSONObject.fromObject(result);
                log.info("\n开始AES解密获得unionid："+ userInfoJSON.get("unionId")+ "\n");
                //开始保存小程序用户的token
                String userKey = userInfoJSON.get("openId")+ ","+userInfoJSON.get("unionId");
                String token = tokenService.generateToken(request.getHeader("user-agent"),openid);
                tokenService.saveToken(userKey,token,request.getSession());
                log.info("\n保存微信token:"+token+",key:"+userKey);
                return ResultFactory.buildSuccessResult(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultFactory.buildFailResult("解密失败");
    }

    /**
     * 验证小程序的token是否过期
     * @param request
     * @return
     */
    @GetMapping(value = "/verifytoken")
    public Result verifyToken(HttpServletRequest request){
        String token = request.getHeader("X-Token");
        if(token != null){
            log.info("\n验证微信token："+ token);
            if(redisTemplate.opsForValue().get(token) != null) {
                return ResultFactory.buildSuccessResult("");
            }else{
                return ResultFactory.buildFailResult("token已过期");
            }
        }
        return ResultFactory.buildFailResult("token为空");
    }
}
