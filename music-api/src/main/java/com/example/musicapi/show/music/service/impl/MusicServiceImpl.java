package com.example.musicapi.show.music.service.impl;

import com.example.musicapi.common.model.CommonContext;
import com.example.musicapi.common.model.Music;
import com.example.musicapi.common.model.PageForm;
import com.example.musicapi.common.model.SerchBean;
import com.example.musicapi.show.music.mapper.MusicMapper;
import com.example.musicapi.show.music.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * 在支持Spring Cache的环境下，对于使用@Cacheable标注的方法，Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，
 * 如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。@CachePut也可以声明
 * 一个方法支持缓存功能。与@Cacheable不同的是使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是
 * 每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
 * Cacheable注解负责将方法的返回值加入到缓存中
 * @CacheEvict CacheEvict注解负责清除缓存(它的三个参数与@Cacheable的意思是一样的)
 * @param
 * @throws Exception
 */
@Service("musicService")
@CacheConfig(cacheNames = "music")
public class MusicServiceImpl implements MusicService {
    @Autowired
    protected MusicMapper musicMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    private final String MUSIC_SET_KEY = "music_set";

    @Override
    public void saveMusic(Music music) throws Exception {
        musicMapper.save(music);
        //selectkey 会在查询结束后将查询的值放入model中
        redisTemplate.opsForZSet().add("music_set", music.getId(), music.getId());
    }

    @Override
    public List<Integer> listMusic() throws Exception {
        List<Integer> ids = musicMapper.list();
        return ids;
    }

    @Override
    @Cacheable(key="'MI'+#p0",unless="#result == null")
    public Music listMusicById(Integer id) throws Exception {
        Music music = musicMapper.listById(id);
        return music;
    }

    @Override
    @CacheEvict(key="'MI'+#p0.id")
    public void updateMusicInfoById(Music music,int isModify) throws Exception {
        if (isModify ==  1 || music.getAuthorAccount().equals(CommonContext.getInstance().getAccount())){
            //非简谱创建者想把简谱设定为不可修改时拦截此操作，设置成原始状态
            if (! CommonContext.getInstance().getAccount().equals(music.getAuthorAccount())) music.setIsModify(isModify);
            music.setLastAuthor(CommonContext.getInstance().getName());
            musicMapper.update(music);
//            listMusicByIdForUpdate(music.getId());
        }else {
            throw new Exception("无权更新");
        }
    }

    @Override
    public List<Music> listMusicByPar(PageForm pageForm) {
        return musicMapper.listByPar(pageForm);
    }

    @Override
    public Long listTotal() {
        return musicMapper.litsTotal();
    }

    @Override
    public List<Music> listByTitle(SerchBean sb) {
        String title =sb.getCondition().getAsStringEmptyNull("title");
        try {
            title = URLEncoder.encode(title, "utf-8") + '%';
            title = URLDecoder.decode(title, "utf-8") + '%';
        }catch (Exception e){

        }
        return musicMapper.listByTitle(sb);
    }

    @Override
    @CacheEvict(key="'MI'+#p0")
    public void delete(Integer id) {
        musicMapper.deleteMusic(id);
        redisTemplate.opsForZSet().remove("music_set",id,id);
    }
}
