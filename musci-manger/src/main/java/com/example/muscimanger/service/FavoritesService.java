package com.example.muscimanger.service;

import com.alibaba.fastjson.JSONObject;
import com.example.muscimanger.model.Music;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收藏夹
 */
public interface FavoritesService {
    /**
     * 关联用户id和谱子id
     * @param Uid
     * @param Mid
     * @throws Exception
     */
    public void save(Integer Uid,Integer Mid) throws Exception;

    /**
     * 根据user_id查询
     * @param Uid
     * @return
     * @throws Exception
     */
    public List<Music> list(Integer Uid) throws Exception;

    /**
     * 根据id分页查询
     * @param Uid
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    public JSONObject listByPage(Integer Uid, Integer page, Integer rows) throws Exception;

    /**
     * 删除
     * @param id
     * @throws Exception
     */
    public void remove(Integer id) throws Exception;

    /**
     * 关联用户id和谱子id  微信接口
     * @param openId
     * @param unionId
     * @param musicId
     * @throws Exception
     */
    public void saveWx(String openId,String unionId,Integer musicId) throws Exception;

    public List<Music> listWx(String openId) throws Exception;

    public JSONObject listByPageWx(String openId, Integer page, Integer rows) throws Exception;

    public void removeWx(Integer musicId,String openId) throws Exception;
}
