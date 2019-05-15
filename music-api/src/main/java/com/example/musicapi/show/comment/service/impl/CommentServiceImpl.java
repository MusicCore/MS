package com.example.musicapi.show.comment.service.impl;

import com.example.musicapi.common.model.Comment;
import com.example.musicapi.common.until.RedisCacheUtil;
import com.example.musicapi.show.comment.Repository.CommentRepository;
import com.example.musicapi.show.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {
    private static String key = "CT_ALL";
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    RedisCacheUtil redisCacheUtil;

    @Override
    public void save(Comment c) throws Exception {
        commentRepository.saveAndFlush(c);
    }
    @Override
    public List<Comment> listAll() throws Exception{
        return  commentRepository.findAll();
    }
    @Override
    public void remove(Integer id) throws Exception{
        commentRepository.deleteById(id);
    }
    @Override
    public void removeAll() throws Exception{
        commentRepository.deleteAll();
    }

    @Override
    public List<Comment> listById(Integer id) throws Exception{
       return commentRepository.findAllByCommentPostId(id);
    }


}
