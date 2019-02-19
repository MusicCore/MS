package com.example.muscimanger.service;

import com.example.muscimanger.model.Rate;

public interface RateService {
    void save(Rate rate);
    Rate getLatestById(Long id);
}
