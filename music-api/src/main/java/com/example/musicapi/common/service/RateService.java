package com.example.musicapi.common.service;


import com.example.musicapi.common.model.Rate;

/**
 * 汇率
 */
public interface RateService {
    void save(Rate rate);
    Rate getLatestById(Long id);
}
