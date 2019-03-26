package com.example.muscimanger.service.impl;

import com.example.muscimanger.mapper.MusicMapper;
import com.example.muscimanger.model.CommonContext;
import com.example.muscimanger.model.Music;
import com.example.muscimanger.model.PageForm;
import com.example.muscimanger.model.SerchBean;
import com.example.muscimanger.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@Service("musicService")
public class MusicServiceImpl implements MusicService {
    @Autowired
    protected MusicMapper musicMapper;

    @Override
    public void saveMusic(Music music) throws Exception {
        musicMapper.save(music);
    }

    @Override
    public List<Music> listMusic() throws Exception {
        List<Music> list = musicMapper.list();
        return list;
    }

    @Override
    public Music listMusicById(Integer id) throws Exception {
        Music music = musicMapper.listById(id);
        return music;
    }

    @Override
    public void updateMusicInfoById(Music music,int isModify) throws Exception {
        if (isModify ==  1 || music.getAuthorAccount().equals(CommonContext.getInstance().getAccount())){
            //非简谱创建者想把简谱设定为不可修改时拦截此操作，设置成原始状态
            if (! CommonContext.getInstance().getAccount().equals(music.getAuthorAccount())) music.setIsModify(isModify);
            music.setLastAuthor(CommonContext.getInstance().getName());
            musicMapper.update(music);
        }else {
            throw new Exception("无权更新");
        }
    }

    @Override
    public List<Music> listMusicByPar(PageForm pageForm) {
        return musicMapper.listByPar(pageForm);
    }

    @Override
    public Long listTotal() {
        return musicMapper.litsTotal();
    }

    @Override
    public List<Music> listByTitle(SerchBean sb) {
        String title =sb.getCondition().getAsStringEmptyNull("title");
        try {
            title = URLEncoder.encode(title, "utf-8") + '%';
            title = URLDecoder.decode(title, "utf-8") + '%';
        }catch (Exception e){

        }
        return musicMapper.listByTitle(sb);
    }
}
