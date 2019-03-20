package com.example.muscimanger.model;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * @Author: yuanci
 * @Date: 2019/3/5
 * @Version: 1.0
 * @Description:
 */
@Entity
@Table(name="comment")
public class Comment {
    @Id
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


    public int getCommentPostId() {
        return commentPostId;
    }

    public void setCommentPostId(int commentPostId) {
        this.commentPostId = commentPostId;
    }

    public String getCommentAuthorName() {
        return commentAuthorName;
    }

    public void setCommentAuthorName(String commentAuthorName) {
        this.commentAuthorName = commentAuthorName;
    }

    public String getCommentAuthorAccount() {
        return commentAuthorAccount;
    }

    public void setCommentAuthorAccount(String commentAuthorAccount) {
        this.commentAuthorAccount = commentAuthorAccount;
    }

    public String getCommentAuthorAvatar() {
        return commentAuthorAvatar;
    }

    public void setCommentAuthorAvatar(String commentAuthorAvatar) {
        this.commentAuthorAvatar = commentAuthorAvatar;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getCommentApproved() {
        return commentApproved;
    }

    public void setCommentApproved(int commentApproved) {
        this.commentApproved = commentApproved;
    }

    public int getCommentParent() {
        return commentParent;
    }

    public void setCommentParent(int commentParent) {
        this.commentParent = commentParent;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }
}
