package com.example.musicapi.common.Repository;


import com.example.musicapi.common.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 汇率
 * python爬虫接口
 */
public interface RateRepository extends JpaRepository<Rate,Long> {
    List<Rate> findAllById(Long id);
}
