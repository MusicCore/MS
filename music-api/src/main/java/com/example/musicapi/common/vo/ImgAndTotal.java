package com.example.musicapi.common.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @Author: yuanci
 * @Date: 2019/3/8
 * @Version: 1.0
 * @Description:
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImgAndTotal {
    private List<ImgUrl> imgUrlList;
    private int totalPages;
}
