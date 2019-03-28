package com.example.muscimanger.Repository;

import com.example.muscimanger.model.Comment;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论Reposotory
 * 继承jap
 */
@Repository
public interface CommonRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findAll();

    @Select("SELECT * FROM comment WHERE comment_post_id =#{id}")
    List<Comment> findAllByCommentPostId(Integer id);
}
