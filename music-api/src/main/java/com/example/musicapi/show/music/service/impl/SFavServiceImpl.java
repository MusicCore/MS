package com.example.musicapi.show.music.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.musicapi.common.model.CommonContext;
import com.example.musicapi.common.model.Music;
import com.example.musicapi.show.music.mapper.SFavMapper;
import com.example.musicapi.show.music.service.SFavService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sFavService")
public class SFavServiceImpl implements SFavService {
    @Autowired
    SFavMapper sFavMapper;

    private final static Logger log = LoggerFactory.getLogger(SFavServiceImpl.class);

    @Override
    public void save(Integer Uid,Integer Mid) throws Exception{
        sFavMapper.save(Uid,Mid);
    }

    @Override
    public JSONObject listByPage(Integer Uid, Integer page, Integer rows) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("favlist",sFavMapper.listFavoritesByPage(Uid,(page-1)*rows,rows));
        int totalPages = sFavMapper.listFavoritesTotal(Uid);
        obj.put("totalPages",getTotal(totalPages,rows));
        return obj;
    }

    @Override
    public List<Music> list(Integer Uid) throws Exception{
        return sFavMapper.listFavorites(Uid);
    }

    @Override
    public void remove(Integer Mid) throws Exception{
        sFavMapper.removeFavorites(Mid, CommonContext.getInstance().getId());
        log.info("\n用户ID："+ CommonContext.getInstance().getId() +"取消收藏：" + Mid +"\n");
    }


    public int getTotal(int totalPages,int rows){
        if((totalPages % rows) > 0) {
            return totalPages / rows + 1;
        }else {
            return totalPages / rows;
        }
    }
}
