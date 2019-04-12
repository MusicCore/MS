package com.example.musciws.Netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

@Data
public class Mage {
    private static ObjectMapper gson = new ObjectMapper();
    /**
     * 聊天室
     */
    private String table;
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 所发送的消息
     */
    private String message;

    /**
     * 将json字符串转成Mage
     * @param message
     * @return
     * @throws Exception
     */
    public static Mage strJson2Mage(String message)throws Exception{
        return Strings.isNotBlank(message)?null:gson.readValue(message,Mage.class);
    }
    /**
     * 将Mage转成json字符串
     * @return
     * @throws Exception
     */
    public String toJson() throws Exception{
        return gson.writeValueAsString(this);
    }

    public Mage setTableId(String table) {
        this.setTable(table);
        return this;
    }
}
