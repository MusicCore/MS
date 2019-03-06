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
    public int commentId;
    public int commentPostId;//谱子ID
    public String commentAuthorName;//评论人昵称
    public String commentAuthorAccount;//评论人账号
    public String commentAuthorAvatar;//评论人头像
    public String commentDate;//评论时间
    public String commentContent;//评论内容
    public int commentApproved;//是否展示此评论
    public int commentParent;//父级评论
    public int userId;//用户ID
}
