package com.example.muscimanger.mapper;

import com.example.muscimanger.model.Music;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收藏夹Mapper
 */
@Repository
@Mapper
public interface FavoritesMapper {
    @Insert("Insert INTO favorites(user_id,music_id) VALUES(#{Uid},#{Mid})")
    public void save(Integer Uid,Integer Mid) throws Exception;

    @Select("select * from music_score mc where mc.id in (select f.music_id from favorites f where f.user_id = #{id})")
    public List<Music> listFavorites(Integer id) throws Exception;

    @Select("select * from music_score mc where mc.id in (select f.music_id from favorites f where f.user_id = #{id}) limit #{page},#{rows}")
    public List<Music> listFavoritesByPage(Integer id,Integer page,Integer rows) throws Exception;

    @Select("select count(1) from music_score mc where mc.id in (select f.music_id from favorites f where f.user_id = #{id})")
    public int listFavoritesTotal(Integer id) throws Exception;

    @Delete("delete from favorites where music_id = #{id} and user_id = #{uid}")
    public void removeFavorites(Integer id,Integer uid) throws Exception;

    //以下为微信收藏的接口 与上相同，只是字段修改
    @Insert("Insert INTO favorites(open_id,union_id,music_id) VALUES(#{openId},#{unionId},#{musicId})")
    public void saveWx(String openId,String unionId,Integer musicId) throws Exception;

    @Select("select * from music_score mc where mc.id in (select f.music_id from favorites f where f.open_id = #{openId})")
    public List<Music> listFavoritesWx(String openId) throws Exception;

    @Select("select * from music_score mc where mc.id in (select f.music_id from favorites f where f.open_id = #{openId}) limit #{page},#{rows}")
    public List<Music> listFavoritesByPageWx(String openId,Integer page,Integer rows) throws Exception;

    @Select("select count(1) from music_score mc where mc.id in (select f.music_id from favorites f where f.open_id = #{openId})")
    public int listFavoritesTotalWx(String openId) throws Exception;

    @Delete("delete from favorites where music_id = #{musicId} and open_id = #{openId}")
    public void removeFavoritesWx(Integer musicId,String openId) throws Exception;
}
