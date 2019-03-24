package com.example.muscishow.service;


import com.example.muscishow.model.Music;
import com.example.muscishow.model.PageForm;
import com.example.muscishow.model.SerchBean;

import java.util.List;

public interface MusicService {


    /**
     * 查询
     * @return
     * @throws Exception
     */
    public Music listMusicById(Integer id) throws Exception;

    /**
     * 通过分页查询（其他参数待加）
     * @param pageForm
     * @return
     */
    public List<Music>  listMusicByPar(PageForm pageForm);

    /**
     * 搜索(条件)
     * @param pageForm
     * @return
     */
    public List<Music>  listByTitle(SerchBean sb);

}
