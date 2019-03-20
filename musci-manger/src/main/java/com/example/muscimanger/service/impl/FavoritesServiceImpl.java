package com.example.muscimanger.service.impl;

import com.example.muscimanger.Repository.CommonRepository;
import com.example.muscimanger.mapper.FavoritesMapper;
import com.example.muscimanger.model.Music;
import com.example.muscimanger.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("favoritesService")
public class FavoritesServiceImpl implements FavoritesService{
    @Autowired
    FavoritesMapper favoritesMapper;

    @Override
    public void save(Integer Uid,Integer Mid) throws Exception{
        favoritesMapper.save(Uid,Mid);
    }

    @Override
    public List<Music> list(Integer Uid) throws Exception{
        return favoritesMapper.listFavorites(Uid);
    }

    @Override
    public void remove(Integer Mid) throws Exception{
        favoritesMapper.removeFavorites(Mid);
    }
}
