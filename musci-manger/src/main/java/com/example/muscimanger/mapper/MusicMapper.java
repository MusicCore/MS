package com.example.muscimanger.mapper;

import com.example.muscimanger.model.Music;
import com.example.muscimanger.model.PageForm;
import com.example.muscimanger.model.SerchBean;
import com.example.muscimanger.provider.MusicProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 谱子Mapper
 */
@Mapper
@Repository
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
    @CachePut(key = "'MI'+#p0.id")
    public void save(Music param) throws Exception;

    @Select("SELECT id FROM music_score")
    public List<Integer> list() throws Exception;

    @Select("SELECT * FROM music_score WHERE id = #{id}")
    @Cacheable(key="'MI'+#p0",unless="#result == null")
    public Music listById(Integer id) throws Exception;

    /**
     * 在支持Spring Cache的环境下，对于使用@Cacheable标注的方法，Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，
     * 如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。@CachePut也可以声明
     * 一个方法支持缓存功能。与@Cacheable不同的是使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是
     * 每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
     * Cacheable注解负责将方法的返回值加入到缓存中
     * @CacheEvict CacheEvict注解负责清除缓存(它的三个参数与@Cacheable的意思是一样的)
     * @param param
     * @throws Exception
     */
    @UpdateProvider(type = MusicProvider.class, method = "updateSQL")
    @CachePut(key = "'MI'+#p0.id")
    //@Caching(put = @CachePut("#p0.id"), evict = { @CacheEvict(value = "Music_Par", allEntries = true) })
    public void  update(Music param) throws Exception;

    @Select("SELECT * FROM music_score order by id desc limit #{page.pageStart},#{page.rows}")
    //@Cacheable(value = "Music_Par",key = "#p0.pageStart",unless="#result == null")
    public List<Music> listByPar(@Param("page") PageForm pageForm);

    @Select("SELECT COUNT(1) FROM music_score")
    public Long litsTotal();

    @SelectProvider(type = MusicProvider.class,method = "listSQL")
    public List<Music> listByTitle(SerchBean sb);
}
