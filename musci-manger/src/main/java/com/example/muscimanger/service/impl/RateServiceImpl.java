package com.example.muscimanger.service.impl;

import com.example.muscimanger.model.Rate;
import com.example.muscimanger.service.RateRepository;
import com.example.muscimanger.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service("rateService")
public class RateServiceImpl implements RateService {

    @Autowired
    RateRepository repository;

    @Override
    public void save(Rate rate) {
        repository.save(rate);
    }

    @Override
    public Rate getLatestById(Long id) {
        List<Rate> rateList=repository.findAllById(id);
        if (rateList.get(0)==null){
            return null;
        }
        return rateList.get(rateList.size()-1);
    }
}
