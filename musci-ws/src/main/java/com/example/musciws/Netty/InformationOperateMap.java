package com.example.musciws.Netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

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
        ConcurrentMap<String,InformationOperateMap> cmap = new ConcurrentHashMap<>();
        if(map.containsKey(mage.getTable())){
            map.get(mage.getTable()).put(mage.getId(),iom);
        }else {
            cmap.put(mage.getId(),iom);
            map.put(mage.getTable(),cmap);
        }
    }

    /**
     * 删除用户
     * @param id
     * @param table
     */
    public static void delete(String id,String table){
        ConcurrentMap<String,InformationOperateMap> cmap = map.get(table);
        if(cmap.size()<=1){
            map.remove(table);
        }else{
            cmap.remove(id);
        }
    }

    /**
     * 判断是否存在用户
     * @param mage
     * @return
     */
    public static Boolean isNo(Mage mage){
        return map.containsKey(mage.getTable())?map.get(mage.getTable()).containsKey(mage.getId())?false:true:true;
    }

    /**
     * 发送消息
     * @param mage
     * @throws Exception
     */
    public void sead(Mage mage) throws Exception{
        ctx.writeAndFlush(new TextWebSocketFrame(mage.toJson()));
    }

    public Mage getMage(){
        return mage;
    }
}
