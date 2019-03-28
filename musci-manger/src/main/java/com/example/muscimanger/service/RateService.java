package com.example.muscimanger.service;

import com.example.muscimanger.model.Rate;

/**
 * 汇率
 */
public interface RateService {
    void save(Rate rate);
    Rate getLatestById(Long id);
}
