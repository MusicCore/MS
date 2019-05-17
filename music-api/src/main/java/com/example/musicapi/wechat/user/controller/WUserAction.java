package com.example.musicapi.wechat.user.controller;

import com.example.musicapi.common.ResultUtils.Result;
import com.example.musicapi.common.ResultUtils.ResultFactory;
import com.example.musicapi.common.model.Security;
import com.example.musicapi.common.until.AesCbcUtil;
import com.example.musicapi.common.until.CheckoutUtil;
import com.example.musicapi.common.until.HttpRequest;
import com.example.musicapi.wechat.user.service.WUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * @Author: yuanci
 * @Date: 2019/3/27
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping(value = "api/weixin/")
public class WUserAction {

    @Autowired
    private WUserService wUserService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    Security security;

    private final static Logger log = LoggerFactory.getLogger(WUserAction.class);

    /**
     * 验证微信小程序的请求域名
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/token")
    public void weChat(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
    @PostMapping(value = "/decodeUserInfo")
    public Result decodeUserInfo(@RequestParam("encryptedData") String encryptedData,
                                 @RequestParam("iv") String iv, @RequestParam("code") String code, HttpServletRequest request) {

        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            return ResultFactory.buildFailResult("code 不能为空");
        }
        //授权（必填）
        String grant_type ="authorization_code";

        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + security.getWxspAppid() + "&secret=" + security.getWxspSecret() + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求  https://api.weixin.qq.com/sns/jscode2session
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
            final Base64.Decoder decoder = Base64.getDecoder();
            String result = AesCbcUtil.decrypt(new String(decoder.decode(encryptedData),"UTF-8"),
                    session_key, new String(decoder.decode(iv),"UTF-8"), "UTF-8");
            if (null != result && result.length() > 0) {
                net.sf.json.JSONObject userInfoJSON = net.sf.json.JSONObject.fromObject(result);
                log.info("\n开始AES解密获得unionid："+ userInfoJSON.get("unionId")+ "\n");
                //开始保存小程序用户的token
                String userKey = userInfoJSON.get("openId")+ ","+userInfoJSON.get("unionId");
                String token = wUserService.generateToken(request.getHeader("user-agent"),openid);
                wUserService.saveToken(userKey,token,request.getSession());
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
    @PostMapping(value = "/verifytoken")
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
