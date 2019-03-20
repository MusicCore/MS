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

    @Delete("delete from favorites where music_id = #{id}")
    public void removeFavorites(Integer id) throws Exception;
}
