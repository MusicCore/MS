package com.example.muscishow.mapper;


import com.example.muscishow.model.Music;
import com.example.muscishow.model.PageForm;
import com.example.muscishow.model.SerchBean;
import com.example.muscishow.provider.MusicProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MusicMapper {

    @Select("SELECT * FROM music_score WHERE id = #{id}")
    public Music listById(Integer id) throws Exception;

    @Select("SELECT * FROM music_score limit #{page.pageStart},#{page.rows}")
    public List<Music> listByPar(@Param("page") PageForm pageForm);

    @SelectProvider(type = MusicProvider.class,method = "listSQL")
    public List<Music> listByTitle(SerchBean sb);
}
