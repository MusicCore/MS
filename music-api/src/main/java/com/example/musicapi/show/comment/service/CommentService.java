package com.example.musicapi.show.comment.service;



import com.example.musicapi.common.model.Comment;

import java.util.List;

/**
 * 评论
 * wjk
 */
public interface CommentService {
    /**
     * 添加评论
     * @param c
     * @throws Exception
     */
     void save(Comment c) throws Exception;
    /**
     * 查询所有评论
     * @return
     * @throws Exception
     */
     List<Comment> listAll() throws Exception;
    /**
     * 删除评论
     * @param id
     * @throws Exception
     */
     void remove(Integer id) throws Exception;
    /**
     * 删除所有评论
     * @throws Exception
     */
     void removeAll() throws Exception;
    /**
     * 谱子id查询
     * @return
     * @throws Exception
     */
    public List<Comment> listById(Integer id) throws Exception;

}
