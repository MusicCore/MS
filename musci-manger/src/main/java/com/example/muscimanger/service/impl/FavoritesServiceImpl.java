package com.example.muscimanger.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.muscimanger.Repository.CommonRepository;
import com.example.muscimanger.controller.MusicPreController;
import com.example.muscimanger.mapper.FavoritesMapper;
import com.example.muscimanger.model.CommonContext;
import com.example.muscimanger.model.Music;
import com.example.muscimanger.model.PageForm;
import com.example.muscimanger.service.FavoritesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("favoritesService")
public class FavoritesServiceImpl implements FavoritesService{
    @Autowired
    FavoritesMapper favoritesMapper;

    private final static Logger log = LoggerFactory.getLogger(FavoritesServiceImpl.class);

    @Override
    public void save(Integer Uid,Integer Mid) throws Exception{
        favoritesMapper.save(Uid,Mid);
    }

    @Override
    public JSONObject listByPage(Integer Uid, Integer page, Integer rows) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("favlist",favoritesMapper.listFavoritesByPage(Uid,(page-1)*rows,rows));
        int totalPages = favoritesMapper.listFavoritesTotal(Uid);
        obj.put("totalPages",getTotal(totalPages,rows));
        return obj;
    }

    @Override
    public List<Music> list(Integer Uid) throws Exception{
        return favoritesMapper.listFavorites(Uid);
    }

    @Override
    public void remove(Integer Mid) throws Exception{
        favoritesMapper.removeFavorites(Mid, CommonContext.getInstance().getId());
        log.info("\n用户ID："+ CommonContext.getInstance().getId() +"取消收藏：" + Mid +"\n");
    }

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
