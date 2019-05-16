package com.example.musicapi.common.mapper;

import com.example.musicapi.common.model.Music;
import com.example.musicapi.common.model.PageForm;
import com.example.musicapi.common.model.SerchBean;
import com.example.musicapi.common.provider.MusicProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 谱子Mapper
 */
@Mapper
@Repository
public interface MusicMapper {
    @Insert("INSERT INTO music_score(" +
            "   id," +
            "   title," +
            "   content_music," +
            "   content_img," +
            "   content_short," +
            "   content," +
            "   author_account," +
            "   author_name," +
            "   create_time," +
            "   update_time," +
            "   last_author," +
            "   is_modify," +
            "   is_delete," +
            "   is_top," +
            "   clicks" +
            ") VALUES(" +
            "   #{id}," +
            "   #{title}," +
            "   #{contentMusic}," +
            "   #{contentImg}," +
            "   #{contentShort}," +
            "   #{content}," +
            "   #{authorAccount}," +
            "   #{authorName}," +
            "   #{createTime}," +
            "   #{updateTime}," +
            "   #{lastAuthor}," +
            "   #{isModify}," +
            "   #{isTop}," +
            "   #{isDelete}," +
            "   #{clicks}" +
            ")")
    public void save(Music param) throws Exception;

    @Select("SELECT id FROM music_score")
    public List<Integer> list() throws Exception;

    @Select("SELECT * FROM music_score WHERE id = #{id}")
    public Music listById(Integer id) throws Exception;

    @UpdateProvider(type = MusicProvider.class, method = "updateSQL")
    public void update(Music param) throws Exception;

    @Select("SELECT * FROM music_score order by id desc limit #{page.pageStart},#{page.rows}")
    public List<Music> listByPar(@Param("page") PageForm pageForm);

    @Select("SELECT COUNT(1) FROM music_score")
    public Long litsTotal();

    @SelectProvider(type = MusicProvider.class,method = "listSQL")
    public List<Music> listByTitle(SerchBean sb);

    @Delete("DELETE FROM music_score WHERE id = #{id}")
    public void deleteMusic(Integer id);
}
