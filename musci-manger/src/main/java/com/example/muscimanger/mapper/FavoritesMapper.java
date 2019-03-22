package com.example.muscimanger.mapper;

import com.example.muscimanger.model.Music;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
