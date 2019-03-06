package com.example.muscimanger.model;

import lombok.*;

/**
 * @Author: yuanci
 * @Date: 2019/3/5
 * @Version: 1.0
 * @Description:
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int commentId;//主键id，子级评论的父级id
    private int commentPostId;//谱子ID
    private String commentAuthorName;//评论人昵称
    private String commentAuthorAccount;//评论人账号
    private String commentAuthorAvatar;//评论人头像
    private String commentDate;//评论时间
    private String commentContent;//评论内容
    private int commentApproved;//是否展示此评论
    private int commentParent;//父级评论
    private int userId;//用户ID
    private String replyTo;//回复对象
}
