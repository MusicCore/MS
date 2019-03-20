package com.example.muscimanger.service;

import com.example.muscimanger.model.Comment;


import java.util.List;

/**
 * wjk
 */
public interface CommentService {
     void save(Comment c) throws Exception;
     List<Comment> listAll() throws Exception;
     void remove(Integer id) throws Exception;
     void removeAll() throws Exception;

    /**
     * 查询
     * @return
     * @throws Exception
     */
    public List<Comment> listById(Integer id) throws Exception;

}
