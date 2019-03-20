package com.example.muscimanger.service.impl;

import com.example.muscimanger.model.Comment;
import com.example.muscimanger.Repository.CommonRepository;
import com.example.muscimanger.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("commentService")
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommonRepository commonRepository;

    @Override
    public void save(Comment c) throws Exception {
        commonRepository.saveAndFlush(c);
    }
    @Override
    public List<Comment> listAll() throws Exception{
        return  commonRepository.findAll();
    }
    @Override
    public void remove(Integer id) throws Exception{
        commonRepository.deleteById(id);
    }
    @Override
    public void removeAll() throws Exception{
        commonRepository.deleteAll();
    }

    @Override
    public List<Comment> listById(Integer id) throws Exception{
       return commonRepository.findAllByCommentPostId(id);
    }


}
