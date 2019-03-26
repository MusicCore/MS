package com.example.muscimanger.mapper;

import com.example.muscimanger.model.Music;
import com.example.muscimanger.model.PageForm;
import com.example.muscimanger.model.SerchBean;
import com.example.muscimanger.provider.MusicProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@CacheConfig(cacheNames = "music")
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

    @Select("SELECT * FROM music_score")
    public List<Music> list() throws Exception;

    @Select("SELECT * FROM music_score WHERE id = #{id}")
    @Cacheable(value = "MI",key="#p0",unless="#result == null")
    public Music listById(Integer id) throws Exception;

    @UpdateProvider(type = MusicProvider.class, method = "updateSQL")
    @Caching(put = @CachePut("#p0.id"), evict = { @CacheEvict(value = "Music_Par", allEntries = true) })
    public void  update(Music param) throws Exception;

    @Select("SELECT * FROM music_score order by id desc limit #{page.pageStart},#{page.rows}")
    @Cacheable(value = "Music_Par",key = "#p0.pageStart",unless="#result == null")
    public List<Music> listByPar(@Param("page") PageForm pageForm);

    @Select("SELECT COUNT(1) FROM music_score")
    public Long litsTotal();

    @SelectProvider(type = MusicProvider.class,method = "listSQL")
    public List<Music> listByTitle(SerchBean sb);
}
