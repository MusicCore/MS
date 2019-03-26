package com.example.muscimanger.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.muscimanger.Repository.CommonRepository;
import com.example.muscimanger.controller.MusicPreController;
import com.example.muscimanger.mapper.FavoritesMapper;
import com.example.muscimanger.model.CommonContext;
import com.example.muscimanger.model.Music;
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
        if((totalPages % rows) > 0) {
            totalPages = totalPages / rows + 1;
        }else {
            totalPages = totalPages / rows;
        }
        obj.put("totalPages",totalPages);
        return obj;
    }

    @Override
    public List<Music> list(Integer Uid) throws Exception{
        return favoritesMapper.listFavorites(Uid);
    }

    @Override
    public void remove(Integer Mid) throws Exception{
        favoritesMapper.removeFavorites(Mid, CommonContext.getInstance().getId());
        log.info("\n用户ID："+ Mid +"取消收藏：" + CommonContext.getInstance().getId() +"\n");
    }
}
