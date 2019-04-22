package com.example.musicapi.wechat.music.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.musicapi.common.model.Music;
import com.example.musicapi.common.model.PageForm;
import com.example.musicapi.wechat.music.mapper.WFavMapper;
import com.example.musicapi.wechat.music.service.WFavService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("wFavService")
public class WFavServiceImpl implements WFavService {
    @Autowired
    WFavMapper favoritesMapper;

    private final static Logger log = LoggerFactory.getLogger(WFavServiceImpl.class);

    //以下为微信接口impl
    @Override
    public void saveWx(String openId, String unionId, Integer musicId) throws Exception {
        favoritesMapper.saveWx(openId,unionId,musicId);
    }

    @Override
    public List<Music> listWx(String openId) throws Exception {
        return favoritesMapper.listFavoritesWx(openId);
    }

    @Override
    public JSONObject listByPageWx(String openId, Integer page, Integer rows) throws Exception {
        JSONObject obj = new JSONObject();
        PageForm pageForm = new PageForm(page,rows);
        obj.put("favlist",favoritesMapper.listFavoritesByPageWx(openId,pageForm.getPageStart(),pageForm.getRows()));
        int totalPageWx = favoritesMapper.listFavoritesTotalWx(openId);
        obj.put("totalPages",getTotal(totalPageWx,rows));
        return obj;
    }

    @Override
    public void removeWx(Integer musicId,String openId) throws Exception {
        favoritesMapper.removeFavoritesWx(musicId, openId);
        log.info("\n微信用户ID："+ openId +"取消收藏：" + musicId +"\n");
    }

    public int getTotal(int totalPages,int rows){
        if((totalPages % rows) > 0) {
            return totalPages / rows + 1;
        }else {
            return totalPages / rows;
        }
    }
}
