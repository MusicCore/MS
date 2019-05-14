package com.example.muscishow.controller.wechat;

import com.example.muscishow.ResultUtils.Result;
import com.example.muscishow.server.CommonServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

/**
 * @Author: yuanci
 * @Date: 2019/5/14
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping(value = "api/weixin/")
public class WeChatAction {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    CommonServer commonServer;

    /**
     * 微信得到详细的谱子信息
     * @param id 乐谱ID
     * @return
     */
    @GetMapping(value = "/musicdetail")
    public Result getMusicDetail(int id){
        return commonServer.getMusicDetail_wx(id);
    }

    /**
     * 微信用户添加收藏夹
     * @param id 乐谱ID
     * @return
     */
    @GetMapping(value = "/fav/link")
    public Result addFavWx(int id){
        return commonServer.addFavWx(id);
    }

    /**
     * 微信用户删除收藏夹
     * @param id 乐谱ID
     * @return
     */
    @GetMapping(value="/favorites/remove")
    public Result delete(Integer id){
        return commonServer.delete(id);
    }

    /**
     * 查询收藏夹
     * @param page
     * @return
     */
    @GetMapping(value="/favorites/list")
    public Result list(Integer page,Integer rows){
        return commonServer.list(page,rows);
    }

    /**
     * 生成token,取得openid unionId
     * @param encryptedData
     * @param iv
     * @param code
     * @return
     */
    @GetMapping(value = "/decodeUserInfo")
    public Result decodeUserInfo(String encryptedData, String iv, String code){
        //+号等字符会被转义，此处先base64加密
        final Base64.Encoder encoder = Base64.getEncoder();
        try{
            return commonServer.decodeUserInfo(encoder.encodeToString(encryptedData.getBytes("UTF-8")),
                    encoder.encodeToString(iv.getBytes("UTF-8")),
                    code);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 验证小程序的token是否过期
     * @return
     */
    @GetMapping(value = "/verifytoken")
    public Result verifyToken(){
        return commonServer.verifyToken();
    }
}
