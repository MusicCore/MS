package com.example.musciws;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InformationOperateMap {
    /*
        ConcurrentMap 多线程安全高效
        HashMap 非线程安全
        HashTable 线程安全但不高效
     */
    public static ConcurrentMap<String,ConcurrentMap<String,InformationOperateMap>> map = new ConcurrentHashMap<>();

    private ChannelHandlerContext ctx;
    private Mage mage;

    public InformationOperateMap(ChannelHandlerContext ctx, Mage mage) {
        this.ctx = ctx;
        this.mage = mage;
    }

    /**
     * 添加用户信息
     * @param ctx
     * @param mage
     */
    public static void add(ChannelHandlerContext ctx,Mage mage){
        InformationOperateMap iom = new InformationOperateMap(ctx,mage);
    }
}
