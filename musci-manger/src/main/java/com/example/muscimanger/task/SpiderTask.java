package com.example.muscimanger.task;

import com.example.muscimanger.model.Rate;
import com.example.muscimanger.service.RateService;
import com.example.muscimanger.until.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SpiderTask {
    @Autowired
    Utils utils;
    @Autowired
    RateService rateService;

    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

//    @Scheduled(fixedRate = 10000)
    public void SpiderAndSave()throws Exception{
        System.out.println("test-----------------------------");
        for (int i = 1314; i < 1315; i++) {
            String a=utils.getTimeById(i);

            Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            Matcher m_html = p_html.matcher(a);
            a = m_html.replaceAll(""); // 过滤html标签
            String[] args1=a.split(",");
            if (a.length()>0&&a.charAt(0)!='-'){
                Rate rate=new Rate();
                rate.setId(i);
                rate.setName(args1[0]);
//                rate.setTime(args1[1]+" "+args1[2]);
//                rate.setRate(Double.parseDouble(args1[3]));
                rateService.save(rate);
            }
        }
    }
}
