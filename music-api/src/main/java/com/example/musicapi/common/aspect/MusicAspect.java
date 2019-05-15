package com.example.musicapi.common.aspect;

import com.example.musicapi.common.mapper.MusicMapper;
import com.example.musicapi.common.model.Music;
import com.example.musicapi.common.model.PageForm;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Music的切面处理
 * 包括
 * 分页缓存处理
 */
@Aspect
@Component
public class MusicAspect {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MusicMapper musicMapper;


    private final String MUSIC_SET_KEY = "music_set";

    @Pointcut(value = "within( com.example.musicapi.common.service.impl.*)" )
    public void MusicAop() {

    }

    @Before("MusicAop()")
    public void before(){

    }

//    zset缓存的是id的值，然后用id值查询查询数据
    @Around("MusicAop()")
    public Object around(ProceedingJoinPoint pjp){
        //方法名
        String methodName = pjp.getSignature().getName();
        //参数
        Object[] objects = pjp.getArgs();
        if ("listMusicByPar".equals(methodName)){
            PageForm page =  (PageForm) objects[0];
            try {
                Set<Integer> ids = redisTemplate.opsForZSet().reverseRange(MUSIC_SET_KEY, page.getStart(), page.getEnd());
                if(ids.size()<=0){
                    List<Integer> Aids = musicMapper.list();
                    for (Integer id: Aids) {
                        redisTemplate.opsForZSet().add(MUSIC_SET_KEY, id, id);
//                        redisTemplate.opsForValue().set(MUSIC_SET_KEY,id,30, TimeUnit.MINUTES);
                    }
                    ids = redisTemplate.opsForZSet().reverseRange(MUSIC_SET_KEY, page.getStart(), page.getEnd());
                }
                List<Music> list = new ArrayList<>(ids.size());
                for (int id : ids){
                    //查询list的时候冲缓存中拿取
                    //第一次查询查数据库
                    //后面以命中缓存为准
                    list.add(musicMapper.listById(id));
                }
                return list;
            }catch (Exception e){
                try {
                    return pjp.proceed();//执行
//                    arround(前-执行-后)

                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    return null;
                }
            }
        }else if("saveMusic".equals(methodName)){
            Music music =  (Music) objects[0];
            try {
                pjp.proceed();
                //执行原本语句,更新缓存
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return null;
            }
            //添加
            redisTemplate.opsForZSet().add(MUSIC_SET_KEY, music.getId(), music.getId());
        }else if("updateMusicInfoById".equals(methodName)){
            Music music =  (Music) objects[0];
            try {
                //执行原本语句,更新缓存
                pjp.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return null;
            }
            redisTemplate.opsForZSet().remove(MUSIC_SET_KEY,music.getId(), music.getId());
            redisTemplate.opsForZSet().add(MUSIC_SET_KEY, music.getId(), music.getId());
        }else if("delete".equals(methodName)) {
            //             删除
            int id = (int) objects[0];
            try {
                pjp.proceed();
                //执行原本语句,更新缓存
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return null;
            }
            redisTemplate.opsForZSet().remove(MUSIC_SET_KEY,id,id);
        }else if("listAll".equals(methodName)){
            try {
                List<Integer> ids = (List<Integer>)pjp.proceed();
                for (Integer id: ids) {
                    redisTemplate.opsForZSet().add(MUSIC_SET_KEY, id, id);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return null;
            }
        }
        /**
         * 不在上面方法的全部按原本执行
         */
        try {
            return pjp.proceed();
        }
        catch (Throwable throwable) {
                throwable.printStackTrace();
                return null;
        }
    }
}


