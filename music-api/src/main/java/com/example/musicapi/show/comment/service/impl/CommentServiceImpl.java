package com.example.musicapi.show.comment.service.impl;

import com.example.musicapi.common.model.Comment;
import com.example.musicapi.show.comment.Repository.CommentRepository;
import com.example.musicapi.show.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

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
