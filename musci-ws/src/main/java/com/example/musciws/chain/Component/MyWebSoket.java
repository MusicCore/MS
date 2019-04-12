package com.example.musciws.chain.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.musciws.WebSocket.WebSocket;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


//@Component
//@ServerEndpoint("/mywebsoket/{id}")
public class MyWebSoket {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 在线人数
     */
    private static Integer chatNum=0;
    /**
     * 聊天室
     * table代表聊天室的位置
     * id保存MyWebSoket
     */
    private static ConcurrentMap<String,ConcurrentMap<Integer,MyWebSoket>> tableW_clients  = new ConcurrentHashMap<>();
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
     * 用户id
     */
    private Integer id;
    /**
     * 建立连接
     * @param
     * @param id
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("id")Integer id, Session session){
        chatNum++;
//        logger.info("现在来连接的客户id："+session.getId()+"用户名："+name);
//        this.username = name;
        this.id =id;
//        this.table=table;
        this.session = session;
        logger.info("有新连接加入！ 当前在线人数" + chatNum);
        try {
            //messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息
            //先给所有人发送通知，说我上线了
            Map<String,Object> map1 = Maps.newHashMap();
            map1.put("messageType",1);
            map1.put("username",username);
//            map1.put("id",id);
//            map1.put("table",table);

            sendMessageAll(JSON.toJSONString(map1),table);

            ConcurrentHashMap<Integer, MyWebSoket> clients = new ConcurrentHashMap<Integer, MyWebSoket>();
            //            加入自己的信息
            clients.put(id,this);
            //            加入聊天室的信息
            tableW_clients.put(table,clients);
            //给自己发一条消息：告诉自己现在都有谁在线
            Map<String,Object> map2 = Maps.newHashMap();
            map2.put("messageType",3);
            Set<Integer> set = tableW_clients.get(table).keySet();
            map2.put("onlineUsers",set);
            sendMessageTo(JSON.toJSONString(map2),table,id);
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
    public void close(String num){
        chatNum--;
        tableW_clients.get(num).remove(id);
        try {
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String,Object> map1 = Maps.newHashMap();
            map1.put("messageType",2);
            map1.put("onlineUsers", tableW_clients.get(num).keySet());
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
            Integer fromid = Integer.parseInt(json.getString("id"));
            Integer toid = Integer.parseInt(json.getString("toid"));
            String fromusername = json.getString("username");
            String tousername =json.getString("toname");
            //如果不是发给所有，那么就发给某一个人
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String,Object> map1 = Maps.newHashMap();
            map1.put("messageType",4);
            map1.put("textMessage",msg);
            map1.put("fromusername",fromusername);
            if(tousername.equals("All")){
                map1.put("tousername","所有人");
                sendMessageAll(JSON.toJSONString(map1),fromusername);
            }
            else{
                map1.put("tousername",tousername);
                map1.put("toid",toid);
                sendMessageTo(JSON.toJSONString(map1),tableN,id);
            }
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
        if (tableW_clients.containsKey(table)){
            sendMessageAll(tableW_clients.get(table),message);
        }
    }

    /**
     * 发送消息给别人
     * @param clients
     * @param message
     * @throws IOException
     */
    public void sendMessageAll(ConcurrentMap<Integer,MyWebSoket> clients,String message) throws IOException {
        for (MyWebSoket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }
    /**
     * 查找房间号
     * @param message
     * @param table
     * @throws IOException
     */
    public void sendMessageTo(String message,String table,Integer id) throws IOException {
        if (tableW_clients.containsKey(table)){
            sendMessageTo(tableW_clients.get(table),message,id);
        }
    }

    /**
     * 自己发送消息
     * @param clients
     * @param message
     * @throws IOException
     */
    public void sendMessageTo(ConcurrentMap<Integer,MyWebSoket> clients,String message,Integer id) throws IOException {
        for (MyWebSoket item : clients.values()) {
            if (item.id.equals(id) ) {
                item.session.getAsyncRemote().sendText(message);
                break;
            }
        }
    }
    public static synchronized int getOnlineCount() {
        return chatNum;
    }
}
