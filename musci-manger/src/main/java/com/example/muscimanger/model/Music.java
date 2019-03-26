package com.example.muscimanger.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Music implements Serializable {
    private Integer id;
    private String title;
    private String contentMusic;
    private String contentImg;
    private String contentShort;
    private String content;
    private String authorAccount;
    private String authorName;
    private String createTime;
    private String updateTime;
    private String lastAuthor;
    private Integer isModify;
    private Integer isTop;
    private Integer isDelete;
    private Integer clicks;
}
