package com.example.musicapi.wechat.music.service.impl;

import com.example.musicapi.common.model.Music;
import com.example.musicapi.wechat.music.mapper.WMusicMapper;
import com.example.musicapi.wechat.music.service.WMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("wMusicService")
public class WMusicServiceImpl implements WMusicService {
    @Autowired
    protected WMusicMapper wMusicMapper;

    @Override
    public Music listMusicById(Integer id) throws Exception {
        return wMusicMapper.listById(id);
    }
}
