package com.example.muscimanger.service;

import com.example.muscimanger.model.Music;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FavoritesService {
    public void save(Integer Uid,Integer Mid) throws Exception;

    public List<Music> list(Integer Uid) throws Exception;

    public void remove(Integer Mid) throws Exception;
}
