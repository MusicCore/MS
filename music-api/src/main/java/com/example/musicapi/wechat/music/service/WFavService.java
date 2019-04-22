package com.example.musicapi.wechat.music.service;

import com.alibaba.fastjson.JSONObject;
import com.example.musicapi.common.model.Music;

import java.util.List;

/**
 * 收藏夹
 */
public interface WFavService {

    /**
     * 关联用户id和谱子id  微信接口
     * @param openId
     * @param unionId
     * @param musicId
     * @throws Exception
     */
    public void saveWx(String openId, String unionId, Integer musicId) throws Exception;

    /**
     * 根据openId查询
     * @param openId
     * @return
     * @throws Exception
     */
    public List<Music> listWx(String openId) throws Exception;

    /**
     * 根据id分页查询
     * @param openId
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    public JSONObject listByPageWx(String openId, Integer page, Integer rows) throws Exception;

    /**
     * 删除
     * @param
     * @throws Exception
     */
    public void removeWx(Integer musicId, String openId) throws Exception;
}
