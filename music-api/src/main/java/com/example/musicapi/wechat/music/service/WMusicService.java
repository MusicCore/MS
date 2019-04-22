package com.example.musicapi.wechat.music.service;



import com.example.musicapi.common.model.Music;

public interface WMusicService {

    /**
     * 查询
     * @return
     * @throws Exception
     */
    public Music listMusicById(Integer id) throws Exception;
}
