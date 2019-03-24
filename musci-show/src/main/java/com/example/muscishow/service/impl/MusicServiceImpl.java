package com.example.muscishow.service.impl;


import com.example.muscishow.mapper.MusicMapper;
import com.example.muscishow.model.Music;
import com.example.muscishow.model.PageForm;
import com.example.muscishow.model.SerchBean;
import com.example.muscishow.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("musicService")
public class MusicServiceImpl implements MusicService {
    @Autowired
    protected MusicMapper musicMapper;


    @Override
    public Music listMusicById(Integer id) throws Exception {
        Music music = musicMapper.listById(id);
        return music;
    }

    @Override
    public List<Music> listMusicByPar(PageForm pageForm) {
        return musicMapper.listByPar(pageForm);
    }

    @Override
    public List<Music> listByTitle(SerchBean sb) {
        String title =sb.getCondition().getAsStringEmptyNull("title");
        title= title+'%';
        return musicMapper.listByTitle(sb);
    }
}
