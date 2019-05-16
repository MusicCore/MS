package com.example.musicapi.rate.controller;

import com.example.musicapi.common.model.Rate;
import com.example.musicapi.rate.service.RateService;
import com.example.musicapi.common.until.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 汇率-接python（暂时没用）
 */
@RestController
public class timeSpiderController {
    @Autowired
    Utils utils;
    @Autowired
    RateService rateService;
    @RequestMapping(value = "/{tid}",method = RequestMethod.GET)
    public Rate getTimeById(@PathVariable long tid)throws Exception{
        Rate rate=new Rate();
        rate=rateService.getLatestById(tid);
        if (null!=rate){
            return rate;
        }
        return null;
    }

    @RequestMapping(value = "/{tid}",method = RequestMethod.POST)
    public Rate saveTimeById(@PathVariable int tid)throws Exception{
        String a=utils.getTimeById(tid);
        String[] args1=a.split(" ");
        if (a.charAt(0)!='-'){
            Rate rate=new Rate();
            rate.setId(tid);
            rate.setName(args1[0]);
            rate.setTime(args1[1]+" "+args1[2]);
            rate.setRate(Double.parseDouble(args1[3]));
            rateService.save(rate);
            return rate;
        }
        return null;
    }

}
