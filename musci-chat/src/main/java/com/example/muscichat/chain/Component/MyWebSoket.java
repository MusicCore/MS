package com.example.muscichat.chain.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.musciws.WebSocket.WebSocket;
import com.example.musciws.chain.tool.radom;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Component
@ServerEndpoint("/mywebsoket/{table}")
public class MyWebSoket {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 在线人数
     */
    private static Integer chatNum=0;
    /**
     * 聊天室
     * table代表聊天室的位置
     */
    private static ConcurrentMap<String,Set<Session>> rooms  = new ConcurrentHashMap<>();
    /**
     *  会话
     */
    private Session session;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 聊天室
     */
    private String table;
    /**
     * 建立连接
     * @param
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("table")String table, Session session){
        chatNum++;
        this.username = radom.randomName();
        this.table=table;
        this.session = session;
        logger.info("有新连接加入！ 当前在线人数" + chatNum);
        try {
            //messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息
            //先给所有人发送通知，说我上线了
            Map<String,Object> map1 = Maps.newHashMap();
            map1.put("messageType",1);
            map1.put("username",username);
            map1.put("table",table);
            //            加入自己的信息
            //            加入聊天室的信息
            // 将session按照房间名来存储，将各个房间的用户隔离
            if (!rooms.containsKey(table)) {
                // 创建房间不存在时，创建房间
                Set<Session> room = new HashSet<>();
                // 添加用户
                room.add(session);
                rooms.put(table,room);
            } else {
                // 房间已存在，直接添加用户到相应的房间
                rooms.get(table).add(session);
            }
            sendMessageAll(JSON.toJSONString(map1),table);
            //给自己发一条消息：告诉自己现在都有谁在线
//            Map<String,Object> map2 = Maps.newHashMap();
//            map2.put("messageType",3);
//            Set<Session> room =rooms.get(table);
//            Set<String> set = new HashSet<>();
//            sendMessageTo(JSON.toJSONString(map2),table,username);
        }catch (IOException e){
            logger.info("服务端发生了错误"+e.getMessage());
        }
    }

    /**
     * 连接发送错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session,Throwable error){
        logger.info("服务端发生了错误"+error.getMessage());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void close(@PathParam("table") String num,Session session){
        chatNum--;
        rooms.get(num).remove(session);
        if(rooms.get(num).isEmpty()){
            rooms.remove(num);
        }
        try {
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String,Object> map1 = Maps.newHashMap();
            map1.put("messageType",2);
            map1.put("username",username);
            sendMessageAll(JSON.toJSONString(map1),username);
        }catch (IOException o){
            logger.info(username+"下线的时候通知所有人发生了错误");
        }
        logger.info("有连接关闭！ 当前在线人数" + chatNum);
    }
    /**
     * 接受消息
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message,Session session){
        try{
            JSONObject json  = JSON.parseObject(message);
            String tableN = json.getString("table");
            String msg = json.getString("message");
            String fromusername = json.getString("username");
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String,Object> map1 = Maps.newHashMap();
            map1.put("messageType",4);
            map1.put("textMessage",msg);
            map1.put("fromusername",fromusername);
            //对所有人说
            sendMessageAll(JSON.toJSONString(map1),table);
        }catch (IOException o){
            logger.info("发生了错误了");
        }
    }
    /**
     * 查找房间号
     * @param message
     * @param table
     * @throws IOException
     */
    public void sendMessageAll(String message,String table) throws IOException {
        if (rooms.containsKey(table)){
            sendMessageAll(rooms.get(table),message);
        }
    }
    /**
     * 广播
     * @param message
     * @throws IOException
     */
    public void sendMessageAll(Set<Session> room,String message) throws IOException {
        for (Session item : room) {
            item.getAsyncRemote().sendText(message);
        }
    }
    /**
     * 查找房间号
     * @param message
     * @param table
     * @throws IOException
     */
    public void sendMessageTo(String message,String table,String username) throws IOException {
        if (rooms.containsKey(table)){
            sendMessageTo(rooms.get(table),message,username);
        }
    }

    /**
     * 自己发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessageTo(Set<Session> room,String message,String username) throws IOException {
//        for (MyWebSoket item : room) {
//            if (item.username.equals(username) ) {
//                item.session.getAsyncRemote().sendText(message);
//            }
//        }
    }
    public static synchronized int getOnlineCount() {
        return chatNum;
    }


}
